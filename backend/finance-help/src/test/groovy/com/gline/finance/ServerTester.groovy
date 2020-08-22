package com.gline.finance

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.handling.Handler
import ratpack.server.RatpackServer
import ratpack.test.embed.EmbeddedApp

class ServerTester
{
    EmbeddedApp createApp(Handler handler)
    {
        EmbeddedApp.fromServer(RatpackServer.start {
            it.registryOf {
                it.add(ObjectMapper.class, Main.getObjectMapper())
            }.handler({ handler})
        })

    }
}
