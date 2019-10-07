package com.gline.finance.persistence;

import java.io.File;
import java.io.IOException;

public interface PersistenceManager
{
    <T extends PersistableStore> T registerStore(T store);

    void readFromFile(File file) throws IOException;

    void persistToFile(File file) throws IOException;
}
