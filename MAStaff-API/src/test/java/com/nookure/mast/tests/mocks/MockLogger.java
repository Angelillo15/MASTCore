package com.nookure.mast.tests.mocks;

import es.angelillo15.mast.api.ILogger;

public class MockLogger extends ILogger {
  public MockLogger() {
    super();
    setInstance(this);
  }
  @Override
  public void info(String message) {
    System.out.println(message);
  }

  @Override
  public void warn(String message) {
    System.out.println(message);
  }

  @Override
  public void error(String message) {
    System.out.println(message);
  }

  @Override
  public void debug(String message) {
    System.out.println(message);
  }
}
