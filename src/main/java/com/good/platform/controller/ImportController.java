package com.good.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.good.platform.response.dto.GoodPlatformResponseVO;
import com.good.platform.service.ImportService;
import com.good.platform.utility.Constants;
import com.good.platform.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/import")
@RequiredArgsConstructor
@Slf4j
public class ImportController {

	@Autowired
	ImportService importService;

	@PostMapping()
	public GoodPlatformResponseVO<Object> addBulkUsers(@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "offset", required = false) Integer offset) {
		Object response = importService.addBulkUsers(limit, offset);
		return ResponseHelper.createResponse(new GoodPlatformResponseVO<Object>(), response, Constants.USER_ADD_SUCCESS,
				Constants.USER_ADD_FAIL);
	}
}
