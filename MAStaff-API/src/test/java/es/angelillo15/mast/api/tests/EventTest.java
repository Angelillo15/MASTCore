package es.angelillo15.mast.api.tests;

import es.angelillo15.mast.api.event.EventHandler;
import es.angelillo15.mast.api.redis.RedisEventManager;
import es.angelillo15.mast.api.event.Listener;
import org.junit.jupiter.api.Test;

public class EventTest implements Listener {

    @Test
    public void eventTest() {
        SampleEvent event = new SampleEvent();
        SampleEvent2 event2 = new SampleEvent2("Sample Event 2");

        RedisEventManager.getInstance().registerListener(this);
        RedisEventManager.getInstance().fireEvent(event);
        RedisEventManager.getInstance().fireEvent(event2);
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
