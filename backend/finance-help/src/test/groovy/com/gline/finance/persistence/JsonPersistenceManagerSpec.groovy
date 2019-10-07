package com.gline.finance.persistence

import com.gline.finance.serialization.Converters
import com.gline.finance.serialization.Memento
import com.gline.finance.serialization.Serializable
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class JsonPersistenceManagerSpec extends Specification
{
    @Rule
    TemporaryFolder temporaryFolder

    void "should handle reading from json file"()
    {
        given:

        def file = temporaryFolder.newFile()

        file.text = """
            {"test": [{"foo": "bar"}]}
        """

        def store = Mock(PersistableStore)
        store.getRepoPersistenceName() >> "test"

        def manager = new JsonPersistenceManager()
        manager.registerStore(store)

        when:

        manager.readFromFile(file)

        then:

        1 * store.setFromMementos({"bar" == it[0].getValue("foo", Converters.identity(String.class))})

    }

    void "should handle writing to json file"()
    {
        given:

        def file = temporaryFolder.newFile()

        def store = Mock(PersistableStore)
        store.getRepoPersistenceName() >> "test"

        def serializable = Mock(Serializable)
        def memento = Memento.emptyBean()
        memento.setProperty("foo", 123)

        serializable.serializeToMemento() >> memento
        store.getSerializableData() >> [serializable]

        def manager = new JsonPersistenceManager()
        manager.registerStore(store)

        when:

        manager.persistToFile(file)

        then:

        file.text == """{"test":[{"foo":123}]}"""

    }
}
