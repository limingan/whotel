package com.whotel.meal.entity;

import java.util.Date;
import java.util.List;

import com.whotel.meal.enums.MealOrderType;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.util.RepoUtil;
import com.whotel.hotel.entity.BaseOrder;
import com.whotel.meal.enums.MealOrderStatus;
import com.whotel.meal.enums.MealType;
/**
 * 餐饮订单
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class MealOrder extends BaseOrder {

	private static final long serialVersionUID = 1L;

	private String hotelCode;//酒店代码
	
	@Embedded
	private List<MealOrderItem> items;
	
	private String resStatus;//预订状态：R：预订(含修改) X:取消
	
	private String confirmationID;//餐饮系统订单号
	
	private String resortRegID;//分店确认号 分店的唯一标识
	
	private Date arrDate;//到达时间，包含日期和时间信息
	
	private String shuffleNo;//市别id
	
	private String shuffleName;//市别名称
	
	private Integer guestNum;//客人人数
	
	private String mbrCardNo;//客人会员卡号
	
	private Date createDate;//预订日期：下单的日期
	
	private Float totalAmount;//订单总价
	
	private MealOrderStatus status;//餐饮订单状态
	
	private String mealTabId;//餐台id
	
	private String restaurantId;//餐厅id
	
	private String arriveTime;//抵店时间
	
	private MealType mealType;//餐段
	
	private Float serviceFee;//服务费
	
	private Float dishesPrice;//菜价

	private MealOrderType mealOrderType;//外卖还是堂食

	private String addr;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public MealOrderType getMealOrderType() {
		return mealOrderType;
	}

	public void setMealOrderType(MealOrderType mealOrderType) {
		this.mealOrderType = mealOrderType;
	}

	public Float getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Float serviceFee) {
		this.serviceFee = serviceFee;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	public String getConfirmationID() {
		return confirmationID;
	}

	public void setConfirmationID(String confirmationID) {
		this.confirmationID = confirmationID;
	}

	public String getResortRegID() {
		return resortRegID;
	}

	public void setResortRegID(String resortRegID) {
		this.resortRegID = resortRegID;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public String getShuffleNo() {
		return shuffleNo;
	}

	public void setShuffleNo(String shuffleNo) {
		this.shuffleNo = shuffleNo;
	}

	public Integer getGuestNum() {
		return guestNum;
	}

	public void setGuestNum(Integer guestNum) {
		this.guestNum = guestNum;
	}

	public String getMbrCardNo() {
		return mbrCardNo;
	}

	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public List<MealOrderItem> getItems() {
		return items;
	}

	public void setItems(List<MealOrderItem> items) {
		this.items = items;
	}
	
	public MealOrderStatus getStatus() {
		return status;
	}

	public void setStatus(MealOrderStatus status) {
		this.status = status;
	}

	public String getMealTabId() {
		return mealTabId;
	}

	public void setMealTabId(String mealTabId) {
		this.mealTabId = mealTabId;
	}
	
	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Restaurant getRestaurant(){
		return RepoUtil.getRestaurantById(restaurantId);
	}
	
	public MealTab getMealTab(){
		return RepoUtil.getMealTabById(mealTabId);
	}
	
	public Shuffle getShuffle(){
		return RepoUtil.getShuffleById(shuffleNo);
	}

	public String getShuffleName() {
		return shuffleName;
	}

	public void setShuffleName(String shuffleName) {
		this.shuffleName = shuffleName;
	}

	public Float getDishesPrice() {
		return dishesPrice;
	}

	public void setDishesPrice(Float dishesPrice) {
		this.dishesPrice = dishesPrice;
	}
	
}
