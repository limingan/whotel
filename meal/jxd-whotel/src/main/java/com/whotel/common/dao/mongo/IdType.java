package com.whotel.common.dao.mongo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ID type: random or sequence, The performance of sequence type is about 30% of random type, because used a collection
 * to store each sequence, have to update the sequence before each new entity save.
 * 
 * 
 * @author KelvinZ
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface IdType {
	public static final String TYPE_RANDOM = "R";
	public static final String TYPE_SEQUENCE = "S";

	String type() default TYPE_RANDOM;
}
