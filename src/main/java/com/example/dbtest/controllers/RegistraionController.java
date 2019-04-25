package com.example.dbtest.controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dbtest.domain.entity.Profile;
import com.example.dbtest.domain.entity.User;
import com.example.dbtest.domain.service.ProfileService;
import com.example.dbtest.domain.service.SendVerifyMailService;
import com.example.dbtest.domain.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistraionController {

    private final UserService userService;
    private final ProfileService profileService;
    private final SendVerifyMailService sendVerifyMailService;

    @Autowired
    public RegistraionController(UserService userService, 
    		ProfileService profileService,
    		SendVerifyMailService sendVerifyMailService) {
        this.userService = userService;
        this.profileService = profileService;
        this.sendVerifyMailService = sendVerifyMailService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public ModelAndView showMyLoginPage(
            @ModelAttribute("RegistrationForm") RegistrationForm registrationForm,
            ModelAndView mav) {
        registrationForm.setNewRegistration(true);
        mav.addObject("registrationForm", registrationForm);
        mav.addObject("title", "会員登録");
        mav.setViewName("registration-form");
        return mav;
    }

    @PostMapping("/processRegistrationForm")
    public ModelAndView processRegistrationForm(
            @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
            BindingResult result,
            ModelAndView mav) {

        // form validation
        if (!result.hasErrors()) {
            User user = makeUser(registrationForm);
            userService.save(user);
            sendVerifyMailService.execute(user);

            mav.setViewName("registration-confirmation");
        } else {
            registrationForm.setNewRegistration(true);
            mav.addObject("registrationForm", registrationForm);
            mav.addObject("title", "会員登録");
            mav.setViewName("registration-form");



        }
        return mav;

    }

    @GetMapping("/verify")
    public String verify(String id) {

        User user = userService.findByTempkey(id);

        if(user == null || user.isEnabled()) {
            return "error"; //エラーページへ遷移
        }

        userService.setEnabled(user);
        Profile prf = new Profile();
        prf.setUserId(user.getId());
        prf.setUpdated(LocalDateTime.now());
        profileService.save(prf);

        return "verify";
    }


    private User makeUser(RegistrationForm registrationForm) {
        return new User(registrationForm.getUsername(), registrationForm.getEmail(),
                registrationForm.getPassword(), false, "ROLE_USER", UUID.randomUUID().toString());
    }


}