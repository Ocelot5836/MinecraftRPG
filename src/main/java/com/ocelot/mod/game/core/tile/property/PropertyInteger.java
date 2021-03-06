package com.ocelot.mod.game.core.tile.property;

import com.google.common.base.Optional;
import com.ocelot.mod.game.Game;

public class PropertyInteger extends PropertyBase<Integer> {

	private String name;
	private int minValue;
	private int maxValue;

	private PropertyInteger(String name, int minValue, int maxValue) {
		super(minValue);
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void setValue(Integer value) {
		if (value < minValue || value > maxValue)
			Game.getGame().handleCrash(new IllegalArgumentException("Property attempted to set integer value out of range. value: " + value + ", range: [" + this.minValue + "-" + this.maxValue + "]"), "A tile attempted to set an integer property out of range.");
		super.setValue(value);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void parseValue(String value) {
		this.setValue(Integer.parseInt(value));
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	@Override
	public IProperty<Integer> copy() {
		IProperty<Integer> property = new PropertyInteger(this.name, this.minValue, this.maxValue);
		property.setValue(this.getValue());
		return property;
	}
	
	public static PropertyInteger create(String name, int minValue, int maxValue) {
		return new PropertyInteger(name, minValue, maxValue);
	}
}