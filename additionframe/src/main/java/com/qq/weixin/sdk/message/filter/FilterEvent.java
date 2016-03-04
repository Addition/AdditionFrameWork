package com.qq.weixin.sdk.message.filter;

import java.util.regex.Pattern;

import com.qq.weixin.sdk.message.Message;
import com.qq.weixin.sdk.message.MessageEvent;

/**
 * filter whether the message is for help
 * 
 * @author antoniohu
 * 
 */
public class FilterEvent extends MessageFilterHelper implements IMessageFilter {

	@Override
	public Message doSpecailMessageFilter(Message message) {
		if (message instanceof MessageEvent) {
			MessageEvent messageEvent = (MessageEvent) message;
			if (Pattern.compile("RECODE_SELECT")
					.matcher(messageEvent.getEventKey()).find()) {
//TODO
//				UserDao userDao = DaoFactory.getInstance().getUserDao();

				String content = " ";
//				User user = new User();
//				user.setUserCode(message.getFromUserName());
//				List<User> list = userDao.findUser();
//				for (User obj : list) {
//					content = content + obj.getContent() + "\n\r\n\r";
//				}
				return buildMessageResultText(content);
			}
		}
		return null;
	}
}
