package com.good.platform.enums;


import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.utility.Constants;

/**
 * OrganisationsTypeEnum is used to maintian the enumeration datas with 
 * respect to the organisation type
 * @author AL3678
 *
 */
public enum OrganisationsTypeEnum {

	SOCIAL_ORGANISATION("Social Organisation");

	public final String value;

	private OrganisationsTypeEnum(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	/*
	 * public static OrganisationsTypeEnum getByName(String name){ try {
	 * for(OrganisationsTypeEnum orgType : values()){
	 * if(orgType.toString().equals(name)){ return orgType; } } } catch(Exception
	 * ex) { throw new SOException(ErrorCode.NOT_FOUND, ex); } return ; }
	 */

	public static OrganisationsTypeEnum getByName(String name) {
		
			for (OrganisationsTypeEnum orgType : values()) {
				if (orgType.toString().equals(name)) {
					return orgType;
				}
			}
			return null;
		
		
	}
	 
}
