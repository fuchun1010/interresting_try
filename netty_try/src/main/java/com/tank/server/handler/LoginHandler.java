package com.tank.server.handler;

import com.google.common.base.Preconditions;
import com.tank.server.container.OnlineUsers;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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
    Objects.requireNonNull(msg);
    log.info("channel start read");

    if (msg instanceof String) {
      String message = (String) msg;
      String[] result = message.split(":");
      Objects.requireNonNull(result);
      Preconditions.checkArgument(result.length == 2, "chat format not right");
      String username = result[0];
      String password = result[1].replace("\r\n", "");

      if ("123".equalsIgnoreCase(password)) {
        log.info("{} login success", username);
        Objects.requireNonNull(this.onlineUsers);
        this.onlineUsers.add(username, ctx);
        this.onlineUsers.broadcast("on line users is:" + this.onlineUsers.number());
      }
    }

  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    log.info("channel read complete");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.info("exception caught");
  }

  private OnlineUsers onlineUsers = OnlineUsers.newInstance();
}
