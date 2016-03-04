/**
 * additionframe
 * AdminDao.java
 * 2015年12月2日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.base.dao;

import org.addition.plat.entity.Admin;

/**
 * Dao接口 - 管理员
 * @version 1.0.0
 * @since 1.0.0
 * @author Addition
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015年12月2日  LiangJiahao    created<br/>
 * <p/> 
 */
public interface AdminDao extends BaseDao<Admin, String> 
{
	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取管理员对象，若管理员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username);
}
