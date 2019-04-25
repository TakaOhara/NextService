package com.example.dbtest.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dbtest.domain.entity.Profile;
import com.example.dbtest.domain.entity.Task;
import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.service.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
    private final ProfileService profileService;
	private final ResourceLoader resourceLoader;
    
    public ProfileController(ProfileService profileService,
			ResourceLoader resourceLoader) {
    	this.profileService = profileService;
    	this.resourceLoader = resourceLoader;
    }
    
    /**
     * 会員個々のプロフィールをフォームとして表示する
     * @param profileForm
     * @param model
     * @return String
     */
    @GetMapping
    public String profile (Model model, Principal principal)throws IOException {
    	
    	int userInfoId = 0;
    	if(principal !=  null) {//認証前はnull
        	Authentication auth = (Authentication)principal;
            UserInfo userInfo = (UserInfo)auth.getPrincipal();
            userInfoId = userInfo.getId();
        }
    	
        Optional<ProfileForm> profileForm = profileService.getProfileForm(userInfoId);
        
        model.addAttribute("profileForm", profileForm.get());
        model.addAttribute("title", "マイプロフィール");
        
		// 1. resourcesにあるdog.jpgを取得
		Resource res = resourceLoader.getResource("classpath:animal/dog.jpg");

		// アプリケーション外のファイルを参照する場合は下記のようにします。
		//Resource res = new FileSystemResource("c:/temp/dog.jpg");

		// 2. Fileからbyte配列へ変換する
		byte[] image = FileCopyUtils.copyToByteArray(res.getFile());

		// DBから取得する場合はBLOBをbyte配列で取得できますので、2へ合流できます

		// byte配列をBASE64文字列に変換
		String encodedImage = Base64.getEncoder().encodeToString(image);

		// BASE64文字列を返却
		model.addAttribute("image", encodedImage);

        return "profile_old";
    	
    }
    
//    @PostMapping(name = "edit")
//    public String update(
//        	@Valid @ModelAttribute ProfileForm profileForm,
//        	BindingResult result,
//        	Model model,
//            Principal principal) {
//        	
//        	int userId = 0;
//        	if(principal !=  null) {//認証前はnull
//            	Authentication auth = (Authentication)principal;
//                UserInfo userInfo = (UserInfo)auth.getPrincipal();
//                userId = userInfo.getId();
//            }
//        	
//        	Profile profile = makeProfile(userId, profileForm);
//        	
//            if (!result.hasErrors()) {
//            	profileService.save(profile);
//                return "redirect:/profile";
//            } else {
//                model.addAttribute("profileForm", profileForm);
//                model.addAttribute("title", "プロフィール");
//                return "task";
//            }
//    
}
