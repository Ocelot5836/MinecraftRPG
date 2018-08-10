package com.ocelot.mod.game.core.tile.property;

public class PropertyBoolean extends PropertyBase<Boolean> {

	private String name;

	private PropertyBoolean(String name) {
		this.name = name;
		this.value = false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static PropertyBoolean create(String name) {
		return new PropertyBoolean(name);
	}

	@Override
	public IProperty<Boolean> copy() {
		IProperty<Boolean> property = new PropertyBoolean(this.name);
		property.setValue(this.getValue());
		return property;
	}
}