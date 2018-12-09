package com.example.dbtest.controllers;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.dbtest.entity.UserInfo;

@ControllerAdvice // クラスレベルに@ControllerAdviceを指定する
public class CustomControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // WebDataBinderのメソッドを呼び出してカスタマイズする
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public void addSomeObjects( Model model, Principal principal) {
    	int id = 0;
    	String username = null;
    	UserInfo userInfo;
    	//認証情報の取得
    	if(principal !=  null) {
    	Authentication auth = (Authentication)principal;
        userInfo = (UserInfo)auth.getPrincipal();
        id = userInfo.getId();
        username = userInfo.getUsername();
    	}
        
        model.addAttribute("id", id);
        model.addAttribute("username", username);
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleSystemException(Exception e) {
//        // 例外ハンドリングを行う
//        logger.error("System Error occurred.", e);
//        return "error/system";
//    }

}