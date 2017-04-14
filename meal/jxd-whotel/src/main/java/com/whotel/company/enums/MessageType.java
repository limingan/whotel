package com.whotel.company.enums;

import com.whotel.common.base.Labeled;

/**
 * 消息类型
 * @author 柯鹏程
 */
public enum MessageType implements Labeled {

	ROOM_BOOK("新订单提醒"),EXCHANGE_GIFT("订单提醒"),ROOM_BOOK_FAIL("酒店预订失败通知"),ROOM_BOOK_SUCCESS("酒店预订成功通知"),	
	ROOM_BOOK_CANCEL("房间订单取消通知"),CREDIT_EXCHANGE_FAIL("积分兑换拒绝提醒"),CREDIT_EXCHANGE_SUCCESS("积分兑换礼品通知"),
	FOCUS_GIFTS("礼品领取成功通知"),MEMBER_CONSUMER("会员消费通知"),OPERATION_FAILED("操作失败通知"),TICKET_BOOK_SUCCESS("温泉票预订成功通知"),
	TICKET_BOOK_FAIL("预订失败通知"),TICKET_BOOK_CANCEL("订单取消通知"),TICKET_BOOK("新订单提醒"),CUSTOMERSERVICE("用户咨询提醒"),GET_QIANDAO("签到成功通知"),
	SHOP_BOOK_CANCEL("订单取消通知"),SHOP_BOOK("新订单提醒"),SHOP_DELIVERED("订单发货通知"),TO_REMIND("来访通知"),GET_COUPON("礼品领取成功通知"),
	MEAL_BOOK("餐厅预订通知"),MEAL_BOOK_FAIL("餐厅预订通知"),MEAL_BOOK_SUCCESS("餐厅预订通知"),MEAL_BOOK_CANCEL("餐厅预订通知"),CHECK_OUT("办理离店提醒"),
	CONSUME_CARD("新订单提醒"),USE_REMIND("微订单提醒"),PAY_SUCCESS("支付成功通知"),MEAL_ORDER_CANCEL("订单取消通知"),COMBO_BOOK_SUCCESS("套餐预定成功通知"),
	MEMBER_REGISTERED("会员注册成功通知"),COMMENT_MESSAGE("订单评价提醒"),MALL_ORDER_VERIFICATION("核销成功提醒");
	//,MEMBER_REGISTERED_FAIL("用户注册失败通知")
	
	private String label;
	
	private String code;
	
	private String name;
	
	private Boolean checked;
	
	private MessageType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getCode() {
		if(this==ROOM_BOOK){//提醒商户，有用户下了新的酒店订单
			code = "OPENTM400045127";
		}else if(this==TICKET_BOOK){//提醒商户，有用户下了新的门票订单
			code = "OPENTM400045127";
		}else if(this==EXCHANGE_GIFT){//提醒商户，有了新的积分兑换订单，需要在e运通审核
			code = "OPENTM207102371";
		}else if(this==ROOM_BOOK_FAIL){
			code = "OPENTM201251549";
		}else if(this==ROOM_BOOK_SUCCESS){
			code = "TM00436";
		}else if(this==ROOM_BOOK_CANCEL){
			code = "OPENTM207867463";
		}else if(this==CREDIT_EXCHANGE_FAIL){
			code = "OPENTM207418556";
		}else if(this==CREDIT_EXCHANGE_SUCCESS){
			code = "OPENTM207522138";
		}else if(this==FOCUS_GIFTS){
			code = "OPENTM200772305";
		}else if(this==MEMBER_CONSUMER){
			code = "TM00010";
		}else if(this==OPERATION_FAILED){
			code = "OPENTM207717375";
		}else if(this==TICKET_BOOK_SUCCESS){
			code="OPENTM202479067";
		}else if(this==TICKET_BOOK_FAIL){
			code="TM00460";
		}else if(this==TICKET_BOOK_CANCEL){
			code="OPENTM201449108";
		}else if(this==CUSTOMERSERVICE){
			code="OPENTM202119578";
		}else if(this==SHOP_BOOK_CANCEL){
			code="OPENTM201449108";
		}else if(this==SHOP_BOOK){
			code = "OPENTM400045127";
		}else if(this==SHOP_DELIVERED){
			code = "OPENTM202243318";
		}else if(this==TO_REMIND){
			code="OPENTM401692301";
		}else if(this==GET_COUPON){
			code = "OPENTM200772305";
		}else if(this==MEAL_BOOK){
			code="OPENTM207568191";
		}else if(this==MEAL_BOOK_FAIL){
			code="OPENTM207568191";
		}else if(this==MessageType.MEAL_BOOK_CANCEL){
			code="OPENTM207568191";
		}else if(this==MessageType.MEAL_BOOK_SUCCESS){
			code="OPENTM207568191";
		}else if(this==CHECK_OUT){
			code="TM00381";
		}else if(this==CONSUME_CARD){
			code = "OPENTM400045127";
		}else if(this==USE_REMIND){
			code = "OPENTM207029553";
		}else if(this==PAY_SUCCESS){
			code = "OPENTM400231951";
		}else if(this==MEAL_ORDER_CANCEL){
			code="OPENTM201449108";
		}else if(this==COMBO_BOOK_SUCCESS){
			code="OPENTM207293096";
		}else if(this==MEMBER_REGISTERED){
			code="OPENTM201796917";
		}else if(this==COMMENT_MESSAGE){
			code="OPENTM205102209";
		}else if(this==MALL_ORDER_VERIFICATION) {
			code="OPENTM409583769";
		}else if(this==GET_QIANDAO) {
			code="OPENTM408372491";
		}
		
//		else if(this==MEMBER_REGISTERED_FAIL){
//			code="OPENTM201449109";
//		}
		return code;
	}
	
	public String getName(){
		if(this==ROOM_BOOK){//提醒商户，有用户下了新的订单
			name = "客房预订提醒（商户）";
		}else if(this==TICKET_BOOK){//提醒商户，有用户下了新的门票订单
			name = "门票预订提醒（商户）";
		}else if(this==EXCHANGE_GIFT){//提醒商户，有了新的积分兑换订单，需要在e运通审核
			name = "积分兑换提醒（商户）";
		}else if(this==ROOM_BOOK_FAIL){
			name = "酒店预订失败通知";
		}else if(this==ROOM_BOOK_SUCCESS){
			name = "酒店预订成功通知";
		}else if(this==ROOM_BOOK_CANCEL){
			name = "房间订单取消通知";
		}else if(this==CREDIT_EXCHANGE_FAIL){
			name = "积分兑换失败提醒";
		}else if(this==CREDIT_EXCHANGE_SUCCESS){
			name = "积分兑换礼品成功通知";
		}else if(this==FOCUS_GIFTS){
			name = "关注礼品领取成功通知";
		}else if(this==MEMBER_CONSUMER){
			name = "会员消费";
		}else if(this==OPERATION_FAILED){
			name = "操作失败";
		}else if(this==TICKET_BOOK_SUCCESS){
			name="门票预订成功通知";
		}else if(this==TICKET_BOOK_FAIL){
			name="门票预订失败通知";
		}else if(this==TICKET_BOOK_CANCEL){
			name="门票预订取消通知";
		}else if(this==CUSTOMERSERVICE){
			name="微信客服（商户）";
		}else if(this==SHOP_BOOK_CANCEL){
			name="商城订单取消通知";
		}else if(this==SHOP_BOOK){
			name = "商城订单提醒";//（商户，用户）
		}else if(this==SHOP_DELIVERED){
			name = "订单已发货提醒（用户）";
		}else if(this==TO_REMIND){
			name = "到店提醒";
		}else if(this==GET_COUPON){
			name = "优惠劵领取成功通知";
		}else if(this==MEAL_BOOK){
			name="餐饮预订提醒（商户）";
		}else if(this==MEAL_BOOK_FAIL){
			name="餐饮预订失败（用户）";
		}else if(this==MessageType.MEAL_BOOK_CANCEL){
			name="餐饮预订取消";
		}else if(this==MessageType.MEAL_BOOK_SUCCESS){
			name="餐饮预订成功（用户）";
		}else if(this==CHECK_OUT){
			name="离店赠送优惠劵提醒";
		}else if(this==CONSUME_CARD){
			name="卡券兑换提醒（商户）";
		}else if(this==USE_REMIND){
			name="预订订单使用提醒（用户）";
		}else if(this==PAY_SUCCESS){
			name="用户支付成功通知（商户）";
		}else if(this==MEAL_ORDER_CANCEL){
			name="餐饮订单取消通知";
		}else if(this==COMBO_BOOK_SUCCESS){
			name="套餐预定成功通知（用户）";
		}else if(this==MEMBER_REGISTERED){
			name="会员注册成功通知（用户）";
		}else if(this==COMMENT_MESSAGE){
			name="点评消息提醒（商户）";
		}else if(this==MALL_ORDER_VERIFICATION) {
			name="商城核销成功提醒";
		}else if(this==GET_QIANDAO) {
			name="签到提醒";
		}
//		else if(this==MEMBER_REGISTERED_FAIL){
//			name="会员注册失败提醒(用户注册)";
//		}
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
