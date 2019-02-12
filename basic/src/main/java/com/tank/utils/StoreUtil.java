package com.tank.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.io.File.separator;

/**
 * @author fuchun
 */
public class StoreUtil {

  public StoreUtil(final String fileName) {
    Objects.requireNonNull(fileName);
    final String configPath = System.getProperty("user.dir") + separator + "config" + separator + fileName;
    File file = new File(configPath);
    if (!file.exists()) {
      throw new RuntimeException(configPath + " not exists");
    }
    this.filePath = configPath;
  }

  public Map<String,String> loadFiles(String propsFile) {

    return null;
  }

  public Map<Integer, Integer> statistics() {
    Map<Integer, Integer> storeHash = Maps.newHashMap();
    List<String> stores = this.loadConfig();
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
    return storeHash;
  }

  private List<String> loadConfig() {
    Preconditions.checkArgument(Objects.nonNull(this.filePath));
    final List<String> stores = Lists.newLinkedList();
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath)));
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
    return stores;
  }


  private String filePath;
}
