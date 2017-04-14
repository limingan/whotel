package com.weixin.core.bean;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;

import com.weixin.core.common.WeixinBean;
import com.whotel.common.base.ExtendParent;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.common.util.ReflectionUtil;
import com.whotel.common.util.TextUtil;

/**
 * 
 */
@SuppressWarnings("serial")
@ExtendParent(extendParentField = true)
public class WeixinMsg implements WeixinBean {
	protected long msgId;
	protected String toUserName;
	protected String fromUserName;
	protected long createTime;
	protected String msgType;

	public WeixinMsg() {
		this.createTime = new Date().getTime() / 1000;
	}

	/**
	 * 生成xml格式的bean
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toXml() throws Exception {
		Dom4jHelper dealor = new Dom4jHelper();
		dealor.createXML("xml");
		List<Field> fs = ReflectionUtil.findAllField(this.getClass());
		if (fs != null) {
			// 遍历bean的字段
			for (Field f : fs) {
				String fName = f.getName();
				if (fName.equals("msgId"))
					continue;
				f.setAccessible(true);
				Class<?> fType = f.getType();
				Object fVal = f.get(this);

				if (ReflectionUtil.isBaseType(f) || fType == String.class) {
					// 当字段为基本类型或者字符串，直接添加节点，微信里节点首字符大写
					if (fVal != null) {
						dealor.addNodeFromRoot(
								TextUtil.capitalize(fName),
								fVal.toString());
					} else {
						dealor.addNodeFromRoot(
								TextUtil.capitalize(fName), "");
					}
				} else if (WeixinBean.class.isAssignableFrom(fType)) {
					// 当节点为类型
					Element ele = dealor.addNodeFromRoot(
							TextUtil.capitalize(fName), "");
					List<Field> beanFields = ReflectionUtil.findAllField(fType);
					for (Field f2 : beanFields) {
						f2.setAccessible(true);
						Element child = ele.addElement(TextUtil.capitalize(f2.getName()));
						if (fVal!=null && f2.get(fVal) != null) {
							child.setText(f2.get(fVal).toString());
						}
					}
				} else if (List.class.isAssignableFrom(fType)
						|| Set.class.isAssignableFrom(fType)) {
					// 当节点为集合
					Element ele = dealor.addNodeFromRoot(
							TextUtil.capitalize(fName), "");
					Collection<?> list = (Collection<?>) fVal;
					if (list == null || list.isEmpty())
						continue;
					Class<?> fobj = list.iterator().next().getClass();
					List<Field> itemFileds = ReflectionUtil.findAllField(fobj);
					String fobjName[] = fobj.toString().split("\\.");
					String itemName = TextUtil.uncapitalize(fobjName[fobjName.length - 1]);
					for (Object obj : list) {
						Element child = ele.addElement(itemName);
						for (Field f2 : itemFileds) {
							f2.setAccessible(true);
							Element itemEle = child.addElement(TextUtil.capitalize(f2.getName()));
							if (f2.get(obj) != null) {
								itemEle.setText(f2.get(obj).toString());
							}
						}
					}

				}

			}
		}
		return dealor.getXML();
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Override
	public String toString() {
		return "WeixinMsg [msgId=" + msgId + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (createTime ^ (createTime >>> 32));
		result = prime * result
				+ ((fromUserName == null) ? 0 : fromUserName.hashCode());
		result = prime * result + (int) (msgId ^ (msgId >>> 32));
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		result = prime * result
				+ ((toUserName == null) ? 0 : toUserName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeixinMsg other = (WeixinMsg) obj;
		if (createTime != other.createTime)
			return false;
		if (fromUserName == null) {
			if (other.fromUserName != null)
				return false;
		} else if (!fromUserName.equals(other.fromUserName))
			return false;
		if (msgId != other.msgId)
			return false;
		if (msgType == null) {
			if (other.msgType != null)
				return false;
		} else if (!msgType.equals(other.msgType))
			return false;
		if (toUserName == null) {
			if (other.toUserName != null)
				return false;
		} else if (!toUserName.equals(other.toUserName))
			return false;
		return true;
	}

}
