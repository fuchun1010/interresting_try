package com.tank;

import com.tank.message.LoginRequestProto;
import com.tank.message.MessageCategoryProto;
import com.tank.message.SearchRequestProto;
import com.tank.ob.LoginObserver;
import com.tank.ob.MessageObservable;
import lombok.val;

/**
 * @author fuchun
 * @date 2019-01-17
 */
public class Basic {

  public static void main(String[] args) {

    MessageObservable messageObservable = new MessageObservable();

    messageObservable.addObserver(new LoginObserver());

    val loginBuilder = LoginRequestProto.LoginRequest.newBuilder();
    val loginRequest = loginBuilder.setName("lisi").setPassword("123456").build();

    val data = loginRequest.toByteString();

    val messageCategoryBuilder = MessageCategoryProto.MessageCategory.newBuilder();
    val messageCategory = messageCategoryBuilder.setRequestType(MessageCategoryProto.MessageCategory.RequestType.LOGIN_REQUEST)
        .setData(data)
        .build();

    messageObservable.changeData(messageCategory);


    val searchBuilder = SearchRequestProto.SearchRequest.newBuilder();
    val searchRequest = searchBuilder.setResultPerPage(50).setPageNumber(1).setResultPerPage(20).build();
    val searchData = searchRequest.toByteString();
    val searchRequestCategory = messageCategoryBuilder.setRequestType(MessageCategoryProto.MessageCategory.RequestType.SEARCH_REQUEST)
        .setData(searchData)
        .build();
    messageObservable.changeData(searchRequestCategory);

  }
}
