package com.tank.server;

import com.google.common.base.Preconditions;
import com.tank.server.handler.LoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author fuchun
 */
@Slf4j
public class LoginServer {

  public LoginServer(final int port) {
    Preconditions.checkArgument(port >= 10000, new RuntimeException("port must greater than 10000"));
    this.port = port;
  }

  public void start() {

    Objects.requireNonNull(port);

    ChannelFuture channelFuture = null;

    EventLoopGroup server = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();

    ServerBootstrap bootstrap = new ServerBootstrap();

    bootstrap.group(server, worker)
        .channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 512)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.SO_RCVBUF, 5 * K)
        .childOption(ChannelOption.SO_SNDBUF, 5 * K)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel sc) throws Exception {
            final ChannelPipeline pipeline = sc.pipeline();
            //TODO load reflect result from config file
            pipeline.addLast(new LoginHandler());
          }
        });

    try {
      channelFuture = bootstrap.bind(this.port).sync();
      log.info("server start successfully, listen on:{}", port);
      Objects.requireNonNull(channelFuture);
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      log.error(" server start exception: {}", e.getMessage());
      e.printStackTrace();
    }
  }


  private final int K = 1024;

  private final int M = 1024 * K;

  @Getter
  private int port;
}
