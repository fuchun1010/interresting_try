package com.tank.client;

import com.tank.common.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fuchun
 * @date 2019-01-15
 */
public class Client {

  public static void main(String[] args) {
    NettyClient nettyClient = new NettyClient("localhost", Constants.PORT);
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(nettyClient);
  }
}
