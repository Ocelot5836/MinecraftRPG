package com.ocelot.mod.game.core.tile.property;

import com.ocelot.mod.game.Game;

public class PropertyDouble extends PropertyBase<Double> {

	private String name;
	private double minValue;
	private double maxValue;

	private PropertyDouble(String name, double minValue, double maxValue) {
		super(minValue);
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void setValue(Double value) {
		if (value < minValue || value > maxValue)
			Game.getGame().handleCrash(new IllegalArgumentException("Property attempted to set double value out of range. value: " + value + ", range: [" + this.minValue + "-" + this.maxValue + "]"), "A tile attempted to set a double property out of range.");
		super.setValue(value);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void parseValue(String value) throws Exception {
		this.setValue(Double.parseDouble(value));
	}

	public double getMinValue() {
		return minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	@Override
	public IProperty<Double> copy() {
		IProperty<Double> property = new PropertyDouble(this.name, this.minValue, this.maxValue);
		property.setValue(this.getValue());
		return property;
	}

	public static PropertyDouble create(String name, double minValue, double maxValue) {
		return new PropertyDouble(name, minValue, maxValue);
	}
}