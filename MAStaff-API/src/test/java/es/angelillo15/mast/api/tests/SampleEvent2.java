package es.angelillo15.mast.api.tests;

import es.angelillo15.mast.api.event.Event;

public class SampleEvent2 extends Event {
    private final String text;
    public SampleEvent2(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
