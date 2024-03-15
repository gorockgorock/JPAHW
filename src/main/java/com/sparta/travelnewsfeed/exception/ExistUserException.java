package com.sparta.travelnewsfeed.exception;

import java.io.Serial;

public class ExistUserException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -4937536120992271478L;

  public ExistUserException() {
    super("중복 된 유저 이름입니다");

  }
}
