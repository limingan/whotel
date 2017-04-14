package com.whotel.thirdparty.jxd.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whotel.common.util.DateUtil;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.common.util.ReflectionUtil;

/**
 * 构造请求xml数据和解析响应xml数据
 * 
 * @author 冯勇
 * 
 */
public class JxdXmlUtils {
	private final static Logger log = LoggerFactory.getLogger(JxdXmlUtils.class);

	@SuppressWarnings("unchecked")
	public static String toXml(final XmlBean bean) {
		Dom4jHelper dom4jHelper = new Dom4jHelper();
		if (AbstractInputParam.class.isAssignableFrom(bean.getClass())) {
			dom4jHelper.createXML("RealOperate", "gbk");
		}
		List<Field> fields = ReflectionUtil.findAllField(bean.getClass());
		if (fields != null) {
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = StringUtils.capitalize(field.getName());
				Class<?> fieldType = field.getType();
				Object fieldValue = null;
				try {
					fieldValue = field.get(bean);
				} catch (Exception ex) {
					log.error("Error on reading field: " + fieldName, ex);
				}
				
				if (log.isDebugEnabled()) {
				 log.debug("Field: name=" + fieldName + ", type=" + fieldType.getName() + ", value=" + fieldValue);
				}
				if (ReflectionUtil.isBaseType(field) || fieldType == String.class) { // 基础类型处理
					String value = (fieldValue != null) ? fieldValue.toString() : "";
					dom4jHelper.addNodeFromRoot(fieldName, value);
				} else if (fieldType == Date.class) {
					String dateStr = (fieldValue != null) ? DateUtil.format((Date) fieldValue, "yyyy-MM-dd") : "";
					dom4jHelper.addNodeFromRoot(fieldName, dateStr);
				} else if (Map.class.isAssignableFrom(fieldType)) {
					Element ele2 = dom4jHelper.addNodeFromRoot(fieldName, "");

					Map<String, Object> map = (Map<String, Object>) fieldValue;
					Set<Entry<String, Object>> entries = map.entrySet();
					for (Entry<String, Object> entry : entries) {
						String innerFieldName = entry.getKey();
						Object innerFieldValue = entry.getValue();
						if (innerFieldValue != null) {
							Element child = ele2.addElement(innerFieldName);
							//child.setText(innerFieldValue.toString());
							//child.
							if(innerFieldValue instanceof List) {
								List<XmlBean> xbs = (List<XmlBean>) innerFieldValue;
								for(XmlBean xb:xbs) {
									child.add(toXml(xb.getRoot(), xb));
								}
							} else {
								child.addText(innerFieldValue.toString());
							}
						}
					}
				} else if (XmlBean.class.isAssignableFrom(fieldType)) {
					Element ele2 = dom4jHelper.addNodeFromRoot(fieldName, "");
					List<Field> fields2 = ReflectionUtil.findAllField(fieldType);
					for (Field field2 : fields2) {
						field2.setAccessible(true);
						String innerFieldName = StringUtils.capitalize(field2.getName());
						Object innerFieldValue = null;
						try {
							innerFieldValue = field2.get(fieldValue);
						} catch (Exception ex) {
							log.error("Error", ex);
						}
						if (innerFieldValue != null) {
							Element child = ele2.addElement(innerFieldName);
							child.setText(innerFieldValue.toString());
						}
					}
				}
			}
		}
		return dom4jHelper.getXML().replaceAll("\n", "");
	}
	
	@SuppressWarnings("unchecked")
	public static Element toXml(String root, final Object bean) {
		Dom4jHelper dom4jHelper = new Dom4jHelper();
		Element element = dom4jHelper.createXML(root);
		List<Field> fields = ReflectionUtil.findAllField(bean.getClass());
		if (fields != null) {
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = StringUtils.capitalize(field.getName());
				Class<?> fieldType = field.getType();
				Object fieldValue = null;
				try {
					fieldValue = field.get(bean);
				} catch (Exception ex) {
					log.error("Error on reading field: " + fieldName, ex);
				}
				 if (log.isDebugEnabled()) {
				 log.debug("Field: name=" + fieldName + ", type=" + fieldType.getName() + ", value=" + fieldValue);
				 }
				if (ReflectionUtil.isBaseType(field) || fieldType == String.class) { // 基础类型处理
					String value = (fieldValue != null) ? fieldValue.toString() : "";
					dom4jHelper.addNodeFromRoot(fieldName, value);
				} else if (fieldType == Date.class) {
					String dateStr = (fieldValue != null) ? DateUtil.format((Date) fieldValue, "yyyy-MM-dd HH:mm") : "";
					dom4jHelper.addNodeFromRoot(fieldName, dateStr);
				} else if (Map.class.isAssignableFrom(fieldType)) {
					Element ele2 = dom4jHelper.addNodeFromRoot(fieldName, "");

					Map<String, Object> map = (Map<String, Object>) fieldValue;
					Set<Entry<String, Object>> entries = map.entrySet();
					for (Entry<String, Object> entry : entries) {
						String innerFieldName = entry.getKey();
						Object innerFieldValue = entry.getValue();
						if (innerFieldValue != null) {
							Element child = ele2.addElement(innerFieldName);
							//child.setText(innerFieldValue.toString());
							child.addText(innerFieldValue.toString());
						}
					}
				} else if (List.class.isAssignableFrom(fieldType)) {
					Element ele2 = dom4jHelper.addNodeFromRoot(fieldName, "");

					if (fieldValue != null) {
						List<XmlBean> xbs = (List<XmlBean>) fieldValue;
						for(XmlBean xb:xbs) {
							ele2.add(toXml(xb.getRoot(), xb));
						}
					}
				} else if (XmlBean.class.isAssignableFrom(fieldType)) {
					Element ele2 = dom4jHelper.addNodeFromRoot(fieldName, "");
					List<Field> fields2 = ReflectionUtil.findAllField(fieldType);
					for (Field field2 : fields2) {
						field2.setAccessible(true);
						String innerFieldName = StringUtils.capitalize(field2.getName());
						Object innerFieldValue = null;
						try {
							innerFieldValue = field2.get(fieldValue);
						} catch (Exception ex) {
							log.error("Error", ex);
						}
						if (innerFieldValue != null) {
							Element child = ele2.addElement(innerFieldName);
							child.setText(innerFieldValue.toString());
						}
					}
				}
			}
		}
		return element;
	}
}
