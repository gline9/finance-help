package com.gline.finance.account

import com.gline.finance.ServerTester
import spock.lang.Specification

class AccountHandlerSpec extends Specification
{
    ServerTester serverTester = new ServerTester()

    void "should create account on post"()
    {
        given:
        def accountStore = Mock(AccountStore)
        def app = serverTester.createApp(new AccountHandler(accountStore))

        when:

        app.test {
            it.request {
                it.body {
                    it.type "application/json"
                    it.text """{"name": "foobarbaz", "balance": 1}"""
                }.post()
            }
        }

        then:

        1 * accountStore.createAccount({it.create().name == "foobarbaz"})

    }

    void "should delete account on delete"()
    {
        given:

        def accountStore = Mock(AccountStore)
        def app = serverTester.createApp(new AccountHandler(accountStore))

        when:

        app.test {
            it.request {
                it.body {
                    it.type "application/json"
                    it.text """{"id": "foobas"}"""
                }.delete()
            }
        }

        then:

        1 * accountStore.deleteAccount({it.id == "foobas"})
    }

}
