package com.ocelot.mod.lib;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;

import com.ocelot.mod.MinecraftRPG;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Lib {

	/**
	 * Loads text to a string from a resource location.
	 * 
	 * @param location
	 *            The location of the text file
	 * @return The text compressed into a string
	 */
	@SideOnly(Side.CLIENT)
	@Nullable
	public static String loadTextToString(ResourceLocation location) {
		try {
			return IOUtils.toString(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream(), Charset.defaultCharset());
		} catch (IOException e) {
			MinecraftRPG.logger().warn("Could not load text " + location + ". Could cause issues later on.");
		}
		return null;
	}
}