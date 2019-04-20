package com.example.dbtest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.repositories.UserInfoRepository;

public class UserInfoExistsValidator implements ConstraintValidator<UserInfoExists, String> {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public void initialize(UserInfoExists annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

//        if (value == null) return true;
//
//        UserInfo userInfo = userInfoRepository.findByEmail(value);
//
//        return userInfo == null;
    	return true;

    }
}
