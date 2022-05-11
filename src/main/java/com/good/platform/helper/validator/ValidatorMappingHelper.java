package com.good.platform.helper.validator;

import com.good.platform.config.StaticContextAccessor;
import com.good.platform.entity.User;
import com.good.platform.repository.UserRepository;

public class ValidatorMappingHelper {

	public static User getUserObject(String userId) {
		return StaticContextAccessor.getBean(UserRepository.class).findById(userId).get();
	}

}