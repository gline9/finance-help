package com.gline.finance.transfers

import com.gline.finance.ServerTester
import spock.lang.Specification

import static spock.genesis.Gen.getDouble
import static spock.genesis.Gen.string
import static spock.genesis.Gen.tuple

class TransferHandlerSpec extends Specification
{
    ServerTester serverTester = new ServerTester();

    void "should transfer actual value from one account to the other"()
    {
        given:

        def transferService = Mock(TransferService)
        def app = serverTester.createApp(new TransferHandler(transferService))

        when:

        app.test {
            it.request {
                it.body {
                    it.type "application/json"
                    it.text """{"from": "$fromID", "to": "$toID", "amount": $value}"""
                }.post()
            }
        }

        then:

        1 * transferService.transferBetweenAccounts(fromID, toID, value)

        where:

        [fromID, toID, value] << tuple(string(~/[a-zA-Z0-9]+/), string(~/[a-zA-Z0-9]+/), getDouble()).take(10)
    }
}
