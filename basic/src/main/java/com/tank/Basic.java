package com.tank;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fuchun
 * @date 2019-01-17
 */
public class Basic {
  public static void main(String[] args) {

    final String configPath = System.getProperty("user.dir") + File.separator + "config/stores.csv";

    File file = new File(configPath);

    if (!file.exists()) {
      throw new RuntimeException("file not exists");
    }
    final List<String> stores = Lists.newLinkedList();
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      while (true) {
        final String code = in.readLine();
        if (Objects.isNull(code)) {
          break;
        }
        stores.add(code);

      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      Objects.requireNonNull(in);
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    final Map<Integer, Integer> storeHash = Maps.newHashMap();

    for (String storeCode : stores) {
      int hashCode = Objects.hashCode(storeCode);
      int code = Math.floorMod(hashCode, 100);
      boolean exists = storeHash.containsKey(code);
      if (!exists) {
        storeHash.put(code, 1);
      } else {
        int count = storeHash.get(code);
        count++;
        storeHash.put(code, count);
      }

    }

    for (Map.Entry<Integer, Integer> item : storeHash.entrySet()) {

      System.out.println("code = [" + item.getKey() + "], count=" + item.getValue());
    }

  }
}
