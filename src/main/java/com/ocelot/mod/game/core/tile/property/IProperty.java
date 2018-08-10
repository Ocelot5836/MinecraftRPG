package com.ocelot.mod.game.core.tile.property;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows the ability for blocks to store basic values inside of the tile positioning data.
 * 
 * @author Ocelot5836
 *
 * @param <T>
 *            The type of value this property will handle
 */
public interface IProperty<T> {

	/**
	 * @return The property's value
	 */
	T getValue();

	/**
	 * @return The default property's value
	 */
	T getDefaultValue();

	/**
	 * Sets the property value to the one specified.
	 * 
	 * @param value
	 *            The new value
	 */
	void setValue(T value);

	/**
	 * @return The name of the property
	 */
	String getName();

	/**
	 * @return a copy of the property
	 */
	IProperty<T> copy();

}