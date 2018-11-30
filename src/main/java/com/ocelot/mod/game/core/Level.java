package com.ocelot.mod.game.core;

import java.util.List;

import com.ocelot.mod.MinecraftRPG;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.entity.GameObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class Level {

	private List<GameObject> objects;
	private TileMap map;

	public Level(ResourceLocation mapLocation) {
		this.map = new TileMap(mapLocation);
	}

	public void update() {
		this.map.update();
		for (int i = 0; i < this.objects.size(); i++) {
			GameObject object = this.objects.get(i);
			object.update();
			if (object.isRemoved()) {
				this.objects.remove(i).onRemove();
				i--;
			}
		}
	}

	public void render(Gui gui, Minecraft mc, Game game, float partialTicks) {
		this.map.render(gui, mc, game, partialTicks);
		for (int i = 0; i < this.objects.size(); i++) {
			GameObject object = this.objects.get(i);
			object.render(gui, mc, game, partialTicks);
		}
	}

	public void delete() {
		this.objects.clear();
		this.objects = null;
		this.map = null;
	}

	public void add(GameObject object) {
		if (!object.isRemoved()) {
			this.objects.add(object);
		} else {
			MinecraftRPG.logger().warn("Attempted to add entity \'" + object.getClass().getName() + "\' but it was already marked as removed");
		}
	}

	public TileMap getMap() {
		return map;
	}

	public double getXOffset() {
		return map.getXOffset();
	}

	public double getYOffset() {
		return map.getYOffset();
	}

	public void setOffset(double xOffset, double yOffset) {
		this.map.setOffset(xOffset, yOffset);
	}

	public void addOffset(double xOffset, double yOffset) {
		this.map.setOffset(this.map.getXOffset() + xOffset, this.map.getYOffset() + yOffset);
	}
}