package com.ocelot.mod.game.core.tile.property;

public class PropertyString extends PropertyBase<String> {

	private String name;
	private String defaultValue;

	private PropertyString(String name, String defaultValue) {
		super(defaultValue);
		this.name = name;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static PropertyString create(String name, String defaultValue) {
		return new PropertyString(name, defaultValue);
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public IProperty<String> copy() {
		IProperty<String> property = new PropertyString(this.name, this.defaultValue);
		property.setValue(this.getValue());
		return property;
	}
}