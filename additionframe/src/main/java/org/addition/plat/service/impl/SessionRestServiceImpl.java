/**
 * additionframe
 * SessionRestServiceImpl.java
 * 2015年12月4日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.service.impl;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.addition.plat.service.SessionRestService;
import org.springframework.stereotype.Service;

/**
 * TODO Add class comment here<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author Addition
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015年12月4日  LiangJiahao    created<br/>
 * <p/> 
 */
@Service("sessionRestService")
public class SessionRestServiceImpl implements SessionRestService
{

	/* (non-Javadoc)
	 * @see org.addition.plat.service.SessionRestService#getUsers(javax.ws.rs.core.HttpHeaders)
	 */
	@Override
	public Response getUsers(HttpHeaders httpHeaders)
	{
		return Response.status(Response.Status.OK).build();
	}

}
