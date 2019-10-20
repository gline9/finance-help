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
        accountCreationMemento.setProperty("name", "foobar")
        def accountCreation = AccountCreationRequest.fromMemento(accountCreationMemento)

        when:

        accountStore.createAccount(accountCreation)

        then:

        1 == accountStore.getAccounts().size()
        "foobar" == accountStore.getAccounts()[0].name
    }

    void "should replace account state properly"()
    {
        given:

        def accountStore = new InMemoryAccountStore()

        def accountCreationMemento = Memento.emptyBean()
        accountCreationMemento.setProperty("balance", 100d)
        accountCreationMemento.setProperty("rate", 1)
        accountCreationMemento.setProperty("compoundsPerYear", 1)
        accountCreationMemento.setProperty("name", "foobar")
        def accountCreation = AccountCreationRequest.fromMemento(accountCreationMemento)
        accountStore.createAccount(accountCreation)


        when:

        def accounts = accountStore.getSerializableData()

        accountStore.createAccount(accountCreation)
        accountStore.setFromMementos(accounts*.serializeToMemento())

        then:

        1 == accountStore.getAccounts().size()
        "foobar" == accountStore.getAccounts()[0].name
    }

    void "should allow deleting accounts"()
    {
        given:

        def accountStore = new InMemoryAccountStore()

        def accountCreationMemento = Memento.emptyBean()
        accountCreationMemento.setProperty("balance", 100d)
        accountCreationMemento.setProperty("rate", 1)
        accountCreationMemento.setProperty("compoundsPerYear", 1)
        accountCreationMemento.setProperty("name", "foobar")
        def accountCreation = AccountCreationRequest.fromMemento(accountCreationMemento)
        Account created = accountStore.createAccount(accountCreation)

        def accountDeletionMemento = Memento.emptyBean()
        accountDeletionMemento.setProperty("id", created.id)

        def accountDeletion = AccountDeletionRequest.fromMemento(accountDeletionMemento)

        when:

        def account = accountStore.deleteAccount(accountDeletion)

        then:

        0 == accountStore.getAccounts().size()
        "foobar" == account.name
    }

    void "shouldn't allow deleting non-existent account"()
    {
        given:

        def accountStore = new InMemoryAccountStore()

        def accountCreationMemento = Memento.emptyBean()
        accountCreationMemento.setProperty("balance", 100d)
        accountCreationMemento.setProperty("rate", 1)
        accountCreationMemento.setProperty("compoundsPerYear", 1)
        accountCreationMemento.setProperty("name", "foobar")
        def accountCreation = AccountCreationRequest.fromMemento(accountCreationMemento)
        Account created = accountStore.createAccount(accountCreation)

        def accountDeletionMemento = Memento.emptyBean()
        accountDeletionMemento.setProperty("id", "foobar")

        def accountDeletion = AccountDeletionRequest.fromMemento(accountDeletionMemento)

        when:

        def account = accountStore.deleteAccount(accountDeletion)

        then:

        def exception = thrown(IllegalArgumentException)
        "Account not found for id 'foobar'" == exception.message
        1 == accountStore.getAccounts().size()
        "foobar" == accountStore.getAccounts()[0].name
    }
}
