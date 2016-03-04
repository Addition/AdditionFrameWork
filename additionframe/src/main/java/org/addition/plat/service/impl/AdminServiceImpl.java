/**
 * additionframe
 * AdminServiceImpl.java
 * 2015年12月2日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.service.impl;

import javax.annotation.Resource;

import org.addition.plat.base.dao.AdminDao;
import org.addition.plat.entity.Admin;
import org.addition.plat.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService
{
	@Resource
	private AdminDao adminDao;

	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}

	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}

	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}
}
