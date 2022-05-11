
  package com.good.platform.response.dto;
  
  import java.util.List;

import lombok.Data;
  
  @Data 
  public class UserResponse extends BaseResponse {
  
  private String firstName; 
  private String lastName; 
  private String emailId;
  private String phone;
  private String userName;
  private String profileImageUrl;
  private String userIdpId;
  private Boolean emailVerified;
  private Boolean phoneVerified;
  private String profileImageFilename;
  private String middleName;
  private List<String> projectsId;
  private String roleId;
  private String role;
  
  }
 