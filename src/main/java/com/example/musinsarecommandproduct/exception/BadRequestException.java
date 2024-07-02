package com.example.musinsarecommandproduct.exception;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
public class BadRequestException extends RuntimeException {

  private BadRequestType type;
  private String message;
  private String code;

  public BadRequestException(BadRequestType type) {
    super(type == null ? null : type.name());
    this.type = type;
    this.message = type.getMessage();
  }

  public BadRequestType getType() {
    return this.type;
  }

  public BadRequestType getCode() {return this.type; }

  @Override
  public String getMessage() {
    return this.message;
  }

}
