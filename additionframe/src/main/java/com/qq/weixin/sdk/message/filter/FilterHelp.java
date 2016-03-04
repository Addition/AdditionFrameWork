package com.qq.weixin.sdk.message.filter;

import java.util.regex.Pattern;

import org.addition.plat.utils.ResourceManager;

import com.qq.weixin.sdk.message.Message;
import com.qq.weixin.sdk.message.MessageText;
import com.qq.weixin.sdk.message.items.ItemsSource;

/**
 * filter whether the message is for help
 * 
 * @author antonio
 * 
 */
public class FilterHelp extends MessageFilterHelper implements IMessageFilter {

	@Override
	public Message doSpecailMessageFilter(Message message) {
		if (message instanceof MessageText) {
			MessageText messageText = (MessageText) message;
			if (Pattern.compile(ResourceManager.getValue("help")).matcher(messageText.getContent()).find()) {
				return buildMessageResultNews(ItemsSource.getGreetingItems());
				//return buildMessageResultText(ResourceManager.getValue("result_help"));
			}
		}
		return null;
	}
}
