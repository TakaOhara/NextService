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

import com.example.dbtest.domain.entity.User;
import com.example.dbtest.domain.repositories.UserRepository;

@Service("UserInfoService")
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
    		BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);//user@test到着

		if(email == null || email.isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}
		
		User userInfo = userRepository.findByEmail(email);

		if(userInfo == null) {
			throw new UsernameNotFoundException("User not found for email:" + email);
		}

		return userInfo;
	}
    
    public User findById(int id) {
    	return userRepository.findById(id);
    }

	public User findByTempkey(String tempkey) {
    	return userRepository.findByTempkey(tempkey);
	}
	
	@Transactional
	public void save(User user) {
		String hushPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(hushPass);
		userRepository.save(user);
	}

	@Transactional
	public void setEnabled(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	//ログイン中の会員id(user_infoテーブル)を取得する
	public int getId() {
		int userId = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			userId = ((User) principal).getId(); // (3)
        }
		return userId;

	}

}