package com.ocelot.mod.game.core.tile;

import javax.annotation.Nullable;

import com.ocelot.mod.MinecraftRPG;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.tileentity.TileEntity;
import com.ocelot.mod.game.core.tile.tiles.TileAir;
import com.ocelot.mod.game.core.tile.tiles.TileEnchantmentTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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

	public static final Tile VOID = new BasicTile(0, "void", "void", new ItemStack(Blocks.COAL_BLOCK));
	public static final Tile AIR = new TileAir(1);
	public static final Tile DIRT = new Basic3DTile(2, "dirt", "dirt", Blocks.DIRT.getDefaultState());
	public static final Tile ANVIL = new Basic3DTile(3, "anvil", "anvil", Blocks.ANVIL.getDefaultState());
	public static final Tile ENCHANTMENT_TABLE = new TileEnchantmentTable(4);
	public static final Tile BEACON = new Basic3DTile(5, "beacon", "beacon", Blocks.BEACON.getDefaultState());
	public static final Tile CARPET = new Basic3DTile(6, "carpet", "carpet", Blocks.CARPET.getDefaultState());

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
	 * Updates the tile. Called 20 times per second.
	 */
	public abstract void update();

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
	 * Updates all the tiles.
	 */
	public static void updateTiles() {
		for (int id = 0; id < TILES.length; id++) {
			Tile tile = getTile(id);
			if (tile != null) {
				tile.update();
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