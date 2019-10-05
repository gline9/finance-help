package com.gline.finance.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

public class MomentoJsonDeserializer extends JsonDeserializer<Momento>
{
    @Override
    @SuppressWarnings("unchecked")
    public Momento deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        return Momento.fromMap(ctxt.readValue(p, Map.class));
    }
}
