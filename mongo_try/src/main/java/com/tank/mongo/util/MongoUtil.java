package com.tank.mongo.util;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoUtil {

  public DBCollection createConn(String collection) {
    synchronized (db) {
      if (db == null) {
        db = (new MongoClient("localhost", 27017)).getDB(collection);
      }
      return db.getCollection(collection);
    }

  }

  public final static MongoUtil mongoUtil = new MongoUtil();

  private MongoUtil() {

  }

  private DB db;

  public static MongoUtil createInstance() {
    return mongoUtil;
  }
}
