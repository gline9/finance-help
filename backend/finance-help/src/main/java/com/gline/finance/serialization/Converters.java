package com.gline.finance.serialization;

import java.time.Instant;
import java.util.function.Function;

public class Converters
{
    public static <T> Converter<T, T> identity(Class<T> clazz)
    {
        return new Converter<T, T>() {
            @Override
            public Object convert(Object input) {
                return input;
            }

            @Override
            public T cast(Object obj) {
                return clazz.cast(obj);
            }
        };
    }

    private static <T> Converter<Number, T> fromNumber(Function<Number, T> converter)
    {
        return new Converter<Number, T>()
        {
            @Override
            public T convert(Number input)
            {
                return converter.apply(input);
            }

            @Override
            public Number cast(Object obj)
            {
                return (Number)obj;
            }
        };
    }

    public static Converter<Number, Double> numberDoubleConverter()
    {
        return fromNumber(Number::doubleValue);
    }

    public static Converter<Number, Instant> instantMillisConverter()
    {
        return fromNumber(number -> Instant.ofEpochMilli(number.longValue()));
    }
}
