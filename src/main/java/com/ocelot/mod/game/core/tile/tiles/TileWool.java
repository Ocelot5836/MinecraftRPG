package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.property.PropertyEnum;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class TileWool extends Basic3DTile {

	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

	public TileWool(int id) {
		super(id, "wool", "wool", null);
	}

	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		EnumDyeColor color = tileMap.getValue(COLOR, x, y, layer);
		return color == null ? Blocks.WOOL.getDefaultState() : Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, color);
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, COLOR);
	}
}