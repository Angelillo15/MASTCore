package com.nookure.mast.tests.addon;

import com.nookure.mast.api.event.EventPriority;
import com.nookure.mast.api.event.MastSubscribe;

public class TestListener {
  @MastSubscribe(priority = EventPriority.LOW)
  public void onTestListener(TestEvent event) {
    System.out.println(event.getText());
  }

}
