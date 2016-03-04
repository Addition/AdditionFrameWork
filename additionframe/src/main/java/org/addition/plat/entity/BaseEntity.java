package org.addition.plat.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEntity implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3930280560712282093L;
	private String id;// ID
	private long createDate;// 创建日期
	private long modifyDate;// 修改日期
	
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	@Column(updatable = false)
	public long getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(long createDate)
	{
		this.createDate = createDate;
	}
	public long getModifyDate()
	{
		return modifyDate;
	}
	public void setModifyDate(long modifyDate)
	{
		this.modifyDate = modifyDate;
	}
	
	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		final BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}
	
}
