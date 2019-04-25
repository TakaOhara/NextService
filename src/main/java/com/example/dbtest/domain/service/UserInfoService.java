package com.example.dbtest.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.repositories.UserInfoRepository;

@Service("UserInfoService")
public class UserInfoService implements UserDetailsService {
	
	private final UserInfoRepository userInfoRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository,
    		BCryptPasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);//user@test到着

		if(email == null || email.isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}
		
		UserInfo userInfo = userInfoRepository.findByEmail(email);

		if(userInfo == null) {
			throw new UsernameNotFoundException("User not found for email:" + email);
		}

		return userInfo;
	}
    
    public UserInfo findById(int id) {
    	return userInfoRepository.findById(id);
    }

	public UserInfo findByTempkey(String tempkey) {
    	return userInfoRepository.findByTempkey(tempkey);
	}
	
	@Transactional
	public void save(UserInfo userInfo) {
		String hushPass = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(hushPass);
		userInfoRepository.save(userInfo);
	}

	@Transactional
	public void setEnabled(UserInfo userInfo) {
		userInfo.setEnabled(true);
		userInfoRepository.save(userInfo);
	}
	
	//ログイン中の会員id(user_infoテーブル)を取得する
	public int getMyId() {
		int userId = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserInfo) {
			userId = ((UserInfo) principal).getId(); // (3)
        }
		return userId;

	}

}