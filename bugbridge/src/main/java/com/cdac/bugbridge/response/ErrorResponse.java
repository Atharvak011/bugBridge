package com.cdac.bugbridge.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "message", "path", "object" })
public class ErrorResponse {
  private int status;
  private String message;
  private String path;
  private Object data;

  public ErrorResponse() {

  }

  public ErrorResponse(int status, String message, String path) {
    this.status = status;
    this.message = message;
    this.path = path;
  }

  public ErrorResponse(int status, String message, String path, Object data) {
    this.status = status;
    this.message = message;
    this.path = path;
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Object getObject() {
    return data;
  }

  public void setObject(Object data) {
    this.data = data;
  }

}
