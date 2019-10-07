package com.gline.finance.serialization

import spock.lang.Specification

class MementoSpec extends Specification
{
    void "empty memento should be empty"()
    {
        given:

        def empty = Memento.emptyBean()

        when:

        def value = empty.getProperty("foobar")

        then:

        null == value
    }
}
