package com.whotel.common.entity;

/**
 * 假删除接口
 * <p>
 * 实现此接口的实体，当isUnDeleted()返回true时，删除操作为非物理删除，只是setDeleted(true)。
 * 
 * @author
 * 
 */
public interface UnDeletable {
	boolean isDeleted();

	void setDeleted(boolean deleted);
}
