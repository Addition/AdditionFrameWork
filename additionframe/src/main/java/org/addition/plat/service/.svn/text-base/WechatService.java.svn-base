package org.addition.plat.service;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.addition.plat.encrypt.SignatureService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.qq.weixin.sdk.message.IMessage;
import com.qq.weixin.sdk.message.Message;
import com.qq.weixin.sdk.message.MessageEvent;
import com.qq.weixin.sdk.message.MessageImage;
import com.qq.weixin.sdk.message.MessageLink;
import com.qq.weixin.sdk.message.MessageLocation;
import com.qq.weixin.sdk.message.MessageText;
import com.qq.weixin.sdk.message.handler.MessageHandlerEvent;
import com.qq.weixin.sdk.message.handler.MessageHandlerHelper;
import com.qq.weixin.sdk.message.handler.MessageHandlerImage;
import com.qq.weixin.sdk.message.handler.MessageHandlerLink;
import com.qq.weixin.sdk.message.handler.MessageHandlerLocation;
import com.qq.weixin.sdk.message.handler.MessageHandlerText;
import com.qq.weixin.sdk.message.result.handler.MessageResultHandlerHelper;
import com.qq.weixin.sdk.message.result.handler.MessageResultHandlerMusic;
import com.qq.weixin.sdk.message.result.handler.MessageResultHandlerNews;
import com.qq.weixin.sdk.message.result.handler.MessageResultHandlerText;

/**
 * weixin tool class
 * 
 * @author antoniohu
 */
@Service("wechatService")
public class WechatService implements IMessage
{

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private HttpServletRequest request;// request
	private Message message;// message comes from
	private HttpServletResponse response;// response
	private Message messageResult;// message will return
	private MessageHandlerHelper messageHadler;// handle message
	private MessageResultHandlerHelper messageResultHandler;// handle result message
	private static final String TOKEN = "antoniohu";
	
	@Autowired(required = true)
	private SignatureService signatureService;

	// deal the message from user
	public void dealMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		this.request = request;
		this.response = response;
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		log.info("dealMessage get echostr :" + echostr + " signature:"
				+ signature + " nonce:" + nonce + " timestamp:" + timestamp);
		// message is from weixin server
		if (signature != null && signatureService.checkSignature(signature, timestamp, nonce,null))
		{
			// when get! when post, echostr is null or if(echostr != null)
			if (echostr != null)
			{
				response.setContentType("text/plain");
				response.getWriter().write(echostr);
			} else
			{
				// do post
				// important! otherwise error text encoding
				response.setContentType("text/xml;charset=UTF-8");
				try
				{
					parseInputStreamToMessage(request);
				} catch (Exception e)
				{
					log.error("parse message throw an exception:", e);
				}
				messageResult = messageHadler.handleMessage(message);
				writeMessageToOuputStream(response);
			}
		} else
		{
			// not from weixin, do nothing
			try
			{
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			} catch (ServletException e)
			{
				log.error("rederect to index throw an exception:", e);
			}
		}
	}

	// write the result message
	private void writeMessageToOuputStream(HttpServletResponse response) throws IOException
	{
		if (messageResult.getMsgType().equalsIgnoreCase(MESSAGE_RESULT_TEXT))
		{
			messageResultHandler = new MessageResultHandlerText();
		} else if (messageResult.getMsgType().equalsIgnoreCase(
				MESSAGE_RESULT_NEWS))
		{
			messageResultHandler = new MessageResultHandlerNews();
		} else if (messageResult.getMsgType().equalsIgnoreCase(
				MESSAGE_RESULT_MUSIC))
		{
			messageResultHandler = new MessageResultHandlerMusic();
		}
		String resultContent = messageResultHandler.buildMessageResult(messageResult);
		response.getWriter().print(resultContent);
	}

	// parse the message content to Message
	private void parseInputStreamToMessage(HttpServletRequest request) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = factory.newDocumentBuilder().parse(request.getInputStream());
		Element root = document.getDocumentElement();
		String type = (root.getElementsByTagName(TAG_MSGTYPE)).item(0)
				.getTextContent();// filter CDATA... -- text/image/...
		if (type.equalsIgnoreCase(MESSAGE_TEXT))
		{// put it first,because most
			// of time is text
			message = new MessageText();
			messageHadler = new MessageHandlerText();
		} else if (type.equalsIgnoreCase(MESSAGE_EVENT))
		{// do subscribe event
			message = new MessageEvent();
			messageHadler = new MessageHandlerEvent();
		} else if (type.equalsIgnoreCase(MESSAGE_IMAGE))
		{
			message = new MessageImage();
			messageHadler = new MessageHandlerImage();
		} else if (type.equalsIgnoreCase(MESSAGE_LINK))
		{
			message = new MessageLink();
			messageHadler = new MessageHandlerLink();
		} else if (type.equalsIgnoreCase(MESSAGE_LOCATION))
		{
			message = new MessageLocation();
			messageHadler = new MessageHandlerLocation();
		}
		messageHadler.parseMessage(message, root);// do the default/common
													// parse!
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public HttpServletResponse getResponse()
	{
		return response;
	}

	public void setResponse(HttpServletResponse response)
	{
		this.response = response;
	}

	public Message getMessage()
	{
		return message;
	}

	public void setMessage(Message message)
	{
		this.message = message;
	}

	public MessageHandlerHelper getMessageHadler()
	{
		return messageHadler;
	}

	public void setMessageHadler(MessageHandlerHelper messageHadler)
	{
		this.messageHadler = messageHadler;
	}

	public Message getMessageResult()
	{
		return messageResult;
	}

	public void setMessageResult(Message messageResult)
	{
		this.messageResult = messageResult;
	}

	public MessageResultHandlerHelper getMessageResultHandler()
	{
		return messageResultHandler;
	}

	public void setMessageResultHandler(
			MessageResultHandlerHelper messageResultHandler)
	{
		this.messageResultHandler = messageResultHandler;
	}

}
