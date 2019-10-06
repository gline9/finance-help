package com.gline.finance.serialization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Memento
{
    private final Map<String, Property> propertyMap;

    private Memento(Map<String, Property> propertyMap)
    {
        this.propertyMap = propertyMap;
    }

    public static Memento emptyBean()
    {
        return new Memento(new HashMap<>());
    }

    public static Memento fromMap(Map<String, Object> propertyMap)
    {
        Memento memento = emptyBean();

        for (Map.Entry<String, Object> entry : propertyMap.entrySet())
        {
            memento.setProperty(entry.getKey(), Property.forValue(entry.getValue()));
        }

        return memento;
    }

    public void setProperty(String key, Object value)
    {
        propertyMap.put(key, Property.forValue(value));
    }

    public void setProperty(String key, Property property)
    {
        propertyMap.put(key, property);
    }

    public <I> void setProperty(String key, I value, Converter<I, ?> converter)
    {
        propertyMap.put(key, Property.forValue(converter.convert(value)));
    }

    public <I, O> O getValue(String key, Converter<I, O> converter)
    {
        return getValue(key, converter, null);
    }

    public <I, O> O getValue(String key, Converter<I, O> converter, Supplier<O> defaultSupplier)
    {
        if (!propertyMap.containsKey(key))
        {
            if (null != defaultSupplier)
            {
                return defaultSupplier.get();
            }
            throw new IllegalArgumentException("Missing property: '" + key + "'");
        }

        Property property = propertyMap.get(key);

        try
        {
            return property.convert(converter);
        }
        catch (ClassCastException ex)
        {
            throw new IllegalArgumentException("Incompatible class " + property.getValueClass() + " for conversion of key '" + key + "'");
        }
    }

    public Set<String> getKeys()
    {
        return Collections.unmodifiableSet(propertyMap.keySet());
    }

    public Property getProperty(String key)
    {
        return propertyMap.get(key);
    }

}
