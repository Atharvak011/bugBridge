package com.cdac.bugbridge.response;

import java.time.LocalDateTime;
import java.util.List;

import com.cdac.bugbridge.dto.BugDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "status", "message", "path" })
public class BugApiResponse {
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;
  private int status;
  private String message;
  private String path;
  private BugDTO bugDTO;
  private List<BugDTO> bugList;

  // Single constructor for all cases
  public BugApiResponse(int status, String message, String path, List<BugDTO> bugList) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
    this.bugList = bugList;
  }

  public BugApiResponse(int status, String message, String path, BugDTO bugDTO) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
    this.bugDTO = bugDTO;
  }

  public BugApiResponse(int status, String message, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
  }

  public BugApiResponse() {
    //TODO Auto-generated constructor stub
  }

  // Getters and setters
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
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

  public BugDTO getBugDTO() {
    return bugDTO;
  }

  public void setBugDTO(BugDTO bugDTO) {
    this.bugDTO = bugDTO;
  }

  public List<BugDTO> getBugList() {
    return bugList;
  }

  public void setBugList(List<BugDTO> bugList) {
    this.bugList = bugList;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("BugApiResponse { ")
        .append("timestamp='").append(timestamp).append('\'')
        .append(", status=").append(status)
        .append(", message='").append(message).append('\'')
        .append(", path='").append(path).append('\'');

    if (bugDTO != null) {
      sb.append(", bugDTO=").append(bugDTO);
    }

    if (bugList != null && !bugList.isEmpty()) {
      sb.append(", bugList=").append(bugList);
    }

    sb.append(" }");
    return sb.toString();
  }

}
