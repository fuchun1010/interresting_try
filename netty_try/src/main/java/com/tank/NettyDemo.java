package com.tank;

import com.tank.server.LoginServer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@Slf4j
public class NettyDemo {
  public static void main(String[] args) {

    ExecutorService service = Executors.newSingleThreadExecutor();

    int port = 10000;

    LoginServer loginServer = new LoginServer(port);

    service.execute(loginServer::start);
  }
}
