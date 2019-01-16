package com.tank.mongo.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoUtil {

  public static MongoUtil createInstance() {
    return mongoUtil;
  }

  public MongoDatabase mongoClient(final String dbName) {
    return Single.Instance.createInstance().getDatabase(dbName);
  }

  public MongoClient initMongoClient(final String dbName) {
    return Single.Instance.createInstance();
  }

  enum Single {
    Instance;

    Single() {
      System.out.println("...init....");
      log.info("...init mongo....");
      MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
      builder.maxConnectionIdleTime(Integer.MAX_VALUE);
      this.mongoClient = new MongoClient(new ServerAddress("localhost", 27017), builder.build());
    }

    public MongoClient createInstance() {
      return this.mongoClient;
    }

    private MongoClient mongoClient;
  }


  private MongoUtil() {

  }

  private static MongoUtil mongoUtil = new MongoUtil();

}
