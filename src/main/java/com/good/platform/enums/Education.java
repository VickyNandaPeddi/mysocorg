package com.good.platform.enums;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.utility.Constants;

@Deprecated
public enum Education {
	
	HIGHER_EDUCATION("Higher Education");
	
	public final String value;

	private Education(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static Education getByName(String name){
	    for(Education education : values()){
	      if(education.toString().equals(name)){
	        return education;
	      }
	    }
	    throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_EDUCATION);
	  }

}
