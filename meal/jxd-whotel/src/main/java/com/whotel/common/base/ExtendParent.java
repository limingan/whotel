package com.whotel.common.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
 * 如果需要通过反射读取父类的字段，需要在父类注解extendParentField＝TRUE ，否则不读取父类的字段
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtendParent {
	public boolean extendParentField();
}
