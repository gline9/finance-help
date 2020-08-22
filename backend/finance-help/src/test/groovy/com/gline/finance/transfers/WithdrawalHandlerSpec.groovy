package com.gline.finance.transfers

import com.gline.finance.ServerTester
import spock.lang.Specification

import static spock.genesis.Gen.getDouble
import static spock.genesis.Gen.string
import static spock.genesis.Gen.tuple

class WithdrawalHandlerSpec extends Specification
{
    ServerTester serverTester = new ServerTester();

    void "should withdraw actual value from the account"()
    {
        given:

        def transferService = Mock(TransferService)
        def app = serverTester.createApp(new WithdrawalHandler(transferService))

        when:

        app.test {
            it.request {
                it.body {
                    it.type "application/json"
                    it.text """{"id": "$id", "amount": $value}"""
                }.post()
            }
        }

        then:

        1 * transferService.withdrawFromAccount(id, value)

        where:

        [id, value] << tuple(string(~/[a-zA-Z0-9]+/), getDouble()).take(10)
    }
}
