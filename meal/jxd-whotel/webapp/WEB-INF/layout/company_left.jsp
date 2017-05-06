<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- BEGIN SIDEBAR -->
<c:set var="urlMap" value="${COMPANY_PRIVILEGE_MAP}" scope="request" />
<div class="page-sidebar-wrapper">
	<div class="page-sidebar navbar-collapse collapse">
		<!-- BEGIN SIDEBAR MENU -->
		<ul class="page-sidebar-menu">
			<li class="start <c:if test="${cur=='sel-index'}">active</c:if>">
				<a href="/company/index.do"><i class="fa fa-home"></i><span
					class="title">首页</span><span class="selected"></span></a>
			</li>
			<li id="company" <c:if test="${cur=='sel-company'}">class="active"</c:if>>
				<a href="javascript:;">
					<i class="fa fa-table"></i><span class="title">商户管理</span><span class="arrow"></span>
				</a>
				<ul class="sub-menu">
					<c:if test="${urlMap['/company/toEditCompany.do']==null}">
						<li <c:if test="${cur_sub=='sel-company-info'}">class="active"</c:if>>
							<a href="/company/toEditCompany.do">商户信息</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/authority/listCompanyRole.do']==null}">
						<li <c:if test="${cur_sub=='sel-role'}">class="active"</c:if>>
							<a href="/company/authority/listCompanyRole.do"> 角色管理 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/listCompanyAdmin.do']==null}">
						<li <c:if test="${cur_sub=='sel-company-admin'}">class="active"</c:if>>
							<a href="/company/listCompanyAdmin.do">商户员工</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/listWeixinFan.do']==null}">
						<li <c:if test="${cur_sub=='sel-company-fan'}">class="active"</c:if>>
							<a href="/company/listWeixinFan.do">商户粉丝</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/listMember.do']==null}">
						<li <c:if test="${cur_sub=='sel-company-member'}">class="active"</c:if>>
							<a href="/company/listMember.do">商户会员</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/toListPayConfig.do']==null}">
						<li <c:if test="${cur_sub=='sel-pay-config'}">class="active"</c:if>>
							<a href="/company/toListPayConfig.do"> 支付配置 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/toTemplateMessage.do']==null}">
						<li <c:if test="${cur_sub=='sel-template_msg'}">class="active"</c:if>>
							<a href="/company/toTemplateMessage.do"> 模版消息 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/toEditSmsConfig.do']==null}">
						<li <c:if test="${cur_sub=='sel-smsConfig'}">class="active"</c:if>>
							<a href="/company/toEditSmsConfig.do"> 短信配置 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/toEditSmsConfig.do']==null}">
						<li <c:if test="${cur_sub=='sel-sysConfig'}">class="active"</c:if>>
							<a href="/company/toSysParamConfig.do"> 系统配置</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/syslog/listSysOperationLogs.do']==null}">
						<li <c:if test="${cur_sub=='sel-syslog'}">class="active"</c:if>>
							<a href="/company/syslog/listSysOperationLogs.do"> 系统日志</a>
						</li>
					</c:if>
				</ul>
			</li>
			
			
			<li id="publicno" <c:if test="${cur=='sel-publicno'}">class="active"</c:if>>
				<a href="javascript:;">
					<i class="fa fa-comments-o"></i><span class="title">公众号</span> <span class="arrow"> </span>
				</a>
				<ul class="sub-menu">
					<c:if test="${urlMap['/company/publicno/publicNoInfo.do']==null}">
						<li <c:if test="${cur_sub=='sel-publicno-info'}">class="active"</c:if>>
							<a href="/company/publicno/publicNoInfo.do"> 公众号 </a>
						</li>
					</c:if>
				</ul>
			</li>

			<li id="webcard" <c:if test="${cur=='sel-webcard'}">class="active"</c:if>>
				<a href="javascript:;"> 
					<i class="fa fa-credit-card"></i><span class="title">微会员</span> <span class="arrow"></span>
				</a>
				<ul class="sub-menu">
					<c:if test="${urlMap['/company/webcard/listCouponTypes.do']==null}">
						<li
							<c:if test="${cur_sub=='sel-webcard-couponType'}">class="active"</c:if>>
							<a href="/company/webcard/listCouponTypes.do"> 券类型 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/webcard/listMbrCardTypes.do']==null}">
						<li
							<c:if test="${cur_sub=='sel-webcard-mbrCardType'}">class="active"</c:if>>
							<a href="/company/webcard/listMbrCardTypes.do">会员卡类型 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/webcard/toMemberPolicyRecord.do']==null}">
						<li
							<c:if test="${cur_sub=='sel-webcard-memberPolicy'}">class="active"</c:if>>
							<a href="/company/webcard/toMemberPolicyRecord.do">会员政策</a>
						</li>
					</c:if>
				</ul>
			</li>

			<li id="whotel" <c:if test="${cur=='sel-whotel'}">class="active"</c:if>>
				<a href="javascript:;">
					<i class="fa fa-cutlery"></i><span class="title">微酒店</span> <span class="arrow"> </span>
				</a>
				<ul class="sub-menu">
					<c:if test="${urlMap['/company/hotel/listHotels.do']==null}">
						<li <c:if test="${cur_sub=='sel-hotel-list'}">class="active"</c:if>>
							<a href="/company/hotel/listHotels.do"> 酒店列表 </a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/hotel/listHotelComment.do']==null}">
						<li <c:if test="${cur_sub=='sel-hotelComment-list'}">class="active"</c:if>>
							<a href="/company/hotel/listHotelComment.do"> 酒店点评 </a>
						</li>
					</c:if>
				</ul>
			</li>
			
			<c:if test="${urlMap['/company/notice/listCompanyNotice.do']==null}">
				<li <c:if test="${cur=='sel-notice'}">class="active"</c:if>>
					<a href="/company/notice/listCompanyNotice.do">
						<i class="fa fa-bell"></i><span class="title">商户公告</span><span class="selected"></span>
					</a>
				</li>
			</c:if>
			
						
			<li id="wmeal" <c:if test="${cur=='sel-meal'}">class="active"</c:if>>
				<a href="javascript:;">
					<i class="fa fa-cutlery"></i><span class="title">微餐饮</span> <span class="arrow"> </span>
				</a>
				<ul class="sub-menu">
					<c:if test="${urlMap['/company/meal/listRestaurant.do']==null}">
						<li <c:if test="${cur_sub=='sel-meal-restaurant'}">class="active"</c:if>>
							<a href="/company/meal/listRestaurant.do">餐厅管理</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/meal/listShuffle.do']==null}">
						<li <c:if test="${cur_sub=='sel-meal-shuffle'}">class="active"</c:if>>
							<a href="/company/meal/listShuffle.do">市别管理</a>
						</li>
					</c:if>
					<li id="dishes_manage" <c:if test="${cur_sub_leaf=='sel-dishes-manage-leaf'}">class="active"</c:if>>
						<a href="javascript:;">
							<i class="fa "></i><span class="title">菜肴管理</span> <span class="arrow"> </span>
						</a>
						<ul class="sub-menu">
							<c:if test="${urlMap['/company/meal/listDishesCategory.do']==null}">
								<li <c:if test="${cur_sub=='sel-meal-dishesCategory'}">class="active"</c:if>>
									<a href="/company/meal/listDishesCategory.do">菜肴分类</a>
								</li>
							</c:if>
							<c:if test="${urlMap['/company/meal/listDishes.do']==null}">
								<li <c:if test="${cur_sub=='sel-meal-dishes'}">class="active"</c:if>>
									<a href="/company/meal/listDishes.do">菜肴管理</a>
								</li>
							</c:if>
						</ul>
					</li>
					<c:if test="${urlMap['/company/meal/listMealTab.do']==null}">
						<li <c:if test="${cur_sub=='sel-meal-mealTab'}">class="active"</c:if>>
							<a href="/company/meal/listMealTab.do">桌台管理</a>
						</li>
						<li <c:if test="${cur_sub=='sel-waiter-waiter'}">class="active"</c:if>>
							<a href="/company/waiter/listWaiter.do">服务员管理</a>
						</li>
					</c:if>
					<c:if test="${urlMap['/company/meal/listMealOrder.do']==null}">
						<li <c:if test="${cur_sub=='sel-meal-order'}">class="active"</c:if>>
							<a href="/company/meal/listMealOrder.do">订单列表</a>
						</li>
					</c:if>
				</ul>
			</li>	
		</ul>
		<!-- END SIDEBAR MENU -->
	</div>
</div>
<!-- END SIDEBAR -->