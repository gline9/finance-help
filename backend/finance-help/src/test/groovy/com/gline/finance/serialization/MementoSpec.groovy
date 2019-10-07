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

    void "should handle retrieving simple value"()
    {
        given:

        def memento = Memento.emptyBean()
        memento.setProperty("foo", "bar")

        when:

        def value = memento.getValue("foo", Converters.identity(String.class))

        then:

        "bar" == value
    }

    void "should handle default supplier when no value"()
    {
        given:

        def empty = Memento.emptyBean()

        when:

        def value = empty.getValue("foo", Converters.identity(String.class), { "baz" })

        then:

        "baz" == value
    }

    void "should give illegal argument when incompatible class"()
    {
        given:

        def memento = Memento.emptyBean()
        memento.setProperty("foo", 123d)

        when:

        def value = memento.getValue("foo", Converters.identity(String.class))

        then:

        thrown(IllegalArgumentException)
    }

    void "should give illegal argument when not present"()
    {
        given:

        def memento = Memento.emptyBean()

        when:

        def value = memento.getValue("foo", Converters.identity(String.class))

        then:

        thrown(IllegalArgumentException)
    }

    void "should allow grabbing properties from maps"()
    {
        given:

        def memento = Memento.fromMap([foo: "bar", bar: 123d])

        when:

        def foo = memento.getValue("foo", Converters.identity(String.class))
        def bar = memento.getValue("bar", Converters.identity(Double.class))

        then:

        "bar" == foo
        123d == bar
    }
}
