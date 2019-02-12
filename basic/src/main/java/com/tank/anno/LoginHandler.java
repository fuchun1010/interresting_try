package com.tank.anno;

import com.tank.Person;

/**
 * @author fuchun
 */
@Bs
public class LoginHandler implements RequestHandler {

  @MessageHandler(method = "login", message = Person.class)
  public void login(final Person person) {
    System.out.println("name = [" + person.getName() + "]");
  }

  @Override
  public int mappedProtocolCode() {
    return 1;
  }
}
