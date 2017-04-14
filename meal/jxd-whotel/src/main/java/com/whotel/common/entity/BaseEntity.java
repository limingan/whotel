package com.whotel.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Polymorphic;

import com.whotel.common.dao.mongo.IdType;

/**
 * 统一定义id的entity基类，同时加上了Morphia和JPA的注解以支持切换
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author
 */
@SuppressWarnings("serial")
// @javax.persistence.MappedSuperclass
@Polymorphic
public abstract class BaseEntity implements IdEntity<String>, Serializable {
	@Id
	@IdType(type = IdType.TYPE_RANDOM)
	private String id;

	// @javax.persistence.Id
	// @javax.persistence.Column(length = 32, nullable = false)
	// @javax.persistence.GeneratedValue(generator = "hibernate-uuid.hex")
	// @org.hibernate.annotations.GenericGenerator(name = "hibernate-uuid.hex",
	// strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		// 如id值为空，则赋值为null
		this.id = StringUtils.isBlank(id) ? null : id;
	}

	/**
	 * 根据自动生成的ID计算出持久化时间
	 * 
	 * @return
	 */
	public Date getPersistentDate() {
		try {
			Date date = new Date(Long.parseLong(id.substring(0, 8), 16) * 1000L);
			return date;
		} catch (Exception ex) {
			return null; // 有些ID不是自动生成的哦
		}
	}

	// @javax.persistence.Transient
	public boolean isNew() {
		return id == null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append("@");
		sb.append("id=").append(getId());
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseEntity))
			return false;
		final BaseEntity other = (BaseEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
			else
				return super.equals(obj);
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		} else {
			return this.getId().hashCode();
		}
	}

}
