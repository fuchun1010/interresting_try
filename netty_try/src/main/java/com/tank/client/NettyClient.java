package com.tank.client;

import com.google.common.base.Preconditions;
import com.tank.common.BytesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author fuchun
 * @date 2019-01-15
 */
@Slf4j
public class NettyClient implements Runnable {

  public NettyClient(final String ip, @Nonnull final int port) {
    Objects.requireNonNull(ip);
    Preconditions.checkArgument(port > 0);
    this.ip = ip;
    this.port = port;
  }

  @Override
  public void run() {
    EventLoopGroup worker = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();

    bootstrap.group(worker)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel serverSocketChannel) throws Exception {
            ChannelPipeline pipeline = serverSocketChannel.pipeline();
            pipeline.addLast(new StringEncoder());
            pipeline.addLast(new StringDecoder());
          }
        });

    ChannelFuture f = null;
    try {
      f = bootstrap.connect(this.ip, port).sync();
      log.info("login server ip  : {}, port: {} successfully", this.ip, port);
      for (int i = 0; i < 10; i++) {
        final String tips = String.format("A%s:123", i);
        ChannelFuture future = f.channel().writeAndFlush(BytesUtil.toByteBuf(tips));
        future.addListener(new ChannelFutureListener() {
          @Override
          public void operationComplete(ChannelFuture channelFuture) throws Exception {
            log.info("{} send successfully", tips);
          }
        });
      }
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  private String ip;

  private int port;

}
