package com.tank.common;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Objects;

/**
 * @author fuchun
 */
public class BytesUtil {

  public static BytesUtil newInstance() {
    return Single.INSTANCE.createInstance();
  }

  /**
   * @param message
   * @return
   */
  public static ByteBuf toByteBuf(final String message) {
    Objects.requireNonNull(message);
    Preconditions.checkArgument(message.length() > 0);
    return Unpooled.copiedBuffer(message.getBytes());
  }

  /**
   * @param data
   * @return
   */
  public static ByteBuf toByteBuf(final byte[] data) {
    Objects.requireNonNull(data);
    Preconditions.checkArgument(data.length > 0);
    return Unpooled.copiedBuffer(data);
  }

  public static String toStr(final byte[] data) {
    Objects.requireNonNull(data);
    Preconditions.checkArgument(data.length > 0);
    final String str = new String(data);
    return str;
  }

  private enum Single {
    INSTANCE;

    Single() {
      this.byteBufUtil = new BytesUtil();
    }

    public BytesUtil createInstance() {
      return this.byteBufUtil;
    }

    private BytesUtil byteBufUtil;
  }

  private BytesUtil() {

  }

}
