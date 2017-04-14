package com.whotel.common.entity;

import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Polymorphic;

/**
 * 不可删实体
 * 
 * @author KelvinZ
 * 
 */
@SuppressWarnings("serial")
@Polymorphic
public abstract class UnDeletedEntity extends BaseEntity implements UnDeletable {
	@Indexed
	private Boolean deleted;

	/**
	 * 为节省MongoDB的空间,可以用空或者false表示未删除;true为已删除
	 */
	@Override
	public boolean isDeleted() {
		return (deleted == null) ? false : deleted;
	}

	/**
	 * 为节省MongoDB的空间,为false时保存null;true保存true
	 */
	@Override
	public void setDeleted(boolean deleted) {
		this.deleted = deleted ? true : null;
	}

}