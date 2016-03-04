/*
 * com.genew.nuas
 * CredentialCheckInInterceptor.java
 * 2015-03-30
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 */
package org.addition.plat.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.addition.plat.encrypt.EncryptionService;
import org.addition.plat.encrypt.SignatureService;
import org.addition.plat.service.INuServicesConfig;
import org.addition.plat.utils.HttpHeaderNames;
import org.addition.plat.utils.JsonUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
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
 * 签名检查拦截器<p/>
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
@Service("credentialCheckInInterceptor")
public class CredentialCheckInInterceptor extends AbstractPhaseInterceptor<Message>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialCheckInInterceptor.class);

    private static final String JSON_MESSAGE
            = JsonUtils.buildSimpleJsonEntiry("102", "invalid signature");

    private SignatureService signatureService;

    private EncryptionService encryptionService;

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

    @Autowired(required = true)
    public void setSignatureService(SignatureService service)
    {
        signatureService = service;
    }

    public SignatureService getSignatureService()
    {
        return signatureService;
    }

    public EncryptionService getEncryptionService()
    {
        return encryptionService;
    }

    @Autowired(required = true)
    public void setEncryptionService(EncryptionService encryptionService)
    {
        this.encryptionService = encryptionService;
    }

    public CredentialCheckInInterceptor()
    {
        super(Phase.RECEIVE);
        addAfter(LoggingInInterceptor.class.getName());
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
            LOGGER.debug("inteceptor for credential check ignored"
                    + " in development, url={}, clientIp={}", requestUrl, ip);
            return;
        }
        LOGGER.debug("inteceptor for credential check beginning, url={}, clientIp={}...", requestUrl, ip);
        final Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message
                .get(Message.PROTOCOL_HEADERS));
        if (null == headers || headers.isEmpty())
        {
            LOGGER.error("headers empty, url={}, clientIp={}", requestUrl, ip);
            throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                    MediaType.APPLICATION_JSON_VALUE).build());
        }
        final List<String> signature = HttpHeaderHelper.getHeader(headers, HttpHeaderNames.SIGNATURE);
        final List<String> timestamp = HttpHeaderHelper.getHeader(headers, HttpHeaderNames.TIMESTAMP);
        final List<String> nonce = HttpHeaderHelper.getHeader(headers, HttpHeaderNames.NONCE);
        // 检查有没有必要的签名头
        if ((null == signature || signature.isEmpty())
                || (null == timestamp || timestamp.isEmpty())
                || (null == nonce || nonce.isEmpty()))
        {
            LOGGER.error("no necessary http header for signature, url={}, clientIp={}", requestUrl, ip);
            throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                    MediaType.APPLICATION_JSON_VALUE).build());
        }

        // 当加密模式下，计算签名时，需要加上密文
        final String encrypted;
        // GET请求，没有 playload
        if (isGET(message))
        {
            encrypted = null;
        }
        else
        {
            DecryptionInInterceptor.decrypt(message, getEncryptionService());
            encrypted = (String) message.get(DecryptionInInterceptor.MESSAGE_PROP_ENCRYPTED);
        }
        final String sha1 = getSignatureService().getSha1(timestamp.get(0),
                nonce.get(0), encrypted);
        if (!signature.get(0).equals(sha1))
        {
            LOGGER.error("calculated signature invalid, clientIp={}, url={}, clientIp={}", requestUrl, ip);
            throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                    MediaType.APPLICATION_JSON_VALUE).build());
        }
        LOGGER.debug("inteceptor for credential check ended");
    }
}
