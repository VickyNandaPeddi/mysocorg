package com.good.platform.client.project.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.utility.Constants;


import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

	@Autowired
	private ObjectMapper objectMapper;


	@Override
	public Exception decode(String s, Response response) {

		try {

			InputStream content = response.body().asInputStream();

			int status = response.status();

			if (status != -1) {
				if (content != null) {
					GoodPlatformResponseVO errorResponse = objectMapper.readValue(content, GoodPlatformResponseVO.class);
						if (status == HttpStatus.NOT_FOUND.value()) {
							throw new SOException(ErrorCode.NOT_FOUND,errorResponse.getMessage());
						} else if (status == HttpStatus.FORBIDDEN.value()) {
							throw new SOException(ErrorCode.ACCESS_DENIED,errorResponse.getMessage());
						} else if (status == HttpStatus.UNAUTHORIZED.value()) {
							throw new SOException(ErrorCode.UNAUTHORIZED,errorResponse.getMessage());
						} else if (status == HttpStatus.BAD_REQUEST.value()) {
							throw new SOException(ErrorCode.BAD_REQUEST,errorResponse.getMessage());
						} else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
							throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR,errorResponse.getMessage());
						} else if (status == HttpStatus.CONFLICT.value()) {
							throw new SOException(ErrorCode.CONFLICT,errorResponse.getMessage());
						} else {
							throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR,errorResponse.getMessage());
						}
					}}
		} catch (IOException exception) { 
			log.error(ExceptionUtils.getStackTrace(exception));
		}
		throw new SOException(ErrorCode.INTERNAL_SERVER_ERROR,Constants.INTERNAL_SERVER_ERROR);

	}
}
