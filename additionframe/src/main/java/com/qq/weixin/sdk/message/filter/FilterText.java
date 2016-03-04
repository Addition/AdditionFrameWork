package com.qq.weixin.sdk.message.filter;

import java.util.regex.Pattern;

import org.addition.plat.utils.ResourceManager;

import com.qq.weixin.sdk.message.Message;
import com.qq.weixin.sdk.message.MessageText;

/**
 * filter whether the message is for text
 * 
 * @author antoniohu
 */
public class FilterText extends MessageFilterHelper implements IMessageFilter {

	@Override
	public Message doSpecailMessageFilter(Message message) {
		if (message instanceof MessageText) {
			MessageText messageText = (MessageText) message;

			if (Pattern.compile(ResourceManager.getValue("RECORD_SELECT"))
					.matcher(messageText.getContent().subSequence(0, 1)).find()) {
//TODO
//				UserDao userDao = DaoFactory.getInstance().getUserDao();
				String content = " ";
			
//				List<User> list = userDao.findUser(message.getFromUserName());
//				for (User obj : list) {
//					
//					content = content + obj.getContent() + "\n\r\n\r";
//				}
				return buildMessageResultText(content);
			}
			;
			if (Pattern.compile(ResourceManager.getValue("RECORD_INSERT"))
					.matcher(messageText.getContent().subSequence(0, 1)).find()) {

//				User user = new User();
//				user.setUserCode(message.getFromUserName());
//				user.setContent(((MessageText) message).getContent());
//				UserDao userDao = new UserDaoSpringImpl();
//				userDao.addUser(user);
				return buildMessageResultText(ResourceManager.getValue("RECORD_SUCESS"));
			}
		}
		return null;
	}
}
