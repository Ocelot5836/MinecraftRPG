package com.ocelot.mod.game.core.tile.property;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.Maps;
import com.ocelot.mod.game.Game;
import com.ocelot.mod.game.core.tile.Tile;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * The basic handler for tile properties. Stores all data within a map and allows tiles to read and write to these container that are per tile.
 * 
 * @author Ocelot5836
 */
public class TileStateContainer {

	public static final TileStateContainer NULL = new TileStateContainer(null);

	private Tile tile;
	private Map<String, IProperty> properties = Maps.<String, IProperty>newHashMap();

	/**
	 * Creates a new Tile State Container.
	 * 
	 * @param tile
	 *            The tile to link the properties to
	 * @param properties
	 *            The properties to be bound to the tile
	 */
	public TileStateContainer(Tile tile, IProperty... properties) {
		this.tile = tile;
		for (int i = 0; i < properties.length; i++) {
			this.properties.put(properties[i].getName(), properties[i].copy());
		}
	}

	/**
	 * @return The tile this container is bound to
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @return The properties in the container
	 */
	public Map<String, IProperty> getProperties() {
		return properties;
	}

	/**
	 * @return The number of properties in this container
	 */
	public int getNumberOfProperties() {
		return properties.size();
	}

	/**
	 * Fetches a value from the specified property.
	 * 
	 * @param property
	 *            The property to fetch the value of
	 * @return The value found bound to the property if it exists in the tile state container
	 * @throws Exception
	 *             If the property does not exist in the container
	 */
	public <T> T getValue(IProperty<T> property) {
		if (this == NULL)
			return property.getDefaultValue();
		if (!properties.containsKey(property.getName()))
			Game.getGame().handleCrash(new IllegalArgumentException("Property " + property.getName() + " attempted to be accessed even though it does not exist."), "You cannot get the value of a property that does not exist!");
		return (T) properties.get(property.getName()) == null ? property.getDefaultValue() : (T) properties.get(property.getName()).getValue();
	}

	/**
	 * Sets the specified property's value to the specified value.
	 * 
	 * @param property
	 *            The property to fetch the value of
	 * @param value
	 *            The new value for the property
	 * @throws Exception
	 *             If the property does not exist in the container
	 */
	public <T> TileStateContainer setValue(IProperty<T> property, T value) {
		if (this == NULL)
			return NULL;
		if (!properties.containsKey(property.getName()))
			Game.getGame().handleCrash(new IllegalArgumentException("Property " + property.getName() + " attempted to be set even though it does not exist."), "You cannot set the value of a property that does not exist!");
		properties.get(property.getName()).setValue(value);
		return this;
	}
}