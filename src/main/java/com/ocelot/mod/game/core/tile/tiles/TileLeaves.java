package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.property.PropertyEnum;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileLeaves extends Basic3DTile {

	public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.<BlockPlanks.EnumType>create("variant", BlockPlanks.EnumType.OAK, BlockPlanks.EnumType.class);

	public TileLeaves(int id) {
		super(id, "leaves", "leaves", null);
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, VARIANT);
	}

	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		BlockPlanks.EnumType type = tileMap.getValue(VARIANT, x, y, layer);
		return type.ordinal() >= 4 ? Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, type) : Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, type);
	}
}