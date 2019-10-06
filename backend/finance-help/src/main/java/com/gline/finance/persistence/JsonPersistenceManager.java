package com.gline.finance.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.MementoJsonDeserializer;
import com.gline.finance.serialization.MementoJsonSerializer;
import com.gline.finance.serialization.Serializable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonPersistenceManager implements PersistenceManager
{
    private final Map<String, PersistableStore> storeMap = new HashMap<>();
    private final ObjectMapper objectMapper;

    public JsonPersistenceManager()
    {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Memento.class, new MementoJsonDeserializer());
        module.addSerializer(Serializable.class, new MementoJsonSerializer());
        objectMapper.registerModule(module);
    }


    @Override
    public <T extends PersistableStore> T registerStore(T store)
    {
        String name = store.getRepoPersistenceName();

        if (storeMap.containsKey(name))
        {
            throw new IllegalArgumentException("Cannot register duplicate repo for name " + name);
        }

        storeMap.put(name, store);

        return store;
    }

    @Override
    public void readFromFile(File file) throws IOException
    {
        Map<String, List<Memento>> values = objectMapper.readValue(file, new TypeReference<Map<String, List<Memento>>>(){});

        for (Map.Entry<String, PersistableStore> entry : storeMap.entrySet())
        {
            String key = entry.getKey();
            PersistableStore store = entry.getValue();

            store.setFromMementos(values.getOrDefault(key, Collections.emptyList()));
        }
    }

    @Override
    public void persistToFile(File file) throws IOException
    {
        Map<String, List<Serializable>> value = new HashMap<>(storeMap.size());

        storeMap.forEach((key, store) -> value.put(key, store.getSerializableData()));

        objectMapper.writeValue(file, value);
    }

}
