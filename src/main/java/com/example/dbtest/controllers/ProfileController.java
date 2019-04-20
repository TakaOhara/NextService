package com.example.dbtest.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dbtest.domain.entity.Profile;
import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.service.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //INDEX
    @GetMapping
    public String profile(ProfileForm profileForm, 
    	Model model,
    	Principal principal) {
    	
    	int userInfoId = 0;
    	if(principal !=  null) {//認証前はnull
        Authentication auth = (Authentication)principal;
        UserInfo userInfo = (UserInfo)auth.getPrincipal();
        userInfoId = userInfo.getId();
    	}

    	if(profileService.existsByUserInfoId(userInfoId)) {
    		profileForm.setNewProfile(false);
    		model.addAttribute("title", "プロフィール編集");
    	}else {
    		profileForm.setNewProfile(true);
    		model.addAttribute("title", "プロフィール新規登録");
    	}
    	
        List<Profile> list = profileService.findAll();
        
        Optional<ProfileForm> form = profileService.getProfileForm(userInfoId);

        model.addAttribute("profileForm", form.get());
        model.addAttribute("existsProf", profileService.existsByUserInfoId(userInfoId));
        model.addAttribute("list", list);

        return "profile";
    }
    

    //INSERT
    @PostMapping(params = "new")
    public String insert(
    	@Valid @ModelAttribute ProfileForm profileForm, //ヴァリデーションはフォームクラスに対して行う
        BindingResult result,
        Model model,
        Principal principal) {
    	
    	int userId = 0;
    	if(principal !=  null) {//認証前はnull
        	Authentication auth = (Authentication)principal;
            UserInfo userInfo = (UserInfo)auth.getPrincipal();
            userId = userInfo.getId();
        }

        Profile profile = makeNewProfile(userId, profileForm);
        //redirect、失敗したらそのままHTML表示
        if (!result.hasErrors()) {
            profileService.save(profile);
            return "redirect:/profile";
        } else {
            profileForm.setNewProfile(true);
            model.addAttribute("profileForm", profileForm);
            List<Profile> list = profileService.findAll();
            model.addAttribute("list", list);//sessionに格納
            model.addAttribute("title", "プロフィール一覧 afterInsert");
            return "profile";
        }
    }

    
    /**
     * UPDATE
     * @param id
     * @param profileForm
     * @param mav
     * @return
     */
    @PostMapping(params = "edit")
    public String update(
    	@Valid @ModelAttribute ProfileForm profileForm,
    	BindingResult result,
    	Model model,
        Principal principal) {
    	
    	int userInfoId = 0;
    	if(principal !=  null) {//認証前はnull
        	Authentication auth = (Authentication)principal;
            UserInfo userInfo = (UserInfo)auth.getPrincipal();
            userInfoId = userInfo.getId();
        }
    	
    	//isNewTaskにfalseが代入される
        Optional<ProfileForm> form = profileService.getProfileForm(userInfoId);

        if (!form.isPresent()) {
            return "redirect:/profile";
        }
    	
    	Profile profile = makeProfile(userInfoId, profileForm);
    	
        if (!result.hasErrors()) {
        	profileService.save(profile);
            return "redirect:/profile/?complete";
        } else {
            model.addAttribute("profileForm", profileForm);
            model.addAttribute("title", "タスク編集画面");
            return "profile";
        }
        
    }

    /**
     * DELETE
     * @param id
     * @param mav
     * @return
     */
    @PostMapping("/delete/{id}")
    public String delete(
    	@PathVariable Integer id,
    	Model model) {
    	
        profileService.deleteById(id);
        return "redirect:/profile";
    }

    private Profile makeNewProfile(int userId, ProfileForm profileForm) {
    	LocalDateTime d = LocalDateTime.now();
        return new Profile(userId, profileForm.getLocationId(), profileForm.getProfileName(), profileForm.getHobby(), d);
    }

    private Profile makeProfile(int userId, ProfileForm profileForm) {//Update用
    	LocalDateTime d = LocalDateTime.now();
    	return new Profile(profileForm.getId(), userId, profileForm.getLocationId(), profileForm.getProfileName(), profileForm.getHobby(), d);
    }


}
