package com.example.dbtest.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dbtest.entity.AdminInfo;
import com.example.dbtest.repositories.AdminInfoRepository;

@Service("AdminInfoService")
public class AdminInfoService implements UserDetailsService {
	
	private final AdminInfoRepository AdminInfoRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminInfoService(AdminInfoRepository AdminInfoRepository, BCryptPasswordEncoder passwordEncoder) {
        this.AdminInfoRepository = AdminInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);//user@test到着

		if(email == null || email.isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}
		
		AdminInfo adminInfo = AdminInfoRepository.findByEmail(email);

		if(adminInfo == null) {
			throw new UsernameNotFoundException("User not found for email:" + email);
		}

		return adminInfo;
	}
	
	@Transactional
	public void save(AdminInfo adminInfo) {
		String hushPass = passwordEncoder.encode(adminInfo.getPassword());
		adminInfo.setPassword(hushPass);
		AdminInfoRepository.save(adminInfo);
	}

}
