package com.tank.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSpec {


  @Before
  public void init() {
    service = Executors.newSingleThreadExecutor();
  }


  @After
  public void destroy() {
    service.shutdown();
  }

  @Test
  public void testClient() {
    NettyClient clientHandler = new NettyClient("localhost", 10000);
    service.execute(clientHandler);
  }

  private ExecutorService service = null;
}
