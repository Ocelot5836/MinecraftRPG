package com.ocelot.mod.game.core.tile.property;

/**
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
	 * @return The property's default value
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
	 * Sets the property value based on what the string contains.
	 * 
	 * @param value
	 *            The new value in string form
	 * @throws Exception
	 *             Just in case something is wrong or the parameter is incorrect
	 */
	void parseValue(String value) throws Exception;

	/**
	 * @return The name of the property
	 */
	String getName();

	/**
	 * @return a copy of the property
	 */
	IProperty<T> copy();

}