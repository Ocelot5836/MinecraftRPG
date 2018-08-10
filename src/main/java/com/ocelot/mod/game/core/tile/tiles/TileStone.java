package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.property.PropertyEnum;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileStone extends Basic3DTile {

	public static final PropertyEnum<BlockStone.EnumType> VARIANT = PropertyEnum.<BlockStone.EnumType>create("variant", BlockStone.EnumType.STONE, BlockStone.EnumType.class);

	public TileStone(int id) {
		super(id, "stone", "stone", null);
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, VARIANT);
	}
	
	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		BlockStone.EnumType type = tileMap.getValue(VARIANT, x, y, layer);
		return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, type);
	}
}