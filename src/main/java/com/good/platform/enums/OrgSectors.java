package com.good.platform.enums;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.utility.Constants;

public enum OrgSectors {
	AGRICULTURE("Agriculture"), EDUCATION("Education"), NUTRITION("Nutrition"), SKILLING("Skilling"),
	WOMEN_EMPOWERMENT("Women empowerment"), HEALTHCARE("Health care");
	
	public final String value;

	private OrgSectors(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static OrgSectors getByName(String name){
	    for(OrgSectors sectorType : values()){
	      if(sectorType.toString().equals(name)){
	        return sectorType;
	      }
	    }
	    throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_SECTOR_SELECTION);
	  }
}
