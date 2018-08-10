package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.core.tile.Basic3DTile;

import net.minecraft.block.state.IBlockState;

// TODO add falling tiles
public class TileFalling extends Basic3DTile {

	public TileFalling(int id, String registryName, String unlocalizedName, IBlockState state) {
		super(id, registryName, unlocalizedName, state);
	}
}