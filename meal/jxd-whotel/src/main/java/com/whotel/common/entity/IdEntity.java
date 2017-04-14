package com.whotel.common.entity;

import java.io.Serializable;

/**
 * 统一定义id的entity接口.
 * 
 * @author
 * 
 * @param <T>
 *            标识属性的类型
 */
public interface IdEntity<T extends Serializable> extends Serializable {
	T getId();

	void setId(T id);
}
