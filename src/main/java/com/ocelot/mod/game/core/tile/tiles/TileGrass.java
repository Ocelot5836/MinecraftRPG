package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.Tile;
import com.ocelot.mod.game.core.tile.property.PropertyBoolean;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileGrass extends Basic3DTile {

	public static final PropertyBoolean SNOWY = PropertyBoolean.create("snowy");

	public TileGrass(int id) {
		super(id, "grass", "grass", null);
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, SNOWY);
	}

	@Override
	public TileStateContainer modifyContainer(int x, int y, int layer, TileMap tileMap, TileStateContainer container) {
		Tile aboveTile = tileMap.getTile(x, y, layer + 1);
		if (aboveTile != null) {
			// TODO check if snow is on top
		}
		return container;
	}

	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		boolean snowy = tileMap.getValue(SNOWY, x, y, layer);
		return Blocks.GRASS.getDefaultState().withProperty(BlockGrass.SNOWY, snowy);
	}
}