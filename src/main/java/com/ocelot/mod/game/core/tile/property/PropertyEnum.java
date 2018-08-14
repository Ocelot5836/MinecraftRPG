package com.ocelot.mod.game.core.tile.property;

import java.util.Collection;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ocelot.mod.game.Game;

import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyBase<T> {

	private String name;
	private final Class<T> valueClass;
	private final ImmutableSet<T> allowedValues;
	private final Map<String, T> nameToValue = Maps.<String, T>newHashMap();

	protected PropertyEnum(String name, T defaultValue, Class<T> valueClass, Collection<T> allowedValues) {
		super(defaultValue);
		this.name = name;
		this.valueClass = valueClass;
		this.allowedValues = ImmutableSet.copyOf(allowedValues);

		for (T t : allowedValues) {
			String s = ((IStringSerializable) t).getName();

			if (this.nameToValue.containsKey(s)) {
				Game.getGame().handleCrash(new IllegalArgumentException("Multiple values have the same name '" + s + "'"));
			}

			this.nameToValue.put(s, t);
		}
	}

	public Collection<T> getAllowedValues() {
		return this.allowedValues;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void parseValue(String value) {
		T obj = this.nameToValue.get(value);
		if (obj != null) {
			this.setValue(obj);
		}
	}

	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ instanceof PropertyEnum && super.equals(p_equals_1_)) {
			PropertyEnum<?> propertyenum = (PropertyEnum) p_equals_1_;
			return this.allowedValues.equals(propertyenum.allowedValues) && this.nameToValue.equals(propertyenum.nameToValue);
		} else {
			return false;
		}
	}

	public int hashCode() {
		int i = super.hashCode();
		i = 31 * i + this.allowedValues.hashCode();
		i = 31 * i + this.nameToValue.hashCode();
		return i;
	}

	public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, T defaultValue, Class<T> clazz) {
		return create(name, defaultValue, clazz, Predicates.alwaysTrue());
	}

	public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, T defaultValue, Class<T> clazz, Predicate<T> filter) {
		return create(name, defaultValue, clazz, Collections2.filter(Lists.newArrayList(clazz.getEnumConstants()), filter));
	}

	public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, T defaultValue, Class<T> clazz, T... values) {
		return create(name, defaultValue, clazz, Lists.newArrayList(values));
	}

	public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, T defaultValue, Class<T> clazz, Collection<T> values) {
		return new PropertyEnum<T>(name, defaultValue, clazz, values);
	}

	@Override
	public IProperty<T> copy() {
		IProperty<T> property = new PropertyEnum(this.name, this.getDefaultValue(), this.valueClass, this.allowedValues);
		property.setValue(this.getValue());
		return property;
	}
}