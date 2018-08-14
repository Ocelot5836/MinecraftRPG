package com.ocelot.mod.game.core.tile;

import java.awt.Color;

import javax.annotation.Nullable;

import com.ocelot.api.libs.RenderingHelper;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.gfx.TileRenderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.MathHelper;

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

	public Basic3DTile(int id, String registryName, String unlocalizedName, @Nullable IBlockState state) {
		super(id, registryName, unlocalizedName);
		this.state = state;
	}

	@Override
	public void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks) {
		IBlockState state = this.getState(tileMap, x, y, layer);
		if (state != null) {
			GlStateManager.disableDepth();

			GlStateManager.pushMatrix();
			GlStateManager.translate(renderX + 8, renderY + 11 - layer * 11, 0);

			int light = 0;

			GlStateManager.pushMatrix();
			this.applyLighting();
			GlStateManager.popMatrix();

			GlStateManager.rotate(45, 1, 0, 0);
			GlStateManager.rotate(180, 1, 0, 0);

			TileRenderer.render(renderX, renderY, state, 16);
			GlStateManager.popMatrix();

			if (this.isFullCube(tileMap, x, y, layer) && !this.isTranslucent(tileMap, x, y, layer)) {
				GlStateManager.enableBlend();
				Color color = new Color(0f, 0f, 0f, 0.5f * ((float) (15 - this.getLighting(tileMap, x, y, layer)) / 15f));
				if (color.getAlpha() != 0) {
					RenderingHelper.fillRect(renderX, renderY, renderX + 16, renderY + 11, color.getRGB());
				}
			}

			this.diableLighting();
		}
	}

	protected void applyLighting() {
		GlStateManager.rotate(45, 1, 0, 0);
		RenderHelper.enableGUIStandardItemLighting();
	}

	protected void diableLighting() {
		RenderHelper.disableStandardItemLighting();
	}

	protected int getLighting(TileMap tileMap, int x, int y, int layer) {
		for (int i = layer; i < layer + 8; i++) {
			Tile tile = tileMap.getTile(x, y, i);
			if (tile != null && tile != Tile.AIR && tile.isFullCube(tileMap, x, y, i) && !tile.isTranslucent(tileMap, x, y, i)) {
				return i >= layer ? 15 : (int) (((float) MathHelper.clamp(i, layer, layer + 5) / ((float) layer + 5f)) * 15f);
			}
		}
		return 15;
	}

	@Nullable
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		return state;
	}
}