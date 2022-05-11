package com.ysy.page.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.entity.Favorite;
import com.ysy.page.repository.FavoriteRepository;

@Service
public class FavoriteService {

	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	
	
	
	public List<Favorite> dataList() {
		//favoriteRepository.getSampleDataList();
		List<Favorite> dataList = favoriteRepository.findAll();
		
		return dataList;
	}
}
