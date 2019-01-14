package com.tank.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fuchun
 * @date 2019-01-14
 */
@Slf4j
public class LoginHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    log.info("channel in active");
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    log.info("channel read");

  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    log.info("channel read complete");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.info("exception caught");
  }
}
