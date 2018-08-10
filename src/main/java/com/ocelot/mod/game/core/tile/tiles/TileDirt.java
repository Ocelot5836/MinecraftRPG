package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.Tile;
import com.ocelot.mod.game.core.tile.property.PropertyBoolean;
import com.ocelot.mod.game.core.tile.property.PropertyEnum;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileDirt extends Basic3DTile {

	public static final PropertyEnum<BlockDirt.DirtType> VARIANT = PropertyEnum.<BlockDirt.DirtType>create("variant", BlockDirt.DirtType.DIRT, BlockDirt.DirtType.class);
	public static final PropertyBoolean SNOWY = PropertyBoolean.create("snowy");

	public TileDirt(int id) {
		super(id, "dirt", "dirt", null);
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, VARIANT, SNOWY);
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
		BlockDirt.DirtType type = tileMap.getValue(VARIANT, x, y, layer);
		boolean snowy = tileMap.getValue(SNOWY, x, y, layer);
		return Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, type).withProperty(BlockDirt.SNOWY, snowy);
	}
}