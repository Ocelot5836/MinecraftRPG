package com.ocelot.mod.game.core.tile;

import javax.annotation.Nullable;

import com.ocelot.mod.MinecraftRPG;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;
import com.ocelot.mod.game.core.tile.tileentity.TileEntity;
import com.ocelot.mod.game.core.tile.tiles.TileAir;
import com.ocelot.mod.game.core.tile.tiles.TileDirt;
import com.ocelot.mod.game.core.tile.tiles.TileFalling;
import com.ocelot.mod.game.core.tile.tiles.TileGrass;
import com.ocelot.mod.game.core.tile.tiles.TilePlanks;
import com.ocelot.mod.game.core.tile.tiles.TileSand;
import com.ocelot.mod.game.core.tile.tiles.TileSapling;
import com.ocelot.mod.game.core.tile.tiles.TileStone;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A single cube in the game world.
 * 
 * @author Ocelot5836
 */
public abstract class Tile {

	/** Contains every single registered tile */
	public static final Tile[] TILES = new Tile[256];

	private int id;
	private String registryName;
	private String unlocalizedName;

	public static final Tile AIR = new TileAir(0);

	public static final Tile STONE = new TileStone(1);
	public static final Tile GRASS = new TileGrass(2);
	public static final Tile DIRT = new TileDirt(3);
	public static final Tile COBBLESTONE = new Basic3DTile(4, "cobblestone", "cobblestone", Blocks.COBBLESTONE.getDefaultState());
	public static final Tile PLANKS = new TilePlanks(5);
	public static final Tile SAPLING = new TileSapling(6);
	public static final Tile BEDROCK = new Basic3DTile(7, "bedrock", "bedrock", Blocks.BEDROCK.getDefaultState());
	// public static final Tile WATER = new TileFluid(8, "water", "water", Blocks.WATER.getDefaultState(), Blocks.FLOWING_WATER.getDefaultState());
	// public static final Tile LAVA = new TileFluid(9, "lava", "lava", Blocks.LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState());
	public static final Tile SAND = new TileSand(10);
	public static final Tile GRAVEL = new TileFalling(11, "gravel", "gravel", Blocks.GRAVEL.getDefaultState());

	// public static final Tile ANVIL = new Basic3DTile(3, "anvil", "anvil", Blocks.ANVIL.getDefaultState());
	// public static final Tile ENCHANTMENT_TABLE = new TileEnchantmentTable(4);
	// public static final Tile BEACON = new Basic3DTile(5, "beacon", "beacon", Blocks.BEACON.getDefaultState());
	// public static final Tile CARPET = new Basic3DTile(6, "carpet", "carpet", Blocks.CARPET.getDefaultState());
	// public static final Tile WOOL = new TileWool(7);

	/**
	 * @param id
	 *            The id of the tile
	 * @param registryName
	 *            The registration name for the tile
	 * @param unlocalizedName
	 *            The name to use when localizing the tile
	 */
	public Tile(int id, String registryName, String unlocalizedName) {
		this.id = id;
		if (TILES[id] != null) {
			throw new RuntimeException("Duplicate tile id for tile \'" + registryName + "\'");
		} else {
			TILES[id] = this;
		}

		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
	}

	/**
	 * Updates the tile at the specified tile position. Called 20 times per second.
	 * 
	 * @param tileMap
	 *            The map this tile is being updated from
	 * @param x
	 *            The x position of this tile
	 * @param y
	 *            The y position of this tile
	 * @param layer
	 *            The layer of this tile
	 */
	public void updateTile(TileMap tileMap, int x, int y, int layer) {
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
	 * @param tileMap
	 *            The map this tile is being rendered from
	 * @param x
	 *            The x position to render this tile
	 * @param y
	 *            The y position to render this tile
	 * @param layer
	 *            The layer to render this tile in
	 * @param renderX
	 *            The x position to render this tile at
	 * @param renderY
	 *            The y position to render this tile at
	 * @param partialTicks
	 *            The percentage from last update and this update
	 */
	public abstract void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks);

	/**
	 * Called to modify any properties in the tileStateContainer.
	 * 
	 * @param tileMap
	 *            The tile map instance
	 * @param container
	 *            The container that is being modified
	 */
	public TileStateContainer modifyContainer(int x, int y, int layer, TileMap tileMap, TileStateContainer container) {
		return container;
	}

	/**
	 * Creates a new tile entity that will be bound to this position.
	 * 
	 * @param tileMap
	 *            The map the tile entity is bound to
	 * @param x
	 *            The x position of the tile entity
	 * @param y
	 *            The y position of the tile entity
	 * @param layer
	 *            The layer of the tile entity
	 * @return The tile entity or null if you do not want a tile entity
	 */
	@Nullable
	public TileEntity createNewTileEntity(TileMap tileMap, int x, int y, int layer) {
		return null;
	}

	/**
	 * Creates a new {@link TileStateContainer}. If you have custom properties, you MUST override this method to register them!
	 * 
	 * @return The container created
	 */
	public TileStateContainer createContainer() {
		return new TileStateContainer(this);
	}

	/**
	 * @return The id used to identify this tile
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The name used to identify this tile
	 */
	public String getRegistryName() {
		return registryName;
	}

	/**
	 * @return The name used when getting a localized name of this tile
	 */
	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	/**
	 * @return The translated name of this tile
	 */
	public String getLocalizedName() {
		return I18n.format("tile." + MinecraftRPG.MOD_ID + "." + this.getUnlocalizedName() + ".name");
	}

	/**
	 * Used to test if you can place this on layer zero.
	 * 
	 * @param tileMap
	 *            The tile map instance
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param layer
	 *            The layer of the tile
	 * @return Whether or not this has the ability to be placed on layer zero
	 */
	public boolean isFullCube(TileMap tileMap, int x, int y, int layer) {
		return true;
	}

	/**
	 * Used to determine if light can pass through this cube.
	 * 
	 * @param tileMap
	 *            The tile map instance
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param layer
	 *            The layer of the tile
	 * @return Whether or not this is a translucent cube
	 */
	public boolean isTranslucent(TileMap tileMap, int x, int y, int layer) {
		return false;
	}

	/**
	 * Updates all the tiles that implement {@link ITickable}.
	 */
	public static void updateTiles() {
		for (int id = 0; id < TILES.length; id++) {
			Tile tile = getTile(id);
			if (tile != null && tile instanceof ITickable) {
				((ITickable) tile).update();
			}
		}
	}

	/**
	 * Checks all the tiles that are registered and sees if the id supplied is the same as the tile's id.
	 * 
	 * @param id
	 *            The if used to register the tile
	 * @return The tile found or null if there is no tile bound to that id
	 */
	@Nullable
	public static Tile getTile(int id) {
		if (id < 0 || id >= TILES.length)
			return null;
		return TILES[id];
	}

	/**
	 * Checks all the tiles that are registered and sees if the registry name supplied is the same as the tile's registry name.
	 * 
	 * @param registryName
	 *            The name used to register the tile
	 * @return The tile found or null if there is no tile bound to that name
	 */
	@Nullable
	public static Tile getTile(String registryName) {
		for (int id = 0; id < TILES.length; id++) {
			Tile tile = getTile(id);
			if (tile != null && tile.getRegistryName().equals(registryName)) {
				return tile;
			}
		}
		return null;
	}
}