package com.tank;

import com.tank.message.LoginRequestProto;
import com.tank.message.MessageCategoryProto;
import com.tank.message.SearchRequestProto;
import lombok.val;

public class MessageDemo {

  public static void main(String[] args) {

    val loginBuilder = LoginRequestProto.LoginRequest.newBuilder();
    val login = loginBuilder.setName("lisi").setPassword("lisi123456").build();
    val data = login.toByteString();

    val messageCategoryBuilder = MessageCategoryProto.MessageCategory.newBuilder();
    val messageCategory = messageCategoryBuilder.setRequestType(MessageCategoryProto.MessageCategory.RequestType.LOGIN_REQUEST).setData(data).build();

    System.out.println("login request size = [" + messageCategory.toByteArray().length + "]");

    val searchBuilder = SearchRequestProto.SearchRequest.newBuilder();
    searchBuilder.setQuery("hello");
    searchBuilder.setPageNumber(2);
    searchBuilder.setResultPerPage(50);

    val person_a = SearchRequestProto.SearchRequest.Person.newBuilder()
        .setGender(1)
        .setName("wangwu")
        .build();

    val person_b = SearchRequestProto.SearchRequest.Person.newBuilder()
        .setGender(0)
        .setName("diaocan")
        .build();

    val searchData = searchBuilder.addPersons(person_a).addPersons(person_b).build().toByteString();

    val searchCategory = messageCategoryBuilder.setRequestType(MessageCategoryProto.MessageCategory.RequestType.SEARCH_REQUEST)
        .setData(searchData)
        .build();


    System.out.println("search request size = [" + searchCategory.toByteArray().length + "]");
  }
}
