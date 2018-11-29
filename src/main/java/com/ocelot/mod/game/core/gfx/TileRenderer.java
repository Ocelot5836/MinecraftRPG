package com.ocelot.mod.game.core.gfx;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ocelot.mod.lib.RenderUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows the ability to render tiles to the screen.
 * 
 * @author Ocelot5836
 */
public class TileRenderer {

	private static final Map<Block, ItemStack> STACKS = Maps.newHashMap();

	private TileRenderer() {
	}

	/**
	 * Renders a flat colored quad to the screen.
	 * 
	 * @param x
	 *            The x position to render at
	 * @param y
	 *            The y position to render at
	 * @param width
	 *            The width of the quad
	 * @param height
	 *            The height of the quad
	 * @param color
	 *            The hex/int color of the quad
	 */
	public static void render(double x, double y, double width, double height, int color) {
		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(x, y + Math.abs(height), 0.0D).endVertex();
		bufferbuilder.pos(x + Math.abs(width), y + Math.abs(height), 0.0D).endVertex();
		bufferbuilder.pos(x + Math.abs(width), y, 0.0D).endVertex();
		bufferbuilder.pos(x, y, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	/**
	 * Renders a flat textured quad to the screen.
	 * 
	 * @param x
	 *            The x position to render at
	 * @param y
	 *            The y position to render at
	 * @param width
	 *            The width of the quad
	 * @param height
	 *            The height of the quad
	 * @param sprite
	 *            The texture to use when rendering the quad
	 */
	public static void render(double x, double y, double width, double height, TextureAtlasSprite sprite) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (x + 0), (double) (y + height), 0).tex((double) sprite.getMinU(), (double) sprite.getMaxV()).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), 0).tex((double) sprite.getMaxU(), (double) sprite.getMaxV()).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + 0), 0).tex((double) sprite.getMaxU(), (double) sprite.getMinV()).endVertex();
		bufferbuilder.pos((double) (x + 0), (double) (y + 0), 0).tex((double) sprite.getMinU(), (double) sprite.getMinV()).endVertex();
		tessellator.draw();
	}

	/**
	 * Renders a block state to the screen.
	 * 
	 * @param x
	 *            The x position to render the state at
	 * @param y
	 *            The y position to render the state at
	 * @param state
	 *            The state to render
	 * @param scale
	 *            The scale of the state
	 */
	public static void render(double x, double y, IBlockState state, double scale) {
		ItemStack stack = STACKS.get(state.getBlock());
		if (stack == null) {
			stack = new ItemStack(state.getBlock());
			STACKS.put(state.getBlock(), stack);
		}

		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		RenderUtil.renderModel(Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state), stack);
		GlStateManager.popMatrix();
	}

	/**
	 * Clears any memory that was used while rendering.
	 */
	public static void dispose() {
		STACKS.clear();
	}
}