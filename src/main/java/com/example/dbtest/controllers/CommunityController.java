package com.example.dbtest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	/**
	 * コミュニティ一覧表示
	 *
	 * @return List<Community>
	 */
	public List<Community> showAll(){
		return null
	}

}
