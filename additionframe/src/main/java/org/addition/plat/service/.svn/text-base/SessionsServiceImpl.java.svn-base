package org.addition.plat.service;

import org.addition.plat.exception.SigninException;
import org.addition.plat.exception.SignoutException;
import org.addition.plat.model.LoginResult;
import org.addition.plat.model.SigninInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("sessionsService")
public final class SessionsServiceImpl
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionsServiceImpl.class);


	private SessionsServiceImpl()
	{
	}


	public LoginResult signin(SigninInfo signinInfo)
            throws SigninException
	{
		return null;
	}

	/**
	 *   * The method that pre-validates if the client which invokes the REST
	 * API is   * from a authorized and authenticated source.   *   * @param
	 * serviceKey The service key   * @param authToken The authorization token
	 * generated after login   * @return TRUE for acceptance and FALSE for
	 * denied.  
	 */
	public boolean isAuthTokenValid(String authToken)
	{
		return true;
	}

	public void logout(int sessionId, String authToken)
            throws SignoutException
	{	
		
	}


}
