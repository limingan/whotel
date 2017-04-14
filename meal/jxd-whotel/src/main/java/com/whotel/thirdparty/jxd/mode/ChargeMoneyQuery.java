package com.whotel.thirdparty.jxd.mode;

import java.util.HashMap;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 充值档次查询
 * @author 冯勇
 *
 */
public class ChargeMoneyQuery extends AbstractInputParam {

	private String opType = "会员增值政策查询";
	
	private String resortID;
	private String MbrCardType;
	private String OutLetCode;
	private String AmoutList;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getResortID() {
		return resortID;
	}
public String getMbrCardType()
{
	return MbrCardType;
	}
public String getOutLetCode()
{
return OutLetCode;	
}
public String getAmoutList()
{
return AmoutList;	
}
public void setMbrCardType(String mbrCardType)
{
this.MbrCardType=mbrCardType;	
}
public void setOutLetCode(String outLetCode)
{
this.OutLetCode=outLetCode;	
}
public void setAmoutList(String amoutList)
{
this.AmoutList=amoutList;	
}

	public void setResortID(String resortID) {
		this.resortID = resortID;
	}

	@Override
	public String getRoot() {
		return null;
	}

}
