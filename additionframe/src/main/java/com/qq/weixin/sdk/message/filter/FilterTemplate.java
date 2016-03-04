package com.qq.weixin.sdk.message.filter;

import java.util.regex.Pattern;

import org.addition.plat.utils.ResourceManager;

import com.qq.weixin.sdk.message.Message;
import com.qq.weixin.sdk.message.MessageText;

/**
 * filter whether the message is for help
 * @author antoniohu
 */
public class FilterTemplate extends MessageFilterHelper implements
		IMessageFilter {

	@Override
	public Message doSpecailMessageFilter(Message message) {
		if (message instanceof MessageText) {
			MessageText messageText = (MessageText) message;
			
			if (Pattern.compile(ResourceManager.getValue("@"))
					.matcher(messageText.getContent().subSequence(0, 1)).find()) {
//TODO
//				User user = new User();
//				user.setUserCode(message.getFromUserName());
//				user.setContent(((MessageText) message).getContent());
//				UserDao userDao = new UserDaoSpringImpl();
//				userDao.addUser(user);
				String test = "记录成功！要坚持哦~";
				return buildMessageResultText(test);
			}
		}
		return null;
	}
}




