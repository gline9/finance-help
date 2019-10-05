package com.gline.finance.serialization;

public interface Converter<I, O>
{
    O convert(I input);

    I cast(Object obj);
}
