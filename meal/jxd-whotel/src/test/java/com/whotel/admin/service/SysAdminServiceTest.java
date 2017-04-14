package com.whotel.admin.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.admin.entity.SysAdmin;
import com.whotel.admin.entity.SysRole;
import com.whotel.common.util.EncryptUtil;
/**
 * 后台管理管理服务
 * @author 冯勇
 *
 */
@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class SysAdminServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private SysAdminService adminService;
	
	@Autowired
	private SysRoleService roleService;
	
	
	@Test
	public void testAddAdmin() {
		
		try {
			SysRole role = new SysRole();
			role.setName("超级管理员");
			role.setDescr("系统维护");
			roleService.saveSysRole(role);
			
			SysAdmin admin = new SysAdmin();
			admin.setUserName("admin");
			admin.setPassword(EncryptUtil.md5("1qaz@wsx"));
			admin.setRoleId(role.getId());
			adminService.saveSysAdmin(admin);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMd5() {
		System.out.println(EncryptUtil.md5("1qaz@wsx"));
	}
	
	@Test
	public void updateAdmin() {
		SysAdmin admin = adminService.getSysAdminByUserName("admin");
		System.out.println(admin.getUserName());
		
		admin.setPassword(EncryptUtil.md5("jxd2015"));
		
		adminService.saveSysAdmin(admin);
	}
	

}
