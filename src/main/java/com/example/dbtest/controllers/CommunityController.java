package com.example.dbtest.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dbtest.domain.entity.Community;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	/**
	 * コミュニティ一覧表示
	 *
	 * @return List<Community>
	 */
	public List<Community> showAll(){
		return null;
	}

}
