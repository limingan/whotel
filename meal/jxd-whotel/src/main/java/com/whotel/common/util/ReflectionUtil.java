package com.whotel.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.whotel.common.base.ExtendParent;


/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 * 
 * @author
 */
public class ReflectionUtil {
	//Field Cache 这样根据fieldName去某对象找Field时会更快 KEY FOR getClass().getName() + "_" + fieldName
	private static final Map<String, Field> FieldCache = Collections.synchronizedMap(new WeakHashMap<String, Field>());
	//key for getClass().getName() ,Object is a List of Fields
	@SuppressWarnings("rawtypes")
	private static final Map<Class, Object> FieldsCache = Collections.synchronizedMap(new WeakHashMap<Class, Object>());
	public static final String CGLIB_CLASS_SEPARATOR = "$$";
	private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 调用Getter方法.
	 * 
	 * @param object
	 * @param method
	 * @return
	 */
	public static Object invokeGetterMethod(Object object, Method method) {
		try {
			return method.invoke(object, new Object[0]);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param object
	 * @param method
	 * @return
	 */
	public static void invokeSetterMethod(Object object, Method method, Object value) {
		try {
			method.invoke(object, new Object[] { value });
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param propertyType
	 *            用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 转换非RuntimeException
	 * 
	 * @param obj
	 *            Object
	 * @param field
	 *            Field
	 * @return Object
	 * @see java.lang.Field#get(Object)
	 */
	public static Object getFieldValue(Object obj, Field field) {
		Assert.notNull(obj);
		Assert.notNull(field);
		try {
			return field.get(obj);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 对于被cglib AOP过的对象, 取得真实的Class类型.
	 */
	public static Class<?> getUserClass(Class<?> clazz) {
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
	 * args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Assert.notNull(obj, "object不能为空");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {// NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * 获取某个CLASS里所有的Field
	 * @author likanglong
	 */
	@SuppressWarnings("rawtypes")
	public static List<Field> findAllField(Class c) {
		return findAllField(c,null);
	}

	/**
	 * 获取某个CLASS里所有的Field
	 * @param filterFields 要过滤的Field名称
	 * @author likanglong
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Field> findAllField(Class c,List<String> filterFields) {
		List<Field>fs =(List<Field>)FieldsCache.get(c);
		if(fs!=null&&fs.size()!=0)
			return fs;
		List<Field> list = new ArrayList<Field>(12);
		try {
			
			Class parent=c.getSuperclass();
			boolean hasAnnotation = parent.isAnnotationPresent(
					ExtendParent.class);
			while (hasAnnotation) {
				ExtendParent annotation = (ExtendParent) parent.getAnnotation(ExtendParent.class);
				if (annotation.extendParentField()) {
					Field[] superfields2 = parent
							.getDeclaredFields();
					for (Field f : superfields2) {
						if (filterFields==null||!filterFields.contains(f.getName())) {
							list.add(f);
						}
					}
				}
				parent=parent.getSuperclass();
				hasAnnotation= parent.isAnnotationPresent(
						ExtendParent.class);
			}
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				if (filterFields==null||!filterFields.contains(f.getName())) {
					list.add(f);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		FieldsCache.put(c, list);
		return list;
	}

	/**
	 * 设置Field的值
	 * @author likanglong
	 * 注意field的访问问题
	 * @throws Exception 
	 */
	public static void setFieldVlaue(Object obj, Field field, String value)
			throws Exception {
		field.setAccessible(true);
		if (field.getType() == boolean.class
				|| field.getType() == Boolean.class) {
			if (value.getClass() == String.class) {
				String str = value;
				if (str.equalsIgnoreCase("true")) {
					field.set(obj, true);
				} else if (str.equalsIgnoreCase("false")) {
					field.set(obj, false);
				}
			} else {
				field.set(obj, Integer.parseInt(value) == 1);
			}
		} else if (field.getType() == int.class
				|| field.getType() == Integer.class) {
			if (field.getType() == int.class && value == null) {
				field.set(obj, 0);
			} else {
				field.set(obj, Integer.parseInt(value));
			}
		} else if (field.getType() == long.class
				|| field.getType() == Long.class) {
			if (field.getType() == long.class && value == null) {
				field.set(obj, 0);
			} else {
				field.set(obj, Long.parseLong(value));
			}

		} else if (field.getType() == double.class
				|| field.getType() == Double.class) {
			if (field.getType() == double.class && value == null) {
				field.set(obj, 0d);
			} else {
				field.set(obj, Double.parseDouble(value));
			}
		} else if (field.getType() == float.class
				|| field.getType() == Float.class) {
			if (field.getType() == float.class && value == null) {
				field.set(obj, 0f);
			} else {
				field.set(obj, Float.parseFloat(value));
			}
		} else if (field.getType() == short.class
				|| field.getType() == Short.class) {
			if (field.getType() == short.class && value == null) {
				field.set(obj, 0);
			} else {
				field.set(obj, Short.parseShort(value));
			}
		} else if (field.getType() == byte.class
				|| field.getType() == Byte.class) {
			if (field.getType() == byte.class && value == null) {
				field.set(obj, 0);
			} else {
				field.set(obj, Byte.parseByte(value));
			}
		} else if (field.getType() == String.class) {
			field.set(obj, value);
		} else {
			throw new Exception(field.getType().getName()
					+ ":type not support for:" + value);
		}
	}

	/**
	 * 产生FieldCache 的KEY
	 * @author likanglong
	 * 注意field的访问问题
	 */
	private static String getCacheFieldkey(Object obj, String fieldName) {
		return obj.getClass().getName() + "_" + fieldName;
	}

	/**
	 * 设置Field的值
	 * @author likanglong
	 * 注意field的访问问题
	 */
	public static void setFieldVlaue(Object obj, String fieldName, Object value) {
		Field field = null;
		try {
			String key = getCacheFieldkey(obj, fieldName);
			field = ReflectionUtil.FieldCache.get(key);
			if (field == null) {
				Class<?> clazz = obj.getClass();
				for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
					try {
						field = clazz.getDeclaredField(fieldName);
						field.setAccessible(true);
						ReflectionUtil.FieldCache.put(key, field);
						break;
					} catch (Exception e) {
						// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
						// 如果这里的异常打印或者往外抛，则就不会执行clazz =
						// clazz.getSuperclass(),最后就不会进入到父类中了
					}
				}
			} 
			if (field == null)
				return;
			if (value == null) {
				field.set(obj, null);
				return;
			}
			if ((field.getType() == boolean.class || field.getType() == Boolean.class)
					&& (value.getClass() != boolean.class && value.getClass() != Boolean.class)) {
				if (value.getClass() == String.class) {
					String str = (String) value;
					if (str.equalsIgnoreCase("true")) {
						field.set(obj, true);
					} else if (str.equalsIgnoreCase("false")) {
						field.set(obj, false);
					}
				} else {
					Number n = (Number) value;
					field.set(obj, n.intValue() == 1);
				}
			} else {
				field.set(obj, value);
			}
		} catch (Exception e) {
			if (field != null) {
				System.out.println(field.getName() + ":" + value);
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据字段名称获取Field
	 * @param filterFields 要过滤的Field名称
	 * @author likanglong
	 */
	@SuppressWarnings("rawtypes")
	public static Field getField(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException {
		String key = getCacheFieldkey(obj, fieldName);
		Field field = ReflectionUtil.FieldCache.get(key);
		if (field != null)
			return field;
		Class c = obj.getClass();
		field = c.getDeclaredField(fieldName);
		field.setAccessible(true);
		ReflectionUtil.FieldCache.put(key, field);
		return field;
	}

	/**
	 * 根据字段名称获取对象的值
	 * @param filterFields 要过滤的Field名称
	 * @author likanglong
	 */
	public static Object getFieldVlaue(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getField(obj, fieldName);
		if (field != null) {
			return field.get(obj);
		} else {
			return null;
		}
	}

	/**
	 * 获取所有值非空的Field
	 * @param filterFields 要过滤的Field名称
	 * @author likanglong
	 */
	@SuppressWarnings("rawtypes")
	public static List<Field> findNotNullField(Object obj) {
		Class c = obj.getClass();
		List<Field> list = new ArrayList<Field>(10);
		try {
			List<Field> fields = findAllField(c);
			for (Field f : fields) {
				f.setAccessible(true);
				if (f.get(obj) != null) {// 这个字段的值不为null
					list.add(f);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	/**
	 * 获取是否是基本类型
	 * @param filterFields 要过滤的Field名称
	 * @author likanglong
	 */
	public static boolean isBaseType(Field f) {
		if (f.getType().isPrimitive())
			return true;
		if (f.getType() == Integer.class || f.getType() == Long.class
				|| f.getType() == Short.class || f.getType() == Boolean.class
				|| f.getType() == Character.class || f.getType() == Byte.class
				|| f.getType() == Double.class || f.getType() == Float.class)
			return true;
		return false;
	}

}
