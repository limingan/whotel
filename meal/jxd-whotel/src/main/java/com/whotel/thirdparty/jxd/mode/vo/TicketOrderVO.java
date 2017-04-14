package com.whotel.thirdparty.jxd.mode.vo;

import java.util.List;

/**
 * 捷信达订单对象
 * @author 冯勇
 * 
 */
public class TicketOrderVO {
	
	private String orderOperate; 		// Add:新增订单,Update:修改订单，Cancel:取消订单
	private String orderNo; 			// 订单号
	private String confirmationID; 		// 第三方订单ID
	private String resortId;			// 酒店编号
	private String profileId;			// 会员唯一标识
	private String mbrCardNo;     		// 会员卡号
	private String mbrCardTypeCode;		//会员卡类型
	private String guestType; 			// 散客，会员
	private String source; 				// mobileSite:手机网站;webSite:官网;weixin:微信
	private String weixinID;            // 微信ID
	private String payMethodType; 		// Alipay:支付宝;Kqpay:快钱;Payinhotel:到店支付;Tenpay:财付通(含微信支付)
	private String paidRefId; 			// 付款流水号
	private Float totalAmount; 			// 总金额
	private Float paidAmount; 			// 付款金额
	private String contactName; 		// 联系人姓名
	private String contactMobile; 		// 联系人手机
	private String contactTel; 			// 联系人电话
	private String contactEmail; 		// 联系人邮件
	private String consumerRevertMsg; 	// 取消原因
	private String orderType; 			// 订单类型 // 0:客房订单,1:餐饮订单,2:温泉门票订单,3:直通车订单,4:综合订单,5套餐预订
	
	private String confirmTypeName;     // 新订单，自动确认，酒店确认，已入住，酒店取消，客人取消
	private String createDate;
	private String hotelCheckStatus;
	private String hotelRevertMsg;
	private String hotelRevertName;
	private String hotelRevertDate;
	private List<OrderDetailVO> orderDetails; 	// 所有订单明细
	private String couponCodes;          //优惠券代码
	private Float couponPaidAmount;		 //优惠券抵扣金额
	private String saleName; 			 //业务员名称
	private Integer checkedcount;		 //验票数量
	private String vlink;				 //批次号
	private Float returnMoneyPaidAmount;//支付返现金额
	private Float mbrCardPaidAmount;//支付会员卡余额金额

	public String getCouponCodes() {
		return couponCodes;
	}

	public void setCouponCodes(String couponCodes) {
		this.couponCodes = couponCodes;
	}

	public Float getCouponPaidAmount() {
		return couponPaidAmount;
	}

	public void setCouponPaidAmount(Float couponPaidAmount) {
		this.couponPaidAmount = couponPaidAmount;
	}

	public String getOrderOperate() {
		return orderOperate;
	}

	public void setOrderOperate(String orderOperate) {
		this.orderOperate = orderOperate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getConfirmationID() {
		return confirmationID;
	}

	public void setConfirmationID(String confirmationID) {
		this.confirmationID = confirmationID;
	}

	public String getResortId() {
		return resortId;
	}

	public void setResortId(String resortId) {
		this.resortId = resortId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getMbrCardNo() {
		return mbrCardNo;
	}

	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}

	public String getGuestType() {
		return guestType;
	}

	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getWeixinID() {
		return weixinID;
	}

	public void setWeixinID(String weixinID) {
		this.weixinID = weixinID;
	}

	public String getPayMethodType() {
		return payMethodType;
	}

	public void setPayMethodType(String payMethodType) {
		this.payMethodType = payMethodType;
	}

	public String getPaidRefId() {
		return paidRefId;
	}

	public void setPaidRefId(String paidRefId) {
		this.paidRefId = paidRefId;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Float getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Float paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getConsumerRevertMsg() {
		return consumerRevertMsg;
	}

	public void setConsumerRevertMsg(String consumerRevertMsg) {
		this.consumerRevertMsg = consumerRevertMsg;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getConfirmTypeName() {
		return confirmTypeName;
	}

	public void setConfirmTypeName(String confirmTypeName) {
		this.confirmTypeName = confirmTypeName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getHotelCheckStatus() {
		return hotelCheckStatus;
	}

	public void setHotelCheckStatus(String hotelCheckStatus) {
		this.hotelCheckStatus = hotelCheckStatus;
	}

	public String getHotelRevertMsg() {
		return hotelRevertMsg;
	}

	public void setHotelRevertMsg(String hotelRevertMsg) {
		this.hotelRevertMsg = hotelRevertMsg;
	}

	public String getHotelRevertName() {
		return hotelRevertName;
	}

	public void setHotelRevertName(String hotelRevertName) {
		this.hotelRevertName = hotelRevertName;
	}

	public String getHotelRevertDate() {
		return hotelRevertDate;
	}

	public void setHotelRevertDate(String hotelRevertDate) {
		this.hotelRevertDate = hotelRevertDate;
	}

	public List<OrderDetailVO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailVO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getMbrCardTypeCode() {
		return mbrCardTypeCode;
	}

	public void setMbrCardTypeCode(String mbrCardTypeCode) {
		this.mbrCardTypeCode = mbrCardTypeCode;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getVlink() {
		return vlink;
	}

	public void setVlink(String vlink) {
		this.vlink = vlink;
	}

	public Integer getCheckedcount() {
		return checkedcount;
	}

	public void setCheckedcount(Integer checkedcount) {
		this.checkedcount = checkedcount;
	}

	public Float getReturnMoneyPaidAmount() {
		return returnMoneyPaidAmount;
	}

	public void setReturnMoneyPaidAmount(Float returnMoneyPaidAmount) {
		this.returnMoneyPaidAmount = returnMoneyPaidAmount;
	}

	public Float getMbrCardPaidAmount() {
		return mbrCardPaidAmount;
	}

	public void setMbrCardPaidAmount(Float mbrCardPaidAmount) {
		this.mbrCardPaidAmount = mbrCardPaidAmount;
	}

	public class OrderDetailVO {
		
		private Integer detailId;    	 // 明细记录编号
		
		private String code;			 // 房型代码
		
		private String salesPromotionId; // 优惠活动id
		
		private String arriveDate;		 // 预抵日期
		
		private String leaveDate;        // 预离日期
		
		private Integer roomQty;		 // 预订数量
		
		private Float totalAmount;      // 总金额
		
		private Float paidAmount;       // 已付金额
		
		private String payMethod;        // 支付方式
		
		private String guestName;        // 客人姓名
		
		private String roomSpecial;      // 其他要求
		
		private String arriveTime;       // 预计抵达时间
		
		private String guestRemark;      // 客人备注
		
		private List<OrderDetailPriceVO> orderDetailPrices;  // 订单明细的价格明细列表
		
		private List<OrderValueAddedServiceVO> orderValueAddedServices; // 订单明细的增值服务项目列

		private List<OrderValueAddedServiceVO> orderAirportPickupServices; // 订单明细的接机服务项目列
		
		private List<AttachProductVO> attachProducts;//附属产品
		
		public Integer getDetailId() {
			return detailId;
		}

		public void setDetailId(Integer detailId) {
			this.detailId = detailId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getSalesPromotionId() {
			return salesPromotionId;
		}

		public void setSalesPromotionId(String salesPromotionId) {
			this.salesPromotionId = salesPromotionId;
		}

		public String getArriveDate() {
			return arriveDate;
		}

		public void setArriveDate(String arriveDate) {
			this.arriveDate = arriveDate;
		}

		public String getLeaveDate() {
			return leaveDate;
		}

		public void setLeaveDate(String leaveDate) {
			this.leaveDate = leaveDate;
		}

		public Integer getRoomQty() {
			return roomQty;
		}

		public void setRoomQty(Integer roomQty) {
			this.roomQty = roomQty;
		}
		
		public Float getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Float totalAmount) {
			this.totalAmount = totalAmount;
		}

		public Float getPaidAmount() {
			return paidAmount;
		}

		public void setPaidAmount(Float paidAmount) {
			this.paidAmount = paidAmount;
		}

		public String getPayMethod() {
			return payMethod;
		}

		public void setPayMethod(String payMethod) {
			this.payMethod = payMethod;
		}

		public String getGuestName() {
			return guestName;
		}

		public void setGuestName(String guestName) {
			this.guestName = guestName;
		}

		public String getRoomSpecial() {
			return roomSpecial;
		}

		public void setRoomSpecial(String roomSpecial) {
			this.roomSpecial = roomSpecial;
		}

		public String getArriveTime() {
			return arriveTime;
		}

		public void setArriveTime(String arriveTime) {
			this.arriveTime = arriveTime;
		}

		public String getGuestRemark() {
			return guestRemark;
		}

		public void setGuestRemark(String guestRemark) {
			this.guestRemark = guestRemark;
		}

		public List<OrderDetailPriceVO> getOrderDetailPrices() {
			return orderDetailPrices;
		}

		public void setOrderDetailPrices(List<OrderDetailPriceVO> orderDetailPrices) {
			this.orderDetailPrices = orderDetailPrices;
		}

		public List<OrderValueAddedServiceVO> getOrderValueAddedServices() {
			return orderValueAddedServices;
		}

		public void setOrderValueAddedServices(
				List<OrderValueAddedServiceVO> orderValueAddedServices) {
			this.orderValueAddedServices = orderValueAddedServices;
		}

		public List<OrderValueAddedServiceVO> getOrderAirportPickupServices() {
			return orderAirportPickupServices;
		}

		public void setOrderAirportPickupServices(
				List<OrderValueAddedServiceVO> orderAirportPickupServices) {
			this.orderAirportPickupServices = orderAirportPickupServices;
		}

		public List<AttachProductVO> getAttachProducts() {
			return attachProducts;
		}

		public void setAttachProducts(List<AttachProductVO> attachProducts) {
			this.attachProducts = attachProducts;
		}
	}
	
	
	public class OrderDetailPriceVO {
		
		private Integer detailId;   // 对应的明细id
		
		private Float price;      // 价格
		
		private String priceDate;  // 价格日期
		
		private String comboCode;//套餐code

		public Integer getDetailId() {
			return detailId;
		}

		public void setDetailId(Integer detailId) {
			this.detailId = detailId;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		public String getPriceDate() {
			return priceDate;
		}

		public void setPriceDate(String priceDate) {
			this.priceDate = priceDate;
		}

		public String getComboCode() {
			return comboCode;
		}

		public void setComboCode(String comboCode) {
			this.comboCode = comboCode;
		}
	}
	
	public class OrderValueAddedServiceVO {
		
		private Integer detailId;       // 对应的明细id
		
		private String valueAddedId;   // 增值服务项目编号
		
		private String extraValue;     // 额外值
		
		private Integer qty;            // 增值服务数量
		
		private Float price;          // 增值服务的价格
		
		private Float amount;         // 增值服务的金额

		public Integer getDetailId() {
			return detailId;
		}

		public void setDetailId(Integer detailId) {
			this.detailId = detailId;
		}

		public String getValueAddedId() {
			return valueAddedId;
		}

		public void setValueAddedId(String valueAddedId) {
			this.valueAddedId = valueAddedId;
		}

		public Integer getQty() {
			return qty;
		}

		public void setQty(Integer qty) {
			this.qty = qty;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		public Float getAmount() {
			return amount;
		}

		public void setAmount(Float amount) {
			this.amount = amount;
		}

		public String getExtraValue() {
			return extraValue;
		}

		public void setExtraValue(String extraValue) {
			this.extraValue = extraValue;
		}
	}
	
	public class AttachProductVO{
		
		private String code;//房型代码
		private Integer roomQty;// 数量
		private Float totalAmount;//金额
		private String serviceType;//类型
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Integer getRoomQty() {
			return roomQty;
		}
		public void setRoomQty(Integer roomQty) {
			this.roomQty = roomQty;
		}
		public Float getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Float totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getServiceType() {
			return serviceType;
		}
		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}
	}
}
