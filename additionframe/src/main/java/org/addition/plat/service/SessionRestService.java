/**
 * additionframe
 * SessionRestService.java
 * 2015年12月3日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * TODO Add class comment here<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author Addition
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015年12月3日  LiangJiahao    created<br/>
 * <p/> 
 */
@Path(value = "/v1")
public interface SessionRestService
{
	@GET    
	@Path("/user/{id}")     
	@Produces("application/json")    
	public Response getUsers(@Context HttpHeaders httpHeaders);
}
