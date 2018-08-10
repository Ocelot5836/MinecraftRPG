package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.property.PropertyEnum;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileSand extends TileFalling {
	
    public static final PropertyEnum<BlockSand.EnumType> VARIANT = PropertyEnum.<BlockSand.EnumType>create("variant", BlockSand.EnumType.SAND, BlockSand.EnumType.class);

	public TileSand(int id) {
		super(id, "sand", "sand", null);
	}
	
	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, VARIANT);
	}
	
	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		return Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, tileMap.getValue(VARIANT, x, y, layer));
	}
}