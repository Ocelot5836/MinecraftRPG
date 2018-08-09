package com.ocelot.mod.game.core.tile;

import com.ocelot.mod.game.Game;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class Tile {

	public static final Tile[] TILES = new Tile[256];

	private int id;
	private String registryName;
	private String unlocalizedName;

	public Tile(int id, String registryName, String unlocalizedName) {
		this.id = id;
		if (TILES[id] != null) {
			throw new RuntimeException("Duplicate tile id for tile \'" + registryName + "\'");
		} else {
			TILES[id] = this;
		}

		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
	}

	public abstract void update();

	public abstract void render(Gui gui, Minecraft mc, Game game, double x, double y, float partialTicks);

	public int getId() {
		return id;
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
}