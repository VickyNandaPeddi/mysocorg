package com.good.platform.utility;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.response.dto.GoodPlatformResponseVO;

public class ResponseHelper<T> {

	public static GoodPlatformResponseVO createResponse(GoodPlatformResponseVO response, Object data,
			String successMessage, String errorMessage) {
		if (data != null) {
			response.setSuccess(true);
			response.setData(data);
			response.setMessage(successMessage);
		} else {
			throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR, errorMessage);
		}
		return response;
	}

}