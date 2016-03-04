/**
 * additionframe
 * AdminDaoImpl.java
 * 2015年12月2日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.base.dao.impl;

import org.addition.plat.base.dao.AdminDao;
import org.addition.plat.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * TODO Add class comment here<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author Addition
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015年12月2日  LiangJiahao    created<br/>
 * <p/> 
 */
@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao 
{
	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
}
