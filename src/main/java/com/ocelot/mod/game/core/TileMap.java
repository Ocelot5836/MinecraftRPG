package com.ocelot.mod.game.core;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.ocelot.mod.MinecraftRPG;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.tile.Tile;
import com.ocelot.mod.game.core.tile.property.IProperty;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;
import com.ocelot.mod.game.core.tile.tileentity.TileEntity;
import com.ocelot.mod.lib.Lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A class that holds and handles the rendering/updating of tiles.
 * 
 * @author Ocelot5836
 */
public class TileMap {

	private static final Map<String, CachedMap> CACHED_MAPS = Maps.<String, CachedMap>newHashMap();

	private int[] tiles;
	private TileStateContainer[] containers;
	private Map<String, TileEntity> tileEntities;

	private int width;
	private int height;
	private int layers;

	private double xOffset;
	private double yOffset;
	private double lastXOffset;
	private double lastYOffset;

	private TileMap() {
		this.xOffset = 0;
		this.yOffset = 0;
	}

	/**
	 * @param width
	 *            The width of the map
	 * @param height
	 *            The height of the map
	 * @param layers
	 *            The amount of layers to have in the map
	 */
	public TileMap(int width, int height, int layers) {
		this.xOffset = 0;
		this.yOffset = 0;
		this.width = width;
		this.height = height;
		this.layers = layers;
		this.tiles = new int[width * height * layers];
		this.containers = new TileStateContainer[this.tiles.length];
		this.tileEntities = new HashMap<String, TileEntity>();
		for (int layer = 0; layer < this.layers; layer++) {
			for (int y = 0; y < this.height; y++) {
				for (int x = 0; x < this.width; x++) {
					this.setTile(layer == 0 ? Tile.STONE : Tile.AIR, x, y, layer);
				}
			}
		}
	}

	/**
	 * @param mapLocation
	 *            The location of the map to load
	 */
	public TileMap(ResourceLocation mapLocation) {
		this.tileEntities = new HashMap<String, TileEntity>();

		CachedMap map = CACHED_MAPS.get(mapLocation.toString());

		if (map == null) {
			String data = Lib.loadTextToString(mapLocation);
			if (data == null)
				throw new RuntimeException("Could not find map \'" + mapLocation + "\'");

			String line = null;
			try {
				BufferedReader reader = new BufferedReader(new StringReader(data));

				this.width = -1;
				this.height = -1;
				this.layers = -1;

				int layer = -1;
				int loadedHeight = 0;
				while ((line = reader.readLine()) != null) {
					if (line.trim().isEmpty() || line.trim().startsWith("#"))
						continue;
					if (this.width == -1) {
						this.width = Integer.parseInt(line);
						continue;
					} else if (this.height == -1) {
						this.height = Integer.parseInt(line);
						continue;
					} else if (this.layers == -1) {
						this.layers = Integer.parseInt(line);
						this.tiles = new int[this.width * this.height * this.layers];
						this.containers = new TileStateContainer[this.tiles.length];
						continue;
					} else {
						if (line.contains("layer:")) {
							if (width == -1 || height == -1 || layers == -1) {
								throw new IllegalArgumentException("Width, Height, and Layers can not be null!");
							}
							layer = Integer.parseInt(line.split(":")[1].trim());
							loadedHeight = 0;
							if (layer >= this.layers) {
								MinecraftRPG.logger().warn("Layer " + layer + " is out of the bounds " + this.layers);
								layer = -1;
								continue;
							}
						} else {
							if (layer == -1) {
								continue;
							}

							if (loadedHeight >= height) {
								layer = -1;
								continue;
							}

							String[] splitTiles = line.split(" ");
							for (int i = 0; i < splitTiles.length; i++) {
								int id = Tile.AIR.getId();
								String[] setProperties = new String[0];
								try {
									String[] splitProperties = splitTiles[i].split("\\[");
									id = Integer.parseInt(splitProperties[0]);
									if (splitProperties.length > 1) {
										setProperties = this.getTileProperties(splitProperties[1]);
									}
								} catch (Throwable t) {
									t.printStackTrace();
								}
								this.setTile(Tile.getTile(id), i, loadedHeight, layer);

								TileStateContainer container = this.getContainer(i, loadedHeight, layer);
								Map<String, IProperty> properties = container.getProperties();

								for (String name : properties.keySet()) {
									IProperty property = properties.get(name);
									for (int j = 0; j < setProperties.length; j++) {
										String[] values = setProperties[j].split("\\=");
										if (values.length > 1) {
											property.parseValue(values[1]);
										}
									}
								}
							}

							loadedHeight++;
						}
					}
				}
				reader.close();
			} catch (Exception e) {
				Game.getGame().handleCrash(e, "Error loading map \'" + mapLocation + "\' for line \'" + line + "\'");
			}
			map = new CachedMap(mapLocation, this.tiles, this.containers, this.tileEntities);
			CACHED_MAPS.put(mapLocation.toString(), map);
		}
		int[] mapTiles = map.getTiles();
		TileStateContainer[] mapContainers = map.getContainers();

		this.tiles = new int[mapTiles.length];
		this.containers = new TileStateContainer[mapContainers.length];

		for (int i = 0; i < mapTiles.length; i++) {
			this.tiles[i] = mapTiles[i];
			this.containers[i] = mapContainers[i];
		}

		this.tileEntities = new HashMap<String, TileEntity>(map.getTileEntities());
	}

	private void checkProperties(Entry<String, IProperty> entry, String[] setProperties) {

	}

	/**
	 * Fetches the properties from a tile.
	 * 
	 * @param tile
	 *            The tile to get the properties from
	 * @return The properties extracted from tiles
	 */
	private String[] getTileProperties(String tile) {
		String[] properties = tile.split("\\]")[0].split(",");
		return properties;
	}

	public void addTreeTemp(int x, int y, int layer, int height) {

	}

	/**
	 * Called 20 times per second. Updates the objects inside of a tile map.
	 */
	public void update() {
		Tile.updateTiles();

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				for (int layer = 0; layer < this.layers; layer++) {
					Tile tile = this.getTile(x, y, layer);
					if (tile != null) {
						tile.updateTile(this, x, y, layer);
					}
				}
			}
		}

		for (String position : this.tileEntities.keySet()) {
			TileEntity te = this.tileEntities.get(position);
			if (te instanceof ITickable) {
				((ITickable) te).update();
			}
		}

		this.lastXOffset = this.xOffset;
		this.lastYOffset = this.yOffset;
	}

	/**
	 * Called to render all the objects to the screen.
	 * 
	 * @param gui
	 *            The gui that is rendering this game
	 * @param mc
	 *            A Minecraft instance
	 * @param game
	 *            A Game instance
	 * @param partialTicks
	 *            The percentage from last update and this update
	 */
	public void render(Gui gui, Minecraft mc, Game game, float partialTicks) {
		double renderOffsetX = this.lastXOffset + (this.xOffset - this.lastXOffset) * partialTicks;
		double renderOffsetY = this.lastYOffset + (this.yOffset - this.lastYOffset) * partialTicks;

		for (int layer = 0; layer < this.layers; layer++) {
			for (int y = (int) (renderOffsetY / 16.0) - 1; y < (int) (renderOffsetY / 16.0 + game.getApp().getHeight() / 8.0) + 1 + layer; y++) {
				for (int x = (int) (renderOffsetX / 16.0) - 1; x < (int) (renderOffsetX / 16.0 + game.getApp().getWidth() / 16.0) + 1; x++) {
					Tile tile = this.getTile(x, y, layer);
					if (tile != null) {
						TileStateContainer container = this.getContainer(x, y, layer);
						if (container != null) {
							tile.modifyContainer(x, y, layer, this, container);
						}
						tile.render(gui, mc, game, this, x, y, layer, x * 16 - renderOffsetX, y * 16 - renderOffsetY - 4.75 * y, partialTicks);
					}
				}
			}
		}
	}

	/**
	 * @return The x position of the "camera"
	 */
	public double getXOffset() {
		return xOffset;
	}

	/**
	 * @return The y position of the "camera"
	 */
	public double getYOffset() {
		return yOffset;
	}

	/**
	 * @return The last x position of the "camera"
	 */
	public double getLastXOffset() {
		return lastXOffset;
	}

	/**
	 * @return The last y position of the "camera"
	 */
	public double getLastYOffset() {
		return lastYOffset;
	}

	/**
	 * Checks the tiles at the specified position.
	 * 
	 * @param x
	 *            The x position to get the tile at
	 * @param y
	 *            The y position to get the tile at
	 * @param layer
	 *            The layer to get the tile at
	 * @return The tile at that position or null if the tile id is invalid or if the position is out of the world
	 */
	@Nullable
	public Tile getTile(int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return null;
		return Tile.getTile(this.tiles[x + y * this.width + layer * this.width * this.height]);
	}

	/**
	 * Checks the tiles at the specified position.
	 * 
	 * @param x
	 *            The x position to get the tile at
	 * @param y
	 *            The y position to get the tile at
	 * @param layer
	 *            The layer to get the tile at
	 * @return The tile container at that position or null if there is no tile there
	 */
	public TileStateContainer getContainer(int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return TileStateContainer.NULL;
		TileStateContainer container = this.containers[x + y * this.width + layer * this.width * this.height];
		return container == null ? TileStateContainer.NULL : container;
	}

	/**
	 * Checks the tiles at the specified position.
	 * 
	 * @param property
	 *            The property to get the value of
	 * @param x
	 *            The x position to get the tile at
	 * @param y
	 *            The y position to get the tile at
	 * @param layer
	 *            The layer to get the tile at
	 * @return The tile container at that position or null if there is no tile there
	 */
	public <T> T getValue(IProperty<T> property, int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return property.getDefaultValue();
		TileStateContainer container = this.getContainer(x, y, layer);
		return container == TileStateContainer.NULL ? property.getDefaultValue() : container.getValue(property);
	}

	/**
	 * Checks the tile entities at the specified position.
	 * 
	 * @param x
	 *            The x position to get the tile entity at
	 * @param y
	 *            The y position to get the tile entity at
	 * @param layer
	 *            The layer to get the tile entity at
	 * @return The tile entity at that position or null if there is no tile entity at that position or if the position is out of the world
	 */
	@Nullable
	public TileEntity getTileEntity(int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return null;
		return this.tileEntities.get(x + "," + y + "," + layer);
	}

	/**
	 * @return The width of the tile map
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The height of the tile map
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return The amount of layers in the tile map
	 */
	public int getLayers() {
		return layers;
	}

	/**
	 * Sets the "camera" position in the world.
	 * 
	 * @param xOffset
	 *            The offset in the x direction
	 * @param yOffset
	 *            The offset in the y direction
	 */
	public TileMap setOffset(double xOffset, double yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		return this;
	}

	/**
	 * Sets the last "camera" position in the world.
	 * 
	 * @param lastXOffset
	 *            xOffset The last offset in the x direction
	 * @param lastYOffset
	 *            The last offset in the y direction
	 */
	public TileMap setLastOffset(double lastXOffset, double lastYOffset) {
		this.lastXOffset = lastXOffset;
		this.lastYOffset = lastYOffset;
		return this;
	}

	/**
	 * Sets the tile at the specified position to the supplied tile.
	 * 
	 * @param tile
	 *            The new tile to place
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param layer
	 *            The layer of the tile
	 */
	public TileStateContainer setTile(Tile tile, int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers || layer == 0 && (!tile.isFullCube(this, x, y, layer) || tile.isTranslucent(this, x, y, layer)))
			return TileStateContainer.NULL;
		this.tiles[x + y * this.width + layer * this.width * this.height] = tile.getId();
		this.containers[x + y * this.width + layer * this.width * this.height] = tile.createContainer();

		TileEntity te = tile.createNewTileEntity(this, x, y, layer);
		if (te != null) {
			this.tileEntities.put(x + "," + y + "," + layer, te);
		}
		return this.getContainer(x, y, layer);
	}

	/**
	 * Sets the value of the container at the specified position.
	 * 
	 * @param property
	 *            The property to set the value of
	 * @param value
	 *            The new value of the property or null to reset to the default
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param layer
	 *            The layer of the tile
	 */
	public <T> void setValue(IProperty<T> property, @Nullable T value, int x, int y, int layer) {
		TileStateContainer container = this.getContainer(x, y, layer);
		if (container != TileStateContainer.NULL) {
			container.setValue(property, value == null ? property.getDefaultValue() : value);
		}
	}

	/**
	 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
	 * 
	 * <br>
	 * </br>
	 * 
	 * Used to store loading data so a map only needs to be loaded once.
	 * 
	 * @author Ocelot5836
	 */
	private static class CachedMap {

		private ResourceLocation location;
		private int[] tiles;
		private TileStateContainer[] containers;
		private Map<String, TileEntity> tileEntities;

		private CachedMap(ResourceLocation location, int[] tiles, TileStateContainer[] containers, Map<String, TileEntity> tileEntities) {
			this.location = location;
			this.tiles = new int[tiles.length];
			this.containers = new TileStateContainer[tiles.length];
			this.tileEntities = new HashMap<String, TileEntity>(tileEntities);
			for (int i = 0; i < tiles.length; i++) {
				this.tiles[i] = tiles[i];
				this.containers[i] = containers[i];
			}
		}

		public ResourceLocation getLocation() {
			return location;
		}

		public int[] getTiles() {
			return tiles;
		}

		public TileStateContainer[] getContainers() {
			return containers;
		}

		public Map<String, TileEntity> getTileEntities() {
			return tileEntities;
		}
	}

	/**
	 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
	 * 
	 * <br>
	 * </br>
	 * 
	 * Used to handle the tile map reloading event.
	 * 
	 * @author Ocelot5836
	 */
	public static class Listener implements IResourceManagerReloadListener {

		private static final Listener INSTANCE = new Listener();

		public static void register() {
			((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(INSTANCE);
		}

		public static void clear() {
			CACHED_MAPS.clear();
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
			MinecraftRPG.logger().info("Reloading TileMaps");
			clear();
		}
	}
}