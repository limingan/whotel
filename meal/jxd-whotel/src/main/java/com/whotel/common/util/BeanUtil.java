package com.whotel.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 * @author
 *
 */
public class BeanUtil extends PropertyUtils {

	private BeanUtil() {}
	/**
	 * override BeanUtils method copyProperties, support not copy null or blank
	 * property
	 * 
	 * @param dest
	 * @param src
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static void copyProperties(Object dest, Object src) {
		// 为两个参数时，skipNull 默认为false
		copyProperties(dest, src, false);
	}

	/**
	 * override BeanUtils method copyProperties, support not copy null or blank
	 * property
	 * 
	 * @param dest
	 * @param src
	 * @param skipNull 拷贝时是否忽略src对象为null的属性
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static void copyProperties(Object dest, Object src, boolean skipNull) {
		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}

		if (src == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		// 获取所有src中的属性，存入于数组中
		PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(src);

		for (int i = 0; i < origDescriptors.length; i++) {
			// 取出src中属性名
			String name = origDescriptors[i].getName();

			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}

			if (PropertyUtils.isReadable(src, name) && PropertyUtils.isWriteable(dest, name)) {
				Object value = null;
				try {
					// 取出属性的值
					value = PropertyUtils.getSimpleProperty(src, name);
				} catch (Exception e) {
					throw new RuntimeException();
				}

				if (skipNull) {
					if (value == null) {
						continue;
					}
				} else if (value == null) {
					value = null;
				}

				try {
					// copyProperties(dest, name, value);
					setProperty(dest, name, value);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}
	}
	
    public static void transMap2Bean(Map<String, Object> map, Object obj) { 
    	
    	try {
			BeanUtils.populate(obj, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       /* try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
        return;  
        */
    }  
  
    public static Map<String, Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
        return map;  
    }  
}
