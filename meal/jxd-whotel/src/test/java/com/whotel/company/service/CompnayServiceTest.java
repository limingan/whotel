package com.whotel.company.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.company.entity.CompanyAdmin;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.service.HotelOrderService;

@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class CompnayServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CompanyService companyService;
	
	@Test
	public void testfindCompanyAdmin() {
		List<CompanyAdmin> list = companyService.findCompanyAdminAll("55ed0b51f19aa1122cbd1a7e", "2");
		for (CompanyAdmin admin : list) {
			System.out.println(admin.getName());
		}
	}
}
