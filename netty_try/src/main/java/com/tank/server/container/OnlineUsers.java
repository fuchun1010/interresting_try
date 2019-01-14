package com.tank.server.container;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.tank.common.BytesUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author fuchun
 * @date 2019-01-14
 */
@Slf4j
public class OnlineUsers {

  public static OnlineUsers newInstance() {
    return Single.INSTANCE.createInstance();
  }

  public int number() {
    int size = 0;
    try {
      readLock.lock();
      size = users.size();
    } finally {
      readLock.unlock();
    }

    return size;
  }

  public void add(final String username, final ChannelHandlerContext ctx) {

    Objects.requireNonNull(username);
    Objects.requireNonNull(ctx);

    synchronized (users) {
      users.putIfAbsent(username, ctx);
    }

  }

  public void remove(final String username) {
    synchronized (users) {
      final ChannelHandlerContext ctx = users.get(username);
      final boolean isClosable = Objects.nonNull(ctx) && ctx.channel().isActive();
      if (isClosable) {
        log.info("close {} channel context", username);
        ctx.close();
      }
      users.remove(username);
    }
  }

  public void broadcast(final String message) {
    Objects.requireNonNull(message);
    Preconditions.checkArgument(message.length() > 0);
    try {
      readLock.lock();
      Iterator<String> keys = users.keySet().iterator();
      while (keys.hasNext()) {
        final String userName = keys.next();
        final ChannelHandlerContext ctx = users.get(userName);
        ctx.writeAndFlush(Unpooled.copiedBuffer(message.getBytes()));
      }
    } finally {
      log.info("unlock in broadcast method");
      readLock.unlock();
    }
  }

  public void sendMsg(final String username, final String message) {
    Objects.nonNull(username);
    Objects.nonNull(message);
    Preconditions.checkArgument(Objects.nonNull(username));
    try {
      readLock.lock();
      final ChannelHandlerContext ctx = this.users.get(username);
      ctx.writeAndFlush(Unpooled.copiedBuffer(BytesUtil.toByteBuf(message)));
    } finally {
      readLock.unlock();
    }
  }

  public boolean isOnline(final String username) {
    boolean exists = false;
    try {
      readLock.lock();
      exists = Objects.nonNull(users.get(username));
    } finally {
      log.info("unlock in isOnline method");
      readLock.unlock();
    }

    return exists;
  }

  private BiMap<String, ChannelHandlerContext> users = HashBiMap.create();

  private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

  private ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();

  private OnlineUsers() {

  }

  private enum Single {

    INSTANCE;

    Single() {
      this.onlineUsers = new OnlineUsers();
    }

    public OnlineUsers createInstance() {
      return this.onlineUsers;
    }

    private OnlineUsers onlineUsers;
  }
}
