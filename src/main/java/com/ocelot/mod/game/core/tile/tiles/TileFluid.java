package com.ocelot.mod.game.core.tile.tiles;

import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.TileMap;
import com.ocelot.mod.game.core.tile.Basic3DTile;
import com.ocelot.mod.game.core.tile.property.PropertyBoolean;
import com.ocelot.mod.game.core.tile.property.PropertyInteger;
import com.ocelot.mod.game.core.tile.property.TileStateContainer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

//TODO work on fluid tiles
public class TileFluid extends Basic3DTile {

	public static final PropertyBoolean SOURCE = PropertyBoolean.create("source");
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

	private IBlockState flowState;

	public TileFluid(int id, String registryName, String unlocalizedName, IBlockState state, IBlockState flowState) {
		super(id, registryName, unlocalizedName, state);
		this.flowState = flowState;
	}

	@Override
	public void updateTile(TileMap tileMap, int x, int y, int layer) {

	}

	@Override
	public void render(Gui gui, Minecraft mc, Game game, TileMap tileMap, int x, int y, int layer, double renderX, double renderY, float partialTicks) {
		super.render(gui, mc, game, tileMap, x, y, layer, renderX, renderY, partialTicks);
	}

	public IBlockState getFlowState(TileMap tileMap, int x, int y, int layer) {
		return flowState;
	}

	@Override
	public IBlockState getState(TileMap tileMap, int x, int y, int layer) {
		return tileMap.getValue(SOURCE, x, y, layer) ? super.getState(tileMap, x, y, layer) : this.getFlowState(tileMap, x, y, layer);
	}

	@Override
	public TileStateContainer createContainer() {
		return new TileStateContainer(this, SOURCE, LEVEL);
	}
}