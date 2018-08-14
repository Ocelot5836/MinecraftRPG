package com.ocelot.mod.game.core.tile.property;

public class PropertyBoolean extends PropertyBase<Boolean> {

	private String name;

	private PropertyBoolean(String name) {
		super(false);
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void parseValue(String value) throws Exception {
		this.setValue(Boolean.parseBoolean(value));
	}

	@Override
	public IProperty<Boolean> copy() {
		IProperty<Boolean> property = new PropertyBoolean(this.name);
		property.setValue(this.getValue());
		return property;
	}

	public static PropertyBoolean create(String name) {
		return new PropertyBoolean(name);
	}
}