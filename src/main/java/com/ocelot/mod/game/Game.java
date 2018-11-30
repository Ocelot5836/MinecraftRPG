package com.ocelot.mod.game;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.mrcrayfish.device.core.Laptop;
import com.ocelot.mod.MinecraftRPG;
import com.ocelot.mod.app.ApplicationRPG;
import com.ocelot.mod.game.core.Level;
import com.ocelot.mod.game.core.gfx.TileRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 * The main class that begins the required processes and loads the required assets.
 * 
 * @author Ocelot5836
 */
public class Game {

	private static Game instance;
	private ApplicationRPG app;

	/** temporary code */
	private Level level;

	/**
	 * Initializes the game.
	 */
	private void init() throws Throwable {
		this.level = new Level(new ResourceLocation(MinecraftRPG.MOD_ID, "test"));
	}

	/**
	 * Launches the game and starts up all the main processes.
	 * 
	 * @param app
	 *            The application that ran the game
	 * @return This game's instance
	 */
	public Game launch(ApplicationRPG app) {
		instance = this;
		this.app = app;

		try {
			this.init();
		} catch (Throwable t) {
			this.handleCrash(t, "Error while initialization");
		}
		MinecraftRPG.logger().info("Starting...");

		return this;
	}

	/**
	 * Called 20 times per second from the gui that is hosting this game.
	 */
	public void update() {
		try {
			this.level.update();

			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				this.level.addOffset(0, -5);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				this.level.addOffset(0, 5);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				this.level.addOffset(-5, 0);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				this.level.addOffset(5, 0);
			}
		} catch (GameCrashException e) {
			handleCrash(e.getCause(), e.getMessage());
		} catch (Exception e) {
			handleCrash(e);
		}
	}

	/**
	 * Called to render any objects to the screen.
	 * 
	 * @param gui
	 *            The gui that is rendering this game
	 * @param mc
	 *            A Minecraft instance
	 * @param mouseX
	 *            The x position of the mouse
	 * @param mouseY
	 *            The y position of the mouse
	 * @param partialTicks
	 *            The percentage from last update and this update
	 */
	public void render(Gui gui, Minecraft mc, float mouseX, float mouseY, float partialTicks) {
		try {
			this.level.render(gui, mc, this, partialTicks);
		} catch (GameCrashException e) {
			handleCrash(e.getCause(), e.getMessage());
		} catch (Exception e) {
			handleCrash(e);
		}
	}

	/**
	 * Disposes of all the game's assets so the garbage collector can clear this from memory while not in use.
	 */
	public void dispose() {
		MinecraftRPG.logger().info("Stopping...");
		TileRenderer.dispose();
		instance = null;
		this.app = null;
	}

	/**
	 * Called whenever the game wishes to crash.
	 * 
	 * @param t
	 *            The error that was thrown
	 */
	public void handleCrash(Throwable t) {
		this.handleCrash(t, "No information provided");
	}

	/**
	 * Called whenever the game wishes to crash.
	 * 
	 * @param t
	 *            The error that was thrown
	 * @param info
	 *            The reason why the game crashed
	 */
	public void handleCrash(Throwable t, String info) {
		MinecraftRPG.logger().error(info, t);
		Laptop.getSystem().closeApplication(this.app.getInfo());
	}

	/**
	 * @return The application instance
	 */
	public ApplicationRPG getApp() {
		return app;
	}

	/**
	 * @return The running game instance or null if the game is not running
	 */
	@Nullable
	public static Game getGame() {
		return instance;
	}
}