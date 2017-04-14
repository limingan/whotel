package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 会员卡绑定
 * @author 柯鹏程
 *
 */
public class MbrCardBinding extends AbstractInputParam {
	
	private String opType = "会员卡绑定附属卡";
	
	private Map<String,String> mbrCardLink;
	
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	@Override
	public String getRoot() {
		return null;
	}
	public Map<String,String> getMbrCardLink() {
		return mbrCardLink;
	}
	public void setMbrCardLink(Map<String,String> mbrCardLink) {
		this.mbrCardLink = mbrCardLink;
	}
}
