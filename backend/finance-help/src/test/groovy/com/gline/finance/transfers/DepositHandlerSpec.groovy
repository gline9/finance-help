package com.gline.finance.transfers

import com.gline.finance.ServerTester
import spock.lang.Specification
import static spock.genesis.Gen.*

class DepositHandlerSpec extends Specification
{
    ServerTester serverTester = new ServerTester();

    void "should deposit actual value to the account"()
    {
        given:

        def transferService = Mock(TransferService)
        def app = serverTester.createApp(new DepositHandler(transferService))

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

        1 * transferService.depositIntoAccount(id, value)

        where:

        [id, value] << tuple(string(~/[a-zA-Z0-9]+/), getDouble()).take(10)
    }
}
