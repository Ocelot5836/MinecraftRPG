package com.ocelot.mod.game.core.tile.property;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * The default implementation of {@link IProperty}.
 * 
 * @author Ocelot5836
 *
 * @param <T>
 *            The type of value this property will handle
 */
public abstract class PropertyBase<T> implements IProperty<T> {

	/** The value being stored. */
	private T value;
	
	/** The default value being stored. */
	private final T defaultValue;
	
	public PropertyBase(T defaultValue) {
		this.value = defaultValue;
		this.defaultValue = defaultValue;
	}

	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Property-" + this.getValue();
	}
}