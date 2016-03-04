/**
 * additionframe
 * AdminService.java
 * 2015年12月2日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.service;

import org.addition.plat.entity.Admin;

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
public interface AdminService extends BaseService<Admin, String> 
{
	/**
	 * 获取当前登录管理员,若未登录则返回null.
	 * 
	 * @return 当前登录管理员对象
	 */
	public Admin getLoginAdmin();
	
	/**
	 * 获取当前登录管理员(从数据库中加载),若未登录则返回null.
	 * 
	 * @return 当前登录管理员对象
	 */
	public Admin loadLoginAdmin();
	
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
