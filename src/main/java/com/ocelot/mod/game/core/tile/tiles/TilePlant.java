package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.gfx.TileRenderer;
import com.ocelot.mod.game.core.tile.Basic3DTile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

public class TilePlant extends Basic3DTile {

	public TilePlant(int id, String registryName, String unlocalizedName, IBlockState state) {
		super(id, registryName, unlocalizedName, state);
	}
	
	@Override
	protected void applyLighting() {
		GlStateManager.rotate(90, 1, 0, 0);
		RenderHelper.enableGUIStandardItemLighting();
	}
}