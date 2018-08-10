package com.ocelot.mod.app;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import net.minecraft.nbt.NBTTagCompound;

public class ApplicationLevelCreator extends Application {

	private static ApplicationLevelCreator app;
	
	private Layout mainLayout;
	
	public ApplicationLevelCreator() {
		app = this;
	}

	@Override
	public void init(NBTTagCompound intent) {

	}

	@Override
	public void load(NBTTagCompound nbt) {

	}

	@Override
	public void save(NBTTagCompound nbt) {

	}
	
	@Override
	public void onClose() {
		super.onClose();
		app = null;
	}
	
	public static ApplicationLevelCreator getApp() {
		return app;
	}
}