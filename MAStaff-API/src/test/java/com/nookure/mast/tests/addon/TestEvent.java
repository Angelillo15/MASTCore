package com.nookure.mast.tests.addon;

import com.nookure.mast.api.event.Event;

public class TestEvent extends Event {
  private final String text;

  public TestEvent(String text) {
    super();
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
