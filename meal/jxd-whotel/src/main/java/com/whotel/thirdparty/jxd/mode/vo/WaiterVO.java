package com.whotel.thirdparty.jxd.mode.vo;

/** 
 * @ClassName: WaiterVO 
 * @Description: 服务员接口返回数据封装VO
 * @author 李中辉 
 * @date 2017年5月6日 上午8:47:46  
 */
public class WaiterVO {
	private String userNo;
	
	private String userName;
	
	private String pwd;
	
	private String iCCard;
	
    private String status;//0: 可用， 1：停用

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getiCCard() {
		return iCCard;
	}

	public void setiCCard(String iCCard) {
		this.iCCard = iCCard;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
