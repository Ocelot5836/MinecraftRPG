package com.ocelot.mod.game.core.entity;

import java.util.UUID;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class GameObject implements INBTSerializable<NBTTagCompound> {

	private Level level;
	private UUID id;
	private double x;
	private double y;
	private double z;
	private double lastX;
	private double lastY;
	private double lastZ;
	private boolean removed;

	public GameObject() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.lastX = 0;
		this.lastY = 0;
		this.lastZ = 0;
		this.removed = false;
	}

	public void update() {
		this.lastX = this.x;
		this.lastY = this.y;
		this.lastZ = this.z;
	}

	public void render(Gui gui, Minecraft mc, Game game, float partialTicks) {
	}

	protected abstract void writeToNBT(NBTTagCompound nbt);

	protected abstract void readFromNBT(NBTTagCompound nbt);

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setUniqueId("id", this.id);
		nbt.setDouble("x", this.x);
		nbt.setDouble("y", this.y);
		nbt.setDouble("z", this.z);
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.id = nbt.getUniqueId("id");
		this.x = nbt.getDouble("x");
		this.y = nbt.getDouble("y");
		this.z = nbt.getDouble("z");
		this.readFromNBT(nbt);
	}

	public void remove() {
		this.removed = true;
	}

	public void onRemove() {
	}

	public boolean isRemoved() {
		return removed;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getLastX() {
		return lastX;
	}

	public double getLastY() {
		return lastY;
	}

	public double getLastZ() {
		return lastZ;
	}
	
	public UUID getId() {
		return id;
	}

	public void setLevel(Level level) {
		this.level = level;
		if (this.id == null) {
			this.id = UUID.randomUUID();
		}
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public void setPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}