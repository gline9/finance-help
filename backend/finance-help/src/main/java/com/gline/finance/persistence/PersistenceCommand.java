package com.gline.finance.persistence;

import com.gline.finance.serialization.Converters;
import com.gline.finance.serialization.Memento;

import java.io.File;
import java.io.IOException;

public class PersistenceCommand
{
    private static final String FILE_NAME_FIELD = "fileName";
    private static final String COMMAND_TYPE_FIELD = "commandType";

    private final String fileName;
    private final PersistenceCommandType type;

    private PersistenceCommand(Memento memento)
    {
        this.fileName = memento.getValue(FILE_NAME_FIELD, Converters.identity(String.class));
        this.type = memento.getValue(COMMAND_TYPE_FIELD, Converters.stringEnumConverter(PersistenceCommandType.class));
    }

    public static PersistenceCommand fromMemento(Memento memento)
    {
        return new PersistenceCommand(memento);
    }

    public void execute(PersistenceManager manager) throws IOException
    {
        File file = new File(fileName);

        if (PersistenceCommandType.LOAD == type)
        {
            manager.readFromFile(file);
        }
        else if (PersistenceCommandType.SAVE == type)
        {
            manager.persistToFile(file);
        }
    }
}
