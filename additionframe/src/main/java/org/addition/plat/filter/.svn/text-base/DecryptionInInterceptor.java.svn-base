package org.addition.plat.filter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.addition.plat.encrypt.EncryptionService;
import org.addition.plat.utils.EncryptTypeUtils;
import org.addition.plat.utils.HttpHeaderNames;
import org.addition.plat.utils.JsonUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public class DecryptionInInterceptor extends AbstractPhaseInterceptor<Message>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DecryptionInInterceptor.class);

    public static final String MESSAGE_PROP_DECRYPTED = "MESSAGE_PROP_DECRYPTED";

    public static final String MESSAGE_PROP_ENCRYPTED = "MESSAGE_PROP_ENCRYPTED";

    private static final String JSON_MESSAGE
            = JsonUtils.buildSimpleJsonEntiry("103", "decryption failed");

    private EncryptionService encryptionService;

    public DecryptionInInterceptor()
    {
        super(Phase.RECEIVE);
        addAfter(AuthCheckInInterceptor.class.getName());
    }

    public EncryptionService getEncryptionService()
    {
        return encryptionService;
    }

    public void setEncryptionService(EncryptionService encryptionService)
    {
        this.encryptionService = encryptionService;
    }

    public void handleMessage(Message message) throws Fault
    {
        LOGGER.debug("inteceptor for decryption beginning...");
        // GET请求，没有 playload
        if (isGET(message))
        {
            return;
        }
        decrypt(message, getEncryptionService());
        LOGGER.debug("inteceptor for decryption ended");
    }

    /**
     * 根据消息解密
     * @param message 待解密的消息
     * @param encryptionService 解密服务
     * @return 如果内容不为空，返回解密后的字符串；如果内容为空，返回 null；如果解密失败，抛出异常
     */
    public static String decrypt(Message message, EncryptionService encryptionService)
    {
        final Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message
                .get(Message.PROTOCOL_HEADERS));
        if (null != headers && !headers.isEmpty())
        {
            final List<String> encryptType = HttpHeaderHelper.getHeader(headers, HttpHeaderNames.ENCRYPT_TYPE);
            final List<String> contentType = HttpHeaderHelper.getHeader(headers, "Content-Type");
            if (null != encryptType && !encryptType.isEmpty()
                    && null != contentType && !contentType.isEmpty() && contentType.get(0).startsWith("application/json"))
            {
                // 如果是加密模式
                if (EncryptTypeUtils.ENCRYPT_TYPE_ENCRYPT.equals(encryptType.get(0)))
                {
                    String decrypted = (String) message.get(MESSAGE_PROP_DECRYPTED);
                    // 已经解密
                    if (null != decrypted)
                    {
                        return decrypted;
                    }
                    try
                    {
                        final InputStream is = message.getContent(InputStream.class);
                        if (null == is)
                        {
                            return null;
                        }
                        LOGGER.debug("decrypting...");
                        final String encrypted = IOUtils.toString(is);
                        if (null != encrypted && !encrypted.isEmpty())
                        {
                            decrypted = encryptionService.decrypt(encrypted);
                            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decrypted.getBytes("utf-8"));
                            message.setContent(InputStream.class, byteArrayInputStream);
                        }
                        // 放入属性，供后续拦截器使用
                        message.put(MESSAGE_PROP_DECRYPTED, decrypted);
                        message.put(MESSAGE_PROP_ENCRYPTED, encrypted);
                        return decrypted;
                    } catch (Exception ex)
                    {
                        LOGGER.error("decryption failed", ex);
                        throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                                MediaType.APPLICATION_JSON_VALUE).build());
                    }
                }
            }
        }
        return null;
    }
}
