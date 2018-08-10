package com.ocelot.mod.game.core.tile;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.gfx.TileRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

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
public class BasicTile extends Tile {

	private TextureAtlasSprite sprite;

	public BasicTile(int id, String registryName, String unlocalizedName, TextureAtlasSprite sprite) {
		super(id, registryName, unlocalizedName);
		this.sprite = sprite;
	}

	public BasicTile(int id, String registryName, String unlocalizedName, ItemStack stack) {
		this(id, registryName, unlocalizedName, Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack).getParticleTexture());
	}

	@Override
	public void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks) {
		TileRenderer.render(x - 8, y - 4, 16, 16, this.sprite);
	}
}