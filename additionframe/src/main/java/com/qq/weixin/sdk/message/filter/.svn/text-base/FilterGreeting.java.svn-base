package com.qq.weixin.sdk.message.filter;

import com.qq.weixin.sdk.message.Message;

/**
 * filter whether the message is for everything<br>
 * so,if this filter locates in the last of filterchain,then it certainly
 * returns the message result
 * 
 * @author antoniohu
 */

public class FilterGreeting extends MessageFilterHelper implements
		IMessageFilter {

	@Override
	public Message doSpecailMessageFilter(Message message) {
		// return
		// buildMessageResultText(ResourceManager.getValue("default_greeting"));
//TODO
//		User user = new User();
//		user.setUserCode(message.getFromUserName());
//		// user.setUserName("1");
//		UserDao userDao = new UserDaoSpringImpl();
		return buildMessageResultText("Hello! 发送  @ + 你想说的话，记录当下，发送  # 查询...");
	}
}