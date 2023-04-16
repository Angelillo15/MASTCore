package es.angelillo15.mast.api.tests;

import es.angelillo15.mast.api.redis.Event;
import es.angelillo15.mast.api.redis.EventHandler;
import es.angelillo15.mast.api.redis.EventManager;
import es.angelillo15.mast.api.redis.Listener;

public class EventTest implements Listener {

    public static void main(String[] args) {
        SampleEvent event = new SampleEvent();
        SampleEvent2 event2 = new SampleEvent2("Sample Event 2");

        EventManager.getInstance().registerListener(new EventTest());
        EventManager.getInstance().fireEvent(event);
        EventManager.getInstance().fireEvent(event2);
    }

    @EventHandler
    public void onSampleEvent(SampleEvent event) {
        System.out.println(event.helloWorld());
    }

    @EventHandler
    public void onSampleEvent2(SampleEvent2 event) {
        System.out.println(event.getText());
    }

}
