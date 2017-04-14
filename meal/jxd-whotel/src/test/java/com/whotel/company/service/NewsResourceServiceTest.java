package com.whotel.company.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.company.entity.NewsResource;

@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class NewsResourceServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private NewsResourceService newsResourceService;
	
	
	@Test
	public void testSynchronizeNewsToMP() {
		
		NewsResource newsResource = newsResourceService.getNewsResourceById("55e662a8cb0d7467ea4ac6a6");
		String mediaId = newsResourceService.synchronizeNewsToMP(newsResource);
		System.out.println(mediaId+"------------");
	}
	
	@Test
	public void testUpdateNewsResource() {
		
	}
	
}
