package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.property.PropertyEnum;
import com.ocelot.mod.game.core.tile.property.PropertyInteger;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileSapling extends TilePlant {

	public static final PropertyEnum<BlockPlanks.EnumType> TYPE = PropertyEnum.<BlockPlanks.EnumType>create("type", BlockPlanks.EnumType.OAK, BlockPlanks.EnumType.class);
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	public TileSapling(int id) {
		super(id, "sapling", "sapling", null);
	}

	@Override
	public boolean isFullCube(TileMap tileMap, int x, int y, int layer) {
		return false;
	}

	@Override
	public boolean isTranslucent(TileMap tileMap, int x, int y, int layer) {
		return true;
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, TYPE, STAGE);
	}

	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		BlockPlanks.EnumType type = tileMap.getValue(TYPE, x, y, layer);
		int stage = tileMap.getValue(STAGE, x, y, layer);
		return Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, type).withProperty(BlockSapling.STAGE, stage);
	}
}