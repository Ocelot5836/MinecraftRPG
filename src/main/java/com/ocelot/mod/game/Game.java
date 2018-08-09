package com.ocelot.mod.game;

import javax.annotation.Nullable;

import com.mrcrayfish.device.core.Laptop;
import com.ocelot.mod.MinecraftRPG;
import com.ocelot.mod.app.ApplicationRPG;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * The main class that begins the required processes and loads the required assets.
 * 
 * @author Ocelot5836
 */
public class Game {

	private static Game instance;

	private ApplicationRPG app;

	public Game() {
		instance = this;
	}

	/**
	 * Initializes the game.
	 * 
	 * @throws Throwable
	 *             just in case something goes wrong so the game can handle it
	 */
	private void init() throws Throwable {
	}

	/**
	 * Launches the game and starts up all the main processes.
	 * 
	 * @param app
	 *            The application that ran the game
	 */
	public void launch(ApplicationRPG app) {
		this.app = app;

		try {
			this.init();
		} catch (Throwable t) {
			this.handleCrash(t);
		}
		MinecraftRPG.logger().info("Starting...");
	}

	/**
	 * Called 20 times per second from the gui that is hosting this game
	 */
	public void update() {
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
	}

	/**
	 * Disposes of all the game's assets so the garbage collector can clear this form memory while not in use.
	 */
	public void dispose() {
		MinecraftRPG.logger().info("Stopping...");

		this.app = null;

		instance = null;
	}

	/**
	 * Called whenever the game wishes to crash.
	 * 
	 * @param t
	 *            The error that was thrown
	 */
	public void handleCrash(Throwable t) {
		MinecraftRPG.logger().catching(t);
		Laptop.getSystem().closeApplication(this.app.getInfo());
	}

	/**
	 * @return The running game instance or null if the game is not running
	 */
	@Nullable
	public static Game getGame() {
		return instance;
	}
}