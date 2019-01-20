package com.tank.ob;

import java.util.Observer;

public interface MessageParser extends Observer {

  Enum fixedMessageType();
}
