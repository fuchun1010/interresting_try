package com.tank;

import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.tank.mongo.util.MongoUtil;
import org.bson.Document;

/**
 * @author fuchun
 */
public class MongoTry {
  public static void main(String[] args) {

    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      final MongoUtil mongoUtil = MongoUtil.createInstance();
      final DBCollection dbCollection = mongoUtil.createConn("tab_users");


//      MongoDatabase db = mongoUtil
//      MongoCollection<Document> docs = db.getCollection("tab_users");
//      MongoCursor cursor = docs.find().iterator();
//      while (cursor.hasNext()) {
//        Document document = (Document) cursor.next();
//      }
//      System.out.println("i = [" + i + "]");
    }


  }
}
