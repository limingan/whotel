package com.whotel.common.util;

import com.whotel.admin.dao.SysRoleDao;
import com.whotel.admin.entity.SysRole;
import com.whotel.common.dao.RegionDao;
import com.whotel.common.entity.Region;
import com.whotel.company.dao.CompanyAdminDao;
import com.whotel.company.dao.CompanyDao;
import com.whotel.company.dao.CompanyRoleDao;
import com.whotel.company.dao.NewsResourceDao;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.CompanyRole;
import com.whotel.company.entity.NewsResource;
import com.whotel.front.dao.WeixinFanDao;
import com.whotel.front.entity.WeixinFan;
import com.whotel.meal.dao.MealTabDao;
import com.whotel.meal.dao.RestaurantDao;
import com.whotel.meal.dao.ShuffleDao;
import com.whotel.meal.entity.MealTab;
import com.whotel.meal.entity.Restaurant;
import com.whotel.meal.entity.Shuffle;
import com.whotel.webiste.dao.ColumnNewsDao;
import com.whotel.webiste.dao.ColumnTemplateDao;
import com.whotel.webiste.entity.ColumnNews;
import com.whotel.webiste.entity.ColumnTemplate;
import com.whotel.card.dao.DepartmentDao;
import com.whotel.card.dao.MemberDao;
import com.whotel.card.entity.Department;
import com.whotel.card.entity.Member;


/**
 * 数据仓库工具，一个门面类，集成了最常用的类的用ID获取对象的方法和获取对应Dao的方法
 * 
 */
public class RepoUtil {
	private static SysRoleDao roleDao;
	
	private static CompanyDao companyDao;
	
	private static NewsResourceDao newsResourceDao;
	
	private static WeixinFanDao weixinFanDao;
	
	private static MemberDao memberDao;
	
	private static ColumnNewsDao columnNewsDao;
	
	private static CompanyRoleDao companyRoleDao;
	
	private static ColumnTemplateDao columnTemplateDao;
	
	private static RegionDao regionDao;
	
	private static DepartmentDao DepartmentDao;
	
	private static CompanyAdminDao companyAdminDao;
	
	private static RestaurantDao restaurantDao;
	
	private static ShuffleDao shuffleDao;
	
	private static MealTabDao mealTabDao;
	
	public static MealTabDao getMealTabDao() {
		if (mealTabDao == null)
			mealTabDao = SpringContextHolder.getBean(MealTabDao.class);
		return mealTabDao;
	}
	
	public static RestaurantDao getRestaurantDao() {
		if (restaurantDao == null)
			restaurantDao = SpringContextHolder.getBean(RestaurantDao.class);
		return restaurantDao;
	}

	public static ShuffleDao getShuffleDao() {
		if (shuffleDao == null)
			shuffleDao = SpringContextHolder.getBean(ShuffleDao.class);
		return shuffleDao;
	}
	
	
	public static CompanyAdminDao getCompanyAdminDao() {
		if (companyAdminDao == null)
			companyAdminDao = SpringContextHolder.getBean(CompanyAdminDao.class);
		return companyAdminDao;
	}
	
	public static SysRoleDao getSysRoleDao() {
		if (roleDao == null)
			roleDao = SpringContextHolder.getBean(SysRoleDao.class);
		return roleDao;
	}
	
	public static CompanyDao getCompanyDao() {
		if (companyDao == null)
			companyDao = SpringContextHolder.getBean(CompanyDao.class);
		return companyDao;
	}
	
	public static NewsResourceDao getNewsResourceDao() {
		if (newsResourceDao == null)
			newsResourceDao = SpringContextHolder.getBean(NewsResourceDao.class);
		return newsResourceDao;
	}
	
	public static WeixinFanDao getWeixinFanDao() {
		if (weixinFanDao == null)
			weixinFanDao = SpringContextHolder.getBean(WeixinFanDao.class);
		return weixinFanDao;
	}
	
	public static MemberDao getMemberDao(){
		if (memberDao == null)
			memberDao = SpringContextHolder.getBean(MemberDao.class);
		return memberDao;
	}
	
	public static ColumnNewsDao getColumnNewsDao() {
		if (columnNewsDao == null)
			columnNewsDao = SpringContextHolder.getBean(ColumnNewsDao.class);
		return columnNewsDao;
	}
	
	public static CompanyRoleDao getCompanyRoleDao() {
		if (companyRoleDao == null)
			companyRoleDao = SpringContextHolder.getBean(CompanyRoleDao.class);
		return companyRoleDao;
	}
	
	public static ColumnTemplateDao getColumnTemplateDao() {
		if (columnTemplateDao == null)
			columnTemplateDao = SpringContextHolder.getBean(ColumnTemplateDao.class);
		return columnTemplateDao;
	}
	
	public static RegionDao getRegionDao() {
		if (regionDao == null)
			regionDao = SpringContextHolder.getBean(RegionDao.class);
		return regionDao;
	}
	
	public static DepartmentDao getDepartmentDao() {
		if (DepartmentDao == null)
			DepartmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		return DepartmentDao;
	}
	
	public static SysRole getSysRole(String roleId) {
		return getSysRoleDao().get(roleId);
	}
	
	public static Company getCompany(String companyId) {
		return getCompanyDao().get(companyId);
	}
	
	public static NewsResource getNewsResource(String newsId) {
		return getNewsResourceDao().get(newsId);
	}
	
	public static WeixinFan getWeixinFan(String openId) {
		return getWeixinFanDao().getByProperty("openId", openId);
	}
	
	public static Member getMemberByOpenId(String openId){
		return getMemberDao().getByProperty("openId", openId);
	}
	
	public static ColumnNews getColumnNews(String newsId) {
		return getColumnNewsDao().get(newsId);
	}
	
	
	public static CompanyRole getCompanyRole(String roleId) {
		return getCompanyRoleDao().get(roleId);
	}
	
	public static ColumnTemplate getColumnTemplate(String templateId) {
		return getColumnTemplateDao().get(templateId);
	}
	
	public static Region getRegionByRegionId(Integer regionId) {
		return getRegionDao().getByProperty("regionId", regionId);
	}
	public static Department getDepartmentById(String id) {
		return getDepartmentDao().getByProperty("id", id);
	}
	
	public static CompanyAdmin getCompanyAdmin(String openId) {
		return getCompanyAdminDao().getByProperty("openId", openId);
	}
	
	public static CompanyAdmin getCompanyAdminById(String id) {
		return getCompanyAdminDao().getByProperty("id", id);
	}
	
	public static Restaurant getRestaurantById(String restaurantId) {
		return getRestaurantDao().getByProperty("id", restaurantId);
	}
	
	public static Shuffle getShuffleById(String shuffleId) {
		return getShuffleDao().getByProperty("id", shuffleId);
	}
	
	public static MealTab getMealTabById(String mealTabId) {
		return getMealTabDao().getByProperty("id", mealTabId);
	}
}
