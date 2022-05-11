package com.good.platform.enums;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.utility.Constants;

public enum FinancialYear {

	FY_2020_2021("FY 2020-2021"), FY_2019_2020("FY 2019-2020"), FY_2018_2019("FY 2018-2019"),
	FY_2017_2018("FY 2017-2018");

	public final String value;

	private FinancialYear(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static FinancialYear getByName(String name){
	    for(FinancialYear year : values()){
	      if(year.toString().equals(name)){
	        return year;
	      }
	    }
	    throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_FINANCIAL_YEAR_SELECTION);
	  }
}