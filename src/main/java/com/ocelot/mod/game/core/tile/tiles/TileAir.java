package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TileAir extends Tile {

	public TileAir(int id) {
		super(id, "air", "air");
	}

	@Override
	public void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks) {
	}
}