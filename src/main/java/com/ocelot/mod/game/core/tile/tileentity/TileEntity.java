package com.ocelot.mod.game.core.tile.tileentity;

import com.ocelot.mod.game.core.TileMap;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A basic tile data storage that can be used in a {@link TileMap}.
 * 
 * @author Ocelot5836
 */
public class TileEntity {

	private TileMap tileMap;
	private int x;
	private int y;
	private int layer;

	/**
	 * @param tileMap
	 *            The map the tile entity is bound to
	 * @param x
	 *            The x position of the tile entity
	 * @param y
	 *            The y position of the tile entity
	 * @param layer
	 *            The layer of the tile entity
	 */
	public TileEntity(TileMap tileMap, int x, int y, int layer) {
		this.tileMap = tileMap;
		this.x = x;
		this.y = y;
		this.layer = layer;
	}

	/**
	 * @return The map this tile entity is bound to
	 */
	protected TileMap getTileMap() {
		return tileMap;
	}

	/**
	 * @return The x position of this tile entity
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y position of this tile entity
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return The layer of this tile entity
	 */
	public int getLayer() {
		return layer;
	}
}