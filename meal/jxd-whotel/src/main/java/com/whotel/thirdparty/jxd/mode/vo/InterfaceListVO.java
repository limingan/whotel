package com.whotel.thirdparty.jxd.mode.vo;

/**
 * 接口列表
 * @author 柯鹏程
 * 
 */
public class InterfaceListVO {
	
	private String interfaceName;//接口名
	
	private Integer useMemberInterface;//是否使用会员接口
	
	private String interfaceUrl;//接口地址
	
	private String mobileAuditUrl;//手机审核订单地址
	
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getUseMemberInterface() {
		return useMemberInterface;
	}

	public void setUseMemberInterface(Integer useMemberInterface) {
		this.useMemberInterface = useMemberInterface;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public String getMobileAuditUrl() {
		return mobileAuditUrl;
	}

	public void setMobileAuditUrl(String mobileAuditUrl) {
		this.mobileAuditUrl = mobileAuditUrl;
	}

	@Override
	public String toString() {
		return "InterfaceListVO [interfaceName=" + interfaceName + ",useMemberInterface=" + useMemberInterface + ",interfaceUrl=" + interfaceUrl + ",mobileAuditUrl=" + mobileAuditUrl + "]";
	}
}
