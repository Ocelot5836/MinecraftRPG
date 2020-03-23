package com.ocelot.mod.game.core.tile;

import javax.annotation.Nullable;

import org.lwjgl.util.glu.Project;

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
 * A basic implementation of {@link Tile} that uses an {@link IBlockState} to render.
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

			this.applyLighting();

			GlStateManager.rotate(45, 1, 0, 0);
			GlStateManager.rotate(180, 1, 0, 0);

			GlStateManager.matrixMode(5889);
			GlStateManager.pushMatrix();
			GlStateManager.loadIdentity();
	        Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
			GlStateManager.matrixMode(5888);
			GlStateManager.pushMatrix();
			GlStateManager.loadIdentity();
			
//			if (this.isFullCube(tileMap, x, y, layer) && !this.isTranslucent(tileMap, x, y, layer))
//				GlStateManager.color(0.5f * ((float) (15 - this.getLighting(tileMap, x, y, layer)) / 15f) / 255f, 0.5f * ((float) (15 - this.getLighting(tileMap, x, y, layer)) / 15f) / 255f, 0.5f * ((float) (15 - this.getLighting(tileMap, x, y, layer)) / 15f) / 255f);
			TileRenderer.render(renderX, renderY, state, 16);

	        GlStateManager.matrixMode(5889);
	        GlStateManager.popMatrix();
	        GlStateManager.matrixMode(5888);
	        GlStateManager.popMatrix();

			this.diableLighting();
			
			GlStateManager.popMatrix();
		}
	}

	/**
	 * Applies the standard quad lighting.
	 */
	protected void applyLighting() {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(45, 1, 0, 0);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.popMatrix();
	}

	/**
	 * Disables all lighting.
	 */
	protected void diableLighting() {
		RenderHelper.disableStandardItemLighting();
	}

	protected int getLighting(TileMap tileMap, int x, int y, int layer) {
		for (int i = layer; i < layer + 8; i++)
		{
			Tile tile = tileMap.getTile(x, y, i);
			if (tile != null && tile != Tile.AIR && tile.isFullCube(tileMap, x, y, i) && !tile.isTranslucent(tileMap, x, y, i))
			{
				return i >= layer ? 15 : (int) (((float) MathHelper.clamp(i, layer, layer + 5) / ((float) layer + 5f)) * 15f);
			}
		}
		return 15;
	}
	
	@Override
	public boolean isFullCube(TileMap tileMap, int x, int y, int layer) {
		IBlockState state = this.getState(tileMap, x, y, layer);
		return state == null ? false : state.isFullCube();
	}
	
	@Override
	public boolean isTranslucent(TileMap tileMap, int x, int y, int layer) {
		IBlockState state = this.getState(tileMap, x, y, layer);
		return state == null ? false : state.isTranslucent();
	}

	/**
	 * Used to determine what state will be rendered. Return <code>null</code> to cancel all rendering.
	 * 
	 * @param tileMap
	 *            The map this is being rendered into
	 * @param x
	 *            The x position of the tile
	 * @param y
	 *            The y position of the tile
	 * @param layer
	 *            The layer of the tile
	 * @return The state that will be used to render or null.
	 */
	@Nullable
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		return state;
	}
}