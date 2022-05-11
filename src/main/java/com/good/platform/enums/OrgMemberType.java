package com.good.platform.enums;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.utility.Constants;

public enum OrgMemberType {

	FOUNDER("Founder"), BOARD_OF_DIRECTOR("Board of Director"), C_SUITE("C-Suite"), OTHER("Other");
	
	public final String value;

	private OrgMemberType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static OrgMemberType getByName(String name){
	    for(OrgMemberType orgType : values()){
	      if(orgType.toString().equals(name)){
	        return orgType;
	      }
	    }
	    throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_ORGANISATION_TYPE);
	  }
}
