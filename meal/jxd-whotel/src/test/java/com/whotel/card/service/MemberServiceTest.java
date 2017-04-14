package com.whotel.card.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.card.entity.Member;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;

@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class MemberServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MemberService memberService;
	
	@Test
	public void testSynchronizeMemberToJXD() {
		Member member = memberService.getMemberById("55faa099ef0a502730783cb3");
		MemberVO re = memberService.synchronizeMemberToJXD(member,null);
		System.out.println(re);
	}
	
	@Test
	public void testDeleteMember() {
		memberService.deleteMember("560a40b0cb0d740e5620a443");
	}
}
