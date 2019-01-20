package com.tank.ob;

import com.tank.message.MessageCategoryProto;
import lombok.Getter;

import java.util.Observable;

/**
 * @author fuchun
 */
public class MessageObservable extends Observable {


  public void changeData(final MessageCategoryProto.MessageCategory latestMessageCategory) {
    this.messageCategory = latestMessageCategory;
    this.setChanged();
    this.notifyObservers();
  }

  @Getter
  private MessageCategoryProto.MessageCategory messageCategory;
}
