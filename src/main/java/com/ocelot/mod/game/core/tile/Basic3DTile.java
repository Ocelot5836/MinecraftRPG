package com.ocelot.mod.game.core.tile;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.gfx.TileRenderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A basic implementation of {@link Tile}.
 * 
 * @author Ocelot5836
 */
public class Basic3DTile extends Tile {

	private IBlockState state;

	public Basic3DTile(int id, String registryName, String unlocalizedName, IBlockState state) {
		super(id, registryName, unlocalizedName);
		this.state = state;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks) {
		GlStateManager.disableDepth();

		GlStateManager.pushMatrix();
		GlStateManager.translate(renderX + 8, renderY + 8 - layer * 11, 0);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(45, 1, 0, 0);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.popMatrix();

		GlStateManager.rotate(45, 1, 0, 0);
		GlStateManager.rotate(180, 1, 0, 0);

		TileRenderer.render(renderX, renderY, this.state, 16);
		GlStateManager.popMatrix();

		// Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
		RenderHelper.disableStandardItemLighting();
	}
}