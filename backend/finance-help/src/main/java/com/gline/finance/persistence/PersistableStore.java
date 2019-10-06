package com.gline.finance.persistence;

import com.gline.finance.serialization.Memento;
import com.gline.finance.serialization.Serializable;

import java.util.List;

public interface PersistableStore
{
    String getRepoPersistenceName();

    void setFromMementos(List<Memento> mementos);

    List<Serializable> getSerializableData();
}
