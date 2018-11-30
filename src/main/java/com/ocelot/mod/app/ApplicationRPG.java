package com.ocelot.mod.app;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.util.GLHelper;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public class ApplicationRPG extends Application {

	private static ApplicationRPG app;

	private Game game;

	public ApplicationRPG() {
		app = this;
	}

	@Override
	public void init(@Nullable NBTTagCompound intent) {
		this.game = new Game().launch(this);

		this.setDefaultWidth(362);
		this.setDefaultHeight(164);
	}

	@Override
	public void load(NBTTagCompound nbt) {
	}

	@Override
	public void save(NBTTagCompound nbt) {
	}

	@Override
	public void onTick() {
		super.onTick();
		this.game.update();
	}

	@Override
	public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
		GlStateManager.pushMatrix();
		{
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			GLHelper.pushScissor(x, y, this.getWidth(), this.getHeight());
			GlStateManager.translate(x, y, 50);
			this.game.render(laptop, mc, mouseX - x, mouseY - y, partialTicks);
			mc.fontRenderer.drawString(Minecraft.getDebugFPS() + " FPS", 2, 2, 0xffffffff, true);
			GLHelper.popScissor();
		}
		GlStateManager.popMatrix();
		super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
	}

	@Override
	public void onClose() {
		super.onClose();
		Game.getGame().dispose();
		TileMap.Listener.clear();
		app = null;
	}

	public static ApplicationRPG getApp() {
		return app;
	}
}