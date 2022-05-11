package com.ysy.page.controller.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.entity.Favorite;
import com.ysy.page.service.FavoriteService;

@RestController
@RequestMapping("/ysy/v1/favorite")
public class FavoriteCotroller {

	
	@Autowired
	private FavoriteService favoriteService;
	
	
	@GetMapping("/dataList")
	public List<Favorite> dataList() {
		
		return favoriteService.dataList();
	}
	
}
