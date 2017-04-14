package com.whotel.thirdparty.jxd.mode.scenicticket;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 景区票劵系统
 * @author 查询景区票劵类型
 */
public class ScenicTicketQuery extends AbstractInputParam {
	
	private String opType = "票券类型查询";
	
	private Map<String,String> tickettypeQuery;
	
	@Override
	public String getRoot() {
		return null;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getTickettypeQuery() {
		return tickettypeQuery;
	}

	public void setTickettypeQuery(Map<String, String> tickettypeQuery) {
		this.tickettypeQuery = tickettypeQuery;
	}
}
