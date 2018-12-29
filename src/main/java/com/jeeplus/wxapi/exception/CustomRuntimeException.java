package com.jeeplus.wxapi.exception;

public class CustomRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 3746438175818546837L;

  public CustomRuntimeException()
  {
  }

  public CustomRuntimeException(String message)
  {
    super(message);
  }

  public CustomRuntimeException(Throwable cause) {
    super(cause);
  }

  public CustomRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
