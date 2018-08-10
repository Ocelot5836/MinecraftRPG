package com.ocelot.mod.game.core.tile.property;

import com.ocelot.mod.game.Game;

public class PropertyInteger extends PropertyBase<Integer> {

	private String name;
	private int minValue;
	private int maxValue;

	private PropertyInteger(String name, int minValue, int maxValue) {
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = 0;
	}
	
	@Override
	public void setValue(Integer value) {
		if(value < minValue || value > maxValue)
			Game.getGame().handleCrash(new IllegalArgumentException("Property attempted to set integer value out of range. value: " + value + ", range: [" + this.minValue + "-" + this.maxValue + "]"), "A tile attempted to set an integer property out of range.");
		super.setValue(value);
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public static PropertyInteger create(String name, int minValue, int maxValue) {
		return new PropertyInteger(name, minValue, maxValue);
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
}