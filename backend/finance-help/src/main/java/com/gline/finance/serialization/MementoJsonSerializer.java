package com.gline.finance.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MementoJsonSerializer extends JsonSerializer<Serializable>
{
    @Override
    public void serialize(Serializable serializable, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        Memento value = serializable.serializeToMemento();
        gen.writeStartObject();
        for (String key : value.getKeys())
        {
            Property property = value.getProperty(key);

            gen.writeObjectField(key, property.getRawValue());
        }
        gen.writeEndObject();
    }
}
