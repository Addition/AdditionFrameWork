/**
 * additionframe
 * AdminDetailsServiceImpl.java
 * 2015年12月2日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.addition.plat.base.dao.AdminDao;
import org.addition.plat.entity.Admin;
import org.addition.plat.entity.Role;
import org.addition.plat.utils.SystemConfig;
import org.addition.plat.utils.SystemConfigUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service("adminDetailsService")
@Transactional
public class AdminDetailsServiceImpl implements UserDetailsService 
{
	private static final long serialVersionUID = 2653636739190406891L;

	@Resource
	private AdminDao adminDao;

	public Admin loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		Admin admin = adminDao.getAdminByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("管理员[" + username + "]不存在!");
		}
		// 解除管理员账户锁定
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		if (admin.getIsAccountLocked() == true) {
			if (systemConfig.getIsLoginFailureLock() == true) {
				int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
				if (loginFailureLockTime != 0) {
					Long lockedDate = admin.getLockedDate();
					Date nonLockedTime = DateUtils.addMinutes(new Date(lockedDate), loginFailureLockTime);
					Date now = new Date();
					if (now.after(nonLockedTime)) {
						admin.setLoginFailureCount(0);
						admin.setIsAccountLocked(false);
						admin.setLockedDate(null);
						adminDao.update(admin);
					}
				}
			} else {
				admin.setLoginFailureCount(0);
				admin.setIsAccountLocked(false);
				admin.setLockedDate(null);
				adminDao.update(admin);
			}
		}
		admin.setAuthorities(getGrantedAuthorities(admin));
		return admin;
	}

	// 获得管理角色数组
	public Set<GrantedAuthority> getGrantedAuthorities(Admin admin) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (Role role : admin.getRoleSet()) {
			grantedAuthorities.add(new GrantedAuthorityImpl(role.getValue()));
		}
		return grantedAuthorities;
	}
}
