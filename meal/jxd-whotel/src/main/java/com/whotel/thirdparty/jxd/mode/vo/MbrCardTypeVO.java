package com.whotel.thirdparty.jxd.mode.vo;

/**
 * 会员卡类型
 * @author kpc
 *
 */
public class MbrCardTypeVO  {
	
	private String code;      //会员卡类型编码
	private String name;      //会员卡类型名称
	private Float salePrice; //售卖金额
	private Float chargeAmt; //卡上余额
	private String remark;    //备注
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public Float getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(Float chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return "MbrCardTypeVO [code=" + code + ", name=" + name + ", salePrice=" + salePrice + ", chargeAmt="
				+ chargeAmt + ", remark=" + remark + "]";
	}
}
