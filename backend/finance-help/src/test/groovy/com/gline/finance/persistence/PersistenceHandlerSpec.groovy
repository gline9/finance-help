package com.gline.finance.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.gline.finance.Main
import ratpack.handling.Handler
import ratpack.server.RatpackServer
import ratpack.test.embed.EmbeddedApp
import spock.lang.Specification

class PersistenceHandlerSpec extends Specification
{
    void "should attempt to save when saving"()
    {
        given:
        def persistenceManager = Mock(PersistenceManager)
        def app = createApp(new PersistenceHandler(persistenceManager))

        when:

        app.test {
            it.request {
                it.body {
                    it.type "application/json"
                    it.text """{"fileName": "foobar", "commandType": "SAVE"}"""
                }.post()
            }
        }

        then:

        1 * persistenceManager.persistToFile(new File("foobar"))
    }

    void "should attempt to load when loading"()
    {
        given:

        def persistenceManager = Mock(PersistenceManager)
        def app = createApp(new PersistenceHandler(persistenceManager))

        when:

        app.test {
            it.request {
                it.body {
                    it.type "application/json"
                    it.text """
                        {"fileName": "foobar", "commandType": "LOAD"}
                    """
                }.post()
            }
        }

        then:

        1 * persistenceManager.readFromFile(new File("foobar"))
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
