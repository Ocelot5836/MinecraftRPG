package com.ocelot.mod.game.core.gfx;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mrcrayfish.guns.client.util.RenderUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;

public class TileRenderer {

	private static final Map<Block, ItemStack> STACKS = Maps.newHashMap();

	private TileRenderer() {
	}

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

	public static void render(double x, double y, IBlockState state, double scale) {
		ItemStack stack = STACKS.get(state.getBlock());
		if(stack == null) {
			stack = new ItemStack(state.getBlock());
			STACKS.put(state.getBlock(), stack);
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		RenderUtil.renderModel(Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state), stack);
		GlStateManager.popMatrix();
	}

	public static void dispose() {
		STACKS.clear();
	}
}