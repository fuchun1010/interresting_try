package com.tank;

import com.google.common.collect.Maps;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.tank.anno.Bs;
import com.tank.anno.MessageHandler;
import com.tank.message.LoginRequestProto;
import com.tank.message.MessageCategoryProto;
import com.tank.message.SearchRequestProto;
import com.tank.ob.LoginObserver;
import com.tank.ob.MessageObservable;
import lombok.val;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author fuchun
 * @date 2019-01-17
 */
public class Basic {

  public static void main(String[] args) {

  }



  private static void protoTry() {
    MessageObservable messageObservable = new MessageObservable();

    messageObservable.addObserver(new LoginObserver());

    val loginBuilder = LoginRequestProto.LoginRequest.newBuilder();
    val loginRequest = loginBuilder.setName("lisi").setPassword("123456").build();

    val data = loginRequest.toByteString();

    val messageCategoryBuilder = MessageCategoryProto.MessageCategory.newBuilder();
    val messageCategory = messageCategoryBuilder.setRequestType(MessageCategoryProto.MessageCategory.RequestType.LOGIN_REQUEST)
        .setData(data)
        .build();

    System.out.println("proto buff person = [" + messageCategory.toByteArray().length + "]");

    messageObservable.changeData(messageCategory);


    val searchBuilder = SearchRequestProto.SearchRequest.newBuilder();
    val searchRequest = searchBuilder.setResultPerPage(50).setPageNumber(1).setResultPerPage(20).build();
    val searchData = searchRequest.toByteString();
    val searchRequestCategory = messageCategoryBuilder.setRequestType(MessageCategoryProto.MessageCategory.RequestType.SEARCH_REQUEST)
        .setData(searchData)
        .build();
    messageObservable.changeData(searchRequestCategory);

    Person person = new Person();
    person.setName("lisi");
    person.setPassword("123456");

    try {
      ByteOutputStream bo = new ByteOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bo);
      out.writeObject(person);
      out.flush();
      byte[] xx = bo.getBytes();
      System.out.println("java seria person  = [" + xx.length + "]");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
