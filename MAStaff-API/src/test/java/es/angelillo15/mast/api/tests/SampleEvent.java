package es.angelillo15.mast.api.tests;

import es.angelillo15.mast.api.event.Event;

public class SampleEvent extends Event {
    public String helloWorld() {
        return "Hello World!";
    }

    public void doSomething() {
        System.out.println("Doing something...");
    }
}
