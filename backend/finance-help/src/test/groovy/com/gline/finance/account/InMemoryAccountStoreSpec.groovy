package com.gline.finance.account

import com.gline.finance.serialization.Memento
import spock.lang.Specification

class InMemoryAccountStoreSpec extends Specification
{
    void "should allow creating accounts"()
    {
        given:

        def accountStore = new InMemoryAccountStore()

        def accountCreationMemento = Memento.emptyBean()
        accountCreationMemento.setProperty("balance", 100d)
        accountCreationMemento.setProperty("rate", 1)
        accountCreationMemento.setProperty("compoundsPerYear", 1)
        def accountCreation = AccountCreationRequest.fromMomento(accountCreationMemento)

        when:

        accountStore.createAccount(accountCreation)

        then:

        1 == accountStore.getAccounts().size()
    }

    void "should replace account state properly"()
    {
        given:

        def accountStore = new InMemoryAccountStore()

        def accountCreationMemento = Memento.emptyBean()
        accountCreationMemento.setProperty("balance", 100d)
        accountCreationMemento.setProperty("rate", 1)
        accountCreationMemento.setProperty("compoundsPerYear", 1)
        def accountCreation = AccountCreationRequest.fromMomento(accountCreationMemento)
        accountStore.createAccount(accountCreation)


        when:

        def accounts = accountStore.getSerializableData()

        accountStore.createAccount(accountCreation)
        accountStore.setFromMementos(accounts*.serializeToMemento())

        then:

        1 == accountStore.getAccounts().size()
    }
}
