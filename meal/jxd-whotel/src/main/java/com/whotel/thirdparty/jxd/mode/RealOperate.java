package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

import java.util.Date;
import java.util.List;


/** 
 * @ClassName: WaiterQuery 
 * @Description: 服务员查询实体封装
 * @author 李中辉 
 * @date 2017年5月6日 上午8:48:46  
 */
public class RealOperate extends AbstractInputParam{

	private String opType = "餐饮点菜";
	
	private String XType="Gemstar";


	private CyReservation cyReservation;

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

	public String getXType() {
		return XType;
	}

	public void setXType(String XType) {
		this.XType = XType;
	}

	public CyReservation getCyReservation() {
		return cyReservation;
	}

	public void setCyReservation(CyReservation cyReservation) {
		this.cyReservation = cyReservation;
	}
}
