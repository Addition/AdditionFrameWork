package org.addition.plat.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.apache.cxf.interceptor.MessageSenderInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public class EncryptionOutInterceptor extends AbstractPhaseInterceptor<Message>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionOutInterceptor.class);

    private static final String JSON_MESSAGE
            = JsonUtils.buildSimpleJsonEntiry("104", "encryption failed");

    private EncryptionService encryptionService;

    public EncryptionOutInterceptor()
    {
        super(Phase.PREPARE_SEND);
        addAfter(MessageSenderInterceptor.class.getName());
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
        final Exchange exchange = message.getExchange();
        final Message request = exchange.getInMessage();
        final Map<String, List<String>> requestHeaders = CastUtils.cast((Map<?, ?>) request.get(Message.PROTOCOL_HEADERS));
        if (null != requestHeaders && !requestHeaders.isEmpty())
        {
            final List<String> encryptType = requestHeaders.get(HttpHeaderNames.ENCRYPT_TYPE);
            if (encryptType != null && !encryptType.isEmpty()
                    && EncryptTypeUtils.ENCRYPT_TYPE_ENCRYPT.equals(encryptType.get(0)))
            {
                try
                {
                    /* FIXME 应该加在加密成功之后，不能加在之前，但在之后加不生效
                     暂时找不到解决办法，加之出错概率很低(加密失败)，暂保持这样
                     */
                    // 添加 HTTP 加密类型头
                    addHeader(message, HttpHeaderNames.ENCRYPT_TYPE, EncryptTypeUtils.ENCRYPT_TYPE_ENCRYPT);
		            
                    final OutputStream os = message.getContent(OutputStream.class);
                    final CachedOutputStream cs = new CachedOutputStream();
                    message.setContent(OutputStream.class, cs);
                    message.getInterceptorChain().doIntercept(message);
                    final CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);
                    final InputStream in = csnew.getInputStream();
                    final String responseContent = IOUtils.toString(in);
                    final String enctypt = getEncryptionService().encrypt(responseContent);
                    // 这里对 responseContent 做处理，处理完后同理，写回流中
                    IOUtils.copy(new ByteArrayInputStream(enctypt.getBytes()), os);
                    os.flush();
                    os.close();
                    message.setContent(OutputStream.class, os);
                } catch (Exception ex)
                {
                    LOGGER.error("encryption failed", ex);
                    throw new WebApplicationException(Response.ok(JSON_MESSAGE,
                            MediaType.APPLICATION_JSON_VALUE).build());
                }
            }
        }
    }

    public static void addHeader(Message message, String name, String value)
    {
        Map<String, List<String>> protocolHeaders = CastUtils.cast((Map<?, ?>) message
                .get(Message.PROTOCOL_HEADERS));
        if (null == protocolHeaders)
        {
            protocolHeaders = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
            message.put(Message.PROTOCOL_HEADERS, protocolHeaders);
        }
        List<String> header = HttpHeaderHelper.getHeader(protocolHeaders, name);
        if (null == header)
        {
            header = new ArrayList<String>();
            protocolHeaders.put(name, header);
        }
        if (0 == header.size())
        {
            header.add(value);
        }
        else
        {
            header.set(0, header.get(0) + "," + value);
        }
    }

    public class CachedStream extends CachedOutputStream
    {
        public CachedStream()
        {
            super();
        }

        @Override
        protected void doFlush() throws IOException
        {
            currentStream.flush();
        }
    }
}
