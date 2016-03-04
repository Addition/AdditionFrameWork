/*
 * com.genew.nuas
 * AuthCheckInInterceptor.java
 * 2015-03-30
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 */
package org.addition.plat.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.addition.plat.service.INuServicesConfig;
import org.addition.plat.service.SessionsServiceImpl;
import org.addition.plat.utils.HttpHeaderNames;
import org.addition.plat.utils.JsonUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * 认证检查拦截器<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author XXZ
 * @history<br/>
 * <pre>
 * ver        date        author      desc
 * 1.0.0      2015-03-30  XXZ         created<br/>
 * </pre>
 * <p/>
 */
@Service("authCheckInInterceptor")
public class AuthCheckInInterceptor extends AbstractPhaseInterceptor<Message>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthCheckInInterceptor.class);

    private static final String JSON_MESSAGE
            = JsonUtils.buildSimpleJsonEntiry("100", "invalid token");

    private SessionsServiceImpl sessionsService;

    private boolean isDevelopmentMode;

    @Autowired(required = true)
    public void setNuServicesConfig(INuServicesConfig config)
    {
        isDevelopmentMode = config.isDevelopmentMode();
    }

    public boolean isDevelopmentMode()
    {
        return isDevelopmentMode;
    }

    public SessionsServiceImpl getSessionsService()
    {
        return sessionsService;
    }

    @Autowired(required = true)
    public void setSessionsService(SessionsServiceImpl sessionsService)
    {
        this.sessionsService = sessionsService;
    }

    public AuthCheckInInterceptor()
    {
        super(Phase.RECEIVE);
        addAfter(CredentialCheckInInterceptor.class.getName());
    }

    public void handleMessage(Message message) throws Fault
    {
        final HttpServletRequest request = (HttpServletRequest) message.get(
                AbstractHTTPDestination.HTTP_REQUEST);
        final StringBuffer requestUrl = request.getRequestURL();
        final String ip = request.getRemoteHost();
        final String path = (String) message.get(Message.PATH_INFO);
        final String method = (String) message.get(Message.HTTP_REQUEST_METHOD);
        if (isDevelopmentMode()
                && path.startsWith("/nuas/api/dev/")
                && "GET".equals(method))
        {
            LOGGER.debug("inteceptor for credential check ignored in"
                    + " development, url={}, clientIp={}", requestUrl, ip);
            return;
        }
        LOGGER.debug("inteceptor for auth check beginning, url={}, clientIp={}...", requestUrl, ip);
        final Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message
                .get(Message.PROTOCOL_HEADERS));

        // 对于非登录资源的访问，需要认证，登录资源，不需要认证，
        if (!path.endsWith("signin")
                && !path.endsWith("sessions")
                && !"POST".equals(method))
        {
            final List<String> authToken = HttpHeaderHelper.getHeader(headers, HttpHeaderNames.AUTH_TOKEN);
            if (null != authToken && !authToken.isEmpty())
            {
                final boolean b = getSessionsService().isAuthTokenValid(authToken.get(0));
                if (!b)
                {
                    LOGGER.error("auth token is invalid,  authToken={}", authToken);
                    throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                            MediaType.APPLICATION_JSON_VALUE).build());
                }
            }
            else
            {
                LOGGER.error("auth token is empty, authToken={}", authToken);
                throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                        MediaType.APPLICATION_JSON_VALUE).build());
            }
        }
        LOGGER.debug("inteceptor for auth check ended");
    }
}
