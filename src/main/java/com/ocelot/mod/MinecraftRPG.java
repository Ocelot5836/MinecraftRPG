package com.ocelot.mod;

import org.apache.logging.log4j.Logger;

import com.mrcrayfish.device.Reference;
import com.mrcrayfish.device.api.ApplicationManager;
import com.mrcrayfish.device.programs.ApplicationBoatRacers;
import com.ocelot.mod.app.ApplicationRPG;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * The main mod class.
 * 
 * @author Ocelot5836
 */
@Mod(modid = MinecraftRPG.MOD_ID, version = MinecraftRPG.VERSION, acceptedMinecraftVersions = "[1.12,1.12.2]", useMetadata = true)
public class MinecraftRPG {

	/** The mod id */
	public static final String MOD_ID = "mrpg";
	/** The current version of the mod */
	public static final String VERSION = "0.0.1";
	/** The id for the rpg app */
	public static final ResourceLocation GAME_ID = new ResourceLocation(MOD_ID, "rpg");

	/** The mod's instance. Probably not too useful but might as well have it */
	@Instance(MOD_ID)
	public static MinecraftRPG instance;

	/** The mod's logger */
	private static Logger logger;

	@EventHandler
	public void pre(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ApplicationManager.registerApplication(GAME_ID, ApplicationRPG.class);
	}

	@EventHandler
	public void post(FMLPostInitializationEvent event) {
	}

	/**
	 * @return A logger that uses the mod id as the name
	 */
	public static Logger logger() {
		return logger;
	}

	/**
	 * @return Whether or not the mod is in a deobfuscated environment
	 */
	public static boolean isDebug() {
		return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}
}