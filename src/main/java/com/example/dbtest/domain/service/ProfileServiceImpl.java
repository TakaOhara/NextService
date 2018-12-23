package com.example.dbtest.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dbtest.controllers.ProfileForm;
import com.example.dbtest.domain.entity.Profile;
import com.example.dbtest.domain.entity.Task;
import com.example.dbtest.domain.repositories.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository repository;

    /**
     * コンストラクタ
     * @param repository
     */
	@Autowired // コンストラクタインジェクション
	public ProfileServiceImpl(ProfileRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Profile> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<ProfileForm> getProfileForm(int userInfoId) {
		Optional<Profile> profile = repository.findByUserInfoId(userInfoId);

		if(!profile.isPresent()) {
			return Optional.empty(); // emptyを使うのが望ましいです
		}
        // mapを使ってスマートに記述
		return profile.map(prof ->//1ラムダ式　profは保持する実際の値
                new ProfileForm(prof.getId(), prof.getLocationId(), prof.getProfileName(), prof.getHobby(), false));
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Profile profile) {
		repository.saveAndFlush(profile);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(int id) {
		if(repository.findByUserInfoId(id).isPresent()) { // 不要な変数宣言の省略
			repository.deleteById(id);
		}
	}

	@Override
	public boolean existsByUserInfoId(int id) {
		return repository.existsByUserInfoId(id);
	}

	@Override
	public Optional<Profile> findByUserInfoId(int id) {
		return findByUserInfoId(id);
	}
}
