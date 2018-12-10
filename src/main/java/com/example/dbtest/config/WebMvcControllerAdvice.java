package com.example.dbtest.config;

import java.security.Principal;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.dbtest.entity.UserInfo;

/**
 * 全てのControllerで共通処理を定義
 */
@ControllerAdvice
public class WebMvcControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // Stringの空文字をNULLに
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    @ModelAttribute
    public void addSomeObjects( Model model, Principal principal) {
    	
    	int id = 0;
    	String username = null;
    	
    	//認証情報の取得
    	if(principal !=  null) {//認証前はnull
    	Authentication auth = (Authentication)principal;
        UserInfo userInfo = (UserInfo)auth.getPrincipal();
        id = userInfo.getId();
        username = userInfo.getUsername();
    	}
        
        model.addAttribute("id", id);
        model.addAttribute("username", username);
    }
}
