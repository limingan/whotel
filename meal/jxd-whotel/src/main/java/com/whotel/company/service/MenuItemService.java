package com.whotel.company.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.core.api.MenuCreator;
import com.weixin.core.bean.CommonButton;
import com.weixin.core.bean.ComplexButton;
import com.weixin.core.bean.Menu;
import com.weixin.core.common.BaseToken;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.util.BeanUtil;
import com.whotel.company.dao.MenuItemDao;
import com.whotel.company.entity.MenuItem;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.ResponseMsg;
import com.whotel.company.enums.ResponseType;
import com.whotel.weixin.service.TokenService;

/**
 * 公众号自定义菜单处理服务类
 * @author 冯勇
 *
 */
@Service
public class MenuItemService {
	
	@Autowired
	private MenuItemDao menuItemDao;
	
	@Autowired
	private PublicNoService publicNoService;
	
	/**
	 * 保存菜单
	 * @param menuItem
	 */
	public void saveMenuItem(MenuItem menuItem) {
		if(menuItem != null) {
			String id = menuItem.getId();
			if(StringUtils.isNotBlank(id)) {
				MenuItem updateMenuItem = getMenuItemById(id);
				BeanUtil.copyProperties(updateMenuItem, menuItem, true);
				menuItemDao.save(updateMenuItem);
			} else {
				List<MenuItem> menuItemsTree = findMenuItemsTree(menuItem.getCompanyId());
				if(menuItemsTree != null) {
					int index = 0;
					for(MenuItem menu:menuItemsTree) {
						if(StringUtils.equals(menuItem.getParentId(), menu.getId())) {
							List<MenuItem> childrens = findMenuItemByParentId(menu.getId());
							int cIndex = 0;
							if(childrens != null) {
								cIndex = childrens.size();
							}
							menuItem.setOrder(cIndex + 1);
							break;
						}
						index ++;
						menuItem.setOrder(index + 1);
					}
				} else {
					menuItem.setOrder(0);
				}
				menuItemDao.save(menuItem);
			}
		}
	}
	
	/**
	 * 更新菜单顺序
	 * @param companyId
	 */
	public void updateMenuItemOrder(String companyId) {
		List<MenuItem> menuItemsTree = findMenuItemsTree(companyId);
		if(menuItemsTree != null) {
			int index = 0;
			for(MenuItem menu:menuItemsTree) {
					List<MenuItem> childrens = findMenuItemByParentId(menu.getId());
					if(childrens != null) {
						int cIndex = 0;
						for(MenuItem children:childrens) {
							children.setOrder(cIndex);
							cIndex ++;
							saveMenuItem(children);
						}
					}
					menu.setOrder(index);
					index ++;
					saveMenuItem(menu);
				}
			}
	}
	
	public MenuItem getMenuItemById(String id) {
		return menuItemDao.get(id);
	}
	
	public List<MenuItem> findMenuItemByParentId(String id) {
		return menuItemDao.findByProperty("parentId", id, Order.asc("order"));
	}
	
	/**
	 * 生成菜单树
	 * @param companyId
	 * @return
	 */
	public List<MenuItem> findMenuItemsTree(String companyId) {
		Query<MenuItem> query = menuItemDao.createQuery();
		query.field("companyId").equal(companyId);
		query.field("parentId").doesNotExist();
		query.order(MenuItemDao.getOrderString(Order.asc("order")));
		
		List<MenuItem> topMenuItems = query.asList();
		if(topMenuItems != null) {
			for(MenuItem item:topMenuItems) {
				List<MenuItem> children = findMenuItemByParentId(item.getId());
				if(children != null) {
					item.setChildren(children);
				}
			}
		}
		return topMenuItems;
	}
	
	/**
	 * 发布自定义菜单到微信服务器
	 * @param companyId
	 * @return
	 */
	public boolean publishMenuItem(String companyId) {
		if(StringUtils.isBlank(companyId)) {
			return false;
		}
		
		List<MenuItem> menuItems = findMenuItemsTree(companyId);
		if(menuItems != null && menuItems.size() > 0) {
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
			Menu menu = new Menu();
			for(MenuItem mi:menuItems) {
				if(mi.isLeaf()) {
					CommonButton commonBtn = new CommonButton();
					commonBtn.setName(mi.getName());
					ResponseMsg rm = mi.getResponseMsg();
					if(rm != null) {
						if(ResponseType.LINK.equals(rm.getResponseType())){
							commonBtn.setUrl(rm.getUrl());
						}else if(ResponseType.SCANCODE.equals(rm.getResponseType())){
							commonBtn.setKey(mi.getId());
							commonBtn.setType("scancode_push");
						}else if(ResponseType.UNLOCK.equals(rm.getResponseType())){
							commonBtn.setKey(mi.getId());
						} else {
							commonBtn.setKey(mi.getId());
						}
					}
					menu.addButton(commonBtn);
				} else {
					ComplexButton complexBtn = new ComplexButton();
					complexBtn.setName(mi.getName());
					
					List<MenuItem> childrens = mi.getChildren();
					for(MenuItem children : childrens) {
						CommonButton commonBtn = new CommonButton();
						
						commonBtn.setName(children.getName());
						ResponseMsg rm = children.getResponseMsg();
						if(rm != null) {
							if(ResponseType.LINK.equals(rm.getResponseType())){
								commonBtn.setUrl(rm.getUrl());
							}else if(ResponseType.SCANCODE.equals(rm.getResponseType())){
								commonBtn.setKey(children.getId());
								commonBtn.setType("scancode_push");
							}else if(ResponseType.UNLOCK.equals(rm.getResponseType())){
								commonBtn.setKey(children.getId());
							} else {
								commonBtn.setKey(children.getId());
							}
						}
						complexBtn.addButton(commonBtn);
					}
					menu.addButton(complexBtn);
				}
			}
			try {
				BaseToken accessToken = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
				BaseToken token = MenuCreator.createMenu(menu, accessToken);
				if(token != null && StringUtils.equals(token.getErrcode(), "0")) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void deleteMenuItem(MenuItem menuItem) {
		menuItemDao.delete(menuItem);
	}
	
	/**
	 * 批量删除菜单
	 * @param ids
	 */
	public void deleteMoreMenuItem(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				List<MenuItem> childrens = findMenuItemByParentId(id);
				if(childrens != null) {
					for(MenuItem child:childrens) {
						child.setCompanyId(companyId);
						deleteMenuItem(child);                 //先删除子菜单
					}
				}
				MenuItem menuItem = getMenuItemById(id);
				menuItem.setCompanyId(companyId);
				deleteMenuItem(menuItem);
			}
			updateMenuItemOrder(companyId);
		}
	}

	/**
	 * 批量更新菜单排序
	 * @param idOrders
	 */
	public void updateMenuOrders(String[] idOrders) {
		if(idOrders != null) {
			for(String idOrder:idOrders) {
				String[] ids = idOrder.split(",");
				if(ids.length == 2) {
					MenuItem menuItem = getMenuItemById(ids[0]);
					menuItem.setOrder(Integer.valueOf(ids[1]));
					saveMenuItem(menuItem);
				}
			}
		}
		
	}
}
