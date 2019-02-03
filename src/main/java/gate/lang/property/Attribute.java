package gate.lang.property;

import gate.constraint.Constraint;
import gate.converter.Converter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface Attribute
{

	public Type getGenericType();

	public Type getElementType();

	public Class<?> getRawType();

	public Object getValue(Object object);

	public void setValue(Object object, Object value);

	public Object forceValue(Object object);

	default boolean isEntityId()
	{
		return false;
	}

	default Class<?> getElementRawType()
	{
		Type type = getElementType();
		if (type instanceof Class<?>)
			return (Class<?>) type;
		if (type instanceof ParameterizedType)
			return (Class<?>) ((ParameterizedType) type).getRawType();
		else if (type instanceof GenericArrayType)
			return Array.newInstance((Class<?>) ((ParameterizedType) ((GenericArrayType) type).getGenericComponentType()).getRawType(), 0).getClass();
		else
			return null;
	}

	default Object createInstance(Class<?> type)
	{
		try
		{
			if (type == List.class)
				return new ArrayList<>();
			else if (type == Collection.class)
				return new ArrayList<>();
			else if (type == Set.class)
				return new HashSet<>();
			else if (type == Map.class)
				return new HashMap<>();

			Constructor constructor = Stream.of(type.getConstructors())
				.filter(e -> e.getParameters().length == 0).findAny().orElse(null);
			if (constructor == null)
				throw new PropertyError("No default constructor found in %s.", type.getName());
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (InstantiationException
			| IllegalAccessException
			| IllegalArgumentException
			| InvocationTargetException e)
		{
			throw new PropertyError("Error trying to create a instance of %s.", type.getName());
		}

	}

	public default boolean getBoolean(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a boolean value from a non boolean attribute");
	}

	public default void setBoolean(Object object, boolean value)
	{
		throw new UnsupportedOperationException("Attempt to write a boolean value to a non boolean attribute");
	}

	public default char getChar(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a char value from a non char attribute");
	}

	public default void setChar(Object object, char value)
	{
		throw new UnsupportedOperationException("Attempt to write a char value to a non char attribute");
	}

	public default byte getByte(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a byte value from a non byte attribute");
	}

	public default void setByte(Object object, byte value)
	{
		throw new UnsupportedOperationException("Attempt to write a byte value to a non byte attribute");
	}

	public default short getShort(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a short value from a non short attribute");
	}

	public default void setShort(Object object, short value)
	{
		throw new UnsupportedOperationException("Attempt to write a short value to a non short attribute");
	}

	public default int getInt(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read an int value from a non int attribute");
	}

	public default void setInt(Object object, int value)
	{
		throw new UnsupportedOperationException("Attempt to write an int value to a non int attribute");
	}

	public default long getLong(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a long value from a non long attribute");
	}

	public default void setLong(Object object, long value)
	{
		throw new UnsupportedOperationException("Attempt to write a long value to a non long attribute");
	}

	public default float getFloat(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a float value from a non float attribute");
	}

	public default void setFloat(Object object, float value)
	{
		throw new UnsupportedOperationException("Attempt to write a float value to a non float attribute");
	}

	public default double getDouble(Object object)
	{
		throw new UnsupportedOperationException("Attempt to read a double value from a non double attribute");
	}

	public default void setDouble(Object object, double value)
	{
		throw new UnsupportedOperationException("Attempt to write a double value to a non double attribute");
	}

	default String getName()
	{
		return null;
	}

	default String getDescription()
	{
		return null;
	}

	default String getPlaceholder()
	{
		return null;
	}

	default String getMask()
	{
		return null;
	}

	default Collection<Constraint.Implementation> getConstraints()
	{
		return Collections.emptyList();
	}

	default String getColumnName()
	{
		return null;
	}

	default String getTableName()
	{
		return Entity.getTableName(getRawType());
	}

	default String getFullTableName()
	{
		return Entity.getFullTableName(getRawType());
	}

	default boolean isEntity()
	{
		return Entity.isEntity(getRawType());
	}

	default Converter getConverter()
	{
		return Converter.getConverter(getRawType());
	}
}
