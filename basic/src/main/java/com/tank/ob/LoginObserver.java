package com.tank.ob;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tank.message.LoginRequestProto;
import com.tank.message.MessageCategoryProto;

import java.util.Observable;

/**
 * @author fuchun
 */
public class LoginObserver implements MessageParser {

  @Override
  public void update(Observable observable, Object arg) {

    MessageObservable messageObservable = (MessageObservable) observable;

    MessageCategoryProto.MessageCategory messageCategory = messageObservable.getMessageCategory();

    MessageCategoryProto.MessageCategory.RequestType messageType = messageCategory.getRequestType();

    if (this.fixedMessageType() == messageType) {
      System.out.println(" process login message");
      ByteString data = messageCategory.getData();
      try {
        LoginRequestProto.LoginRequest request = LoginRequestProto.LoginRequest.parseFrom(data);
        System.out.println("name = [" + request.getName() + "], password = [" + request.getPassword() + "]");
      } catch (InvalidProtocolBufferException e) {
        e.printStackTrace();
      }
    }


  }

  @Override
  public Enum fixedMessageType() {
    return MessageCategoryProto.MessageCategory.RequestType.LOGIN_REQUEST;
  }
}
