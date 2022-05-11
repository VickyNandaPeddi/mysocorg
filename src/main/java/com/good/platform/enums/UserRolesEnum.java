package com.good.platform.enums;

public enum UserRolesEnum {
	PEER_REVIEWER("PEER-REVIEWER");
	
	private String role;
	  
    public String getRole()
    {
        return this.role;
    }
  
    private UserRolesEnum(String role)
    {
        this.role = role;
    }
}
