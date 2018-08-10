package com.ocelot.mod.game.core;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.tile.Tile;
import com.ocelot.mod.game.core.tile.property.IProperty;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;
import com.ocelot.mod.game.core.tile.tileentity.TileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ITickable;

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
					this.setTile(layer == 0 ? Tile.DIRT : Tile.AIR, x, y, layer);
				}
			}
		}
	}

	/**
	 * Called 20 times per second. Updates the things inside a tile map.
	 */
	public void update() {
		Tile.updateTiles();
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
	 * Called to render this tile to the screen.
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
		for (int layer = 0; layer < this.layers; layer++) {
			for (int y = 0; y < this.height; y++) {
				for (int x = 0; x < this.width; x++) {
					Tile tile = this.getTile(x, y, layer);
					if (tile != null) {
						TileStateContainer container = this.getContainer(x, y, layer);
						if (container != null) {
							tile.modifyContainer(x, y, layer, this, container);
						}
						tile.render(gui, mc, game, this, x, y, layer, x * 16 - (this.lastXOffset + (this.xOffset - this.lastXOffset) * partialTicks), y * 16 - (this.lastYOffset + (this.yOffset - this.lastYOffset) * partialTicks) - 4.75 * y, partialTicks);
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
	@Nullable
	public TileStateContainer getContainer(int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return null;
		return this.containers[x + y * this.width + layer * this.width * this.height];
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
	@Nullable
	public <T> T getValue(IProperty<T> property, int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return null;
		TileStateContainer container = this.getContainer(x, y, layer);
		return container == null ? null : container.getValue(property);
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
	public void setTile(Tile tile, int x, int y, int layer) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height || layer < 0 || layer >= this.layers)
			return;
		this.tiles[x + y * this.width + layer * this.width * this.height] = tile.getId();
		this.containers[x + y * this.width + layer * this.width * this.height] = tile.createContainer();

		TileEntity te = tile.createNewTileEntity(this, x, y, layer);
		if (te != null) {
			this.tileEntities.put(x + "," + y + "," + layer, te);
		}
	}

	/**
	 * Sets the value of the container at the specified position.
	 * 
	 * @param property
	 *            The property to set the value of
	 * @param value
	 *            The new value of the property
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param layer
	 *            The layer of the tile
	 */
	public <T> void setValue(IProperty<T> property, T value, int x, int y, int layer) {
		TileStateContainer container = this.getContainer(x, y, layer);
		if (container != null) {
			container.setValue(property, value);
		}
	}
}