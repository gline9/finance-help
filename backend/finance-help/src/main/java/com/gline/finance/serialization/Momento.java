package com.gline.finance.serialization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Momento
{
    private final Map<String, Property> propertyMap;

    private Momento(Map<String, Property> propertyMap)
    {
        this.propertyMap = propertyMap;
    }

    public static Momento emptyBean()
    {
        return new Momento(new HashMap<>());
    }

    public static Momento fromMap(Map<String, Object> propertyMap)
    {
        Momento momento = emptyBean();

        for (Map.Entry<String, Object> entry : propertyMap.entrySet())
        {
            momento.setProperty(entry.getKey(), Property.forValue(entry.getValue()));
        }

        return momento;
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
