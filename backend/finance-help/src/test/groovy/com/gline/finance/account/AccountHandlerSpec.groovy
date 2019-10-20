package com.gline.finance.account

import com.fasterxml.jackson.databind.ObjectMapper
import com.gline.finance.Main
import ratpack.handling.Handler
import ratpack.server.RatpackServer
import ratpack.test.embed.EmbeddedApp
import spock.lang.Specification

class AccountHandlerSpec extends Specification
{
    void "should create account on post"()
    {
        given:
        def accountStore = Mock(AccountStore)
        def app = createApp(new AccountHandler(accountStore))

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
        def app = createApp(new AccountHandler(accountStore))

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

    private EmbeddedApp createApp(Handler handler)
    {
        EmbeddedApp.fromServer(RatpackServer.start {
            it.registryOf {
                it.add(ObjectMapper.class, Main.getObjectMapper())
            }.handler({ handler})
        })

    }
}
