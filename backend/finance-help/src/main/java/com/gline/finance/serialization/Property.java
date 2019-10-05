package com.gline.finance.serialization;

public class Property
{
    private Object value;

    private Property(Object value)
    {
        this.value = value;
    }

    public static Property forValue(Object value)
    {
        return new Property(value);
    }

    public <I, O> O convert(Converter<I, O> converter)
    {
        return converter.convert(converter.cast(value));
    }

    public Class<?> getValueClass()
    {
        return value.getClass();
    }

    public boolean isNull()
    {
        return null == value;
    }

    public Object getRawValue()
    {
        return value;
    }
}
