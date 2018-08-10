package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.api.utils.TextureUtils;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.tileentity.TileEntity;
import com.ocelot.mod.game.core.tile.tileentity.TileEntityEnchantmentTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEnchantmentTable extends Basic3DTile {

	private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
	private final ModelBook modelBook = new ModelBook();

	public TileEnchantmentTable(int id) {
		super(id, "enchantment_table", "enchantment_table", Blocks.ENCHANTING_TABLE.getDefaultState());
	}

	@Override
	public void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks) {
		super.render(gui, mc, game, tileMap, x, y, layer, renderX, renderY, partialTicks);

		if (tileMap.getTileEntity(x, y, layer) instanceof TileEntityEnchantmentTable) {
			TileEntityEnchantmentTable te = (TileEntityEnchantmentTable) tileMap.getTileEntity(x, y, layer);

			GlStateManager.enableDepth();

			GlStateManager.pushMatrix();
			GlStateManager.translate(renderX + 8, renderY + 8 - layer * 11, 0);

			GlStateManager.rotate(-45, 1, 0, 0);
			GlStateManager.rotate(180, 1, 0, 0);

			GlStateManager.translate(0, 5, 0);

			GlStateManager.scale(20, 20, 20);

			{
				GlStateManager.pushMatrix();

				float f = (float) te.tickCount + partialTicks;
				GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
				float f1;

				for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
					;
				}

				while (f1 < -(float) Math.PI) {
					f1 += ((float) Math.PI * 2F);
				}

				float f2 = te.bookRotationPrev + f1 * partialTicks;
				GlStateManager.rotate(-f2 * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
				TextureUtils.bindTexture(TEXTURE_BOOK);
				float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
				float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
				f3 = (f3 - (float) MathHelper.fastFloor((double) f3)) * 1.6F - 0.3F;
				f4 = (f4 - (float) MathHelper.fastFloor((double) f4)) * 1.6F - 0.3F;

				if (f3 < 0.0F) {
					f3 = 0.0F;
				}

				if (f4 < 0.0F) {
					f4 = 0.0F;
				}

				if (f3 > 1.0F) {
					f3 = 1.0F;
				}

				if (f4 > 1.0F) {
					f4 = 1.0F;
				}

				float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
				GlStateManager.enableCull();
				this.modelBook.render((Entity) null, f, f3, f4, f5, 0.0F, 0.0625F);
				GlStateManager.popMatrix();
			}

			GlStateManager.popMatrix();
		}
	}

	@Override
	public TileEntity createNewTileEntity(TileMap tileMap, int x, int y, int layer) {
		return new TileEntityEnchantmentTable(tileMap, x, y, layer);
	}
}