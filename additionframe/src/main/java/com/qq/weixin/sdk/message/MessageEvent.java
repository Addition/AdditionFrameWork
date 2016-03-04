package com.qq.weixin.sdk.message;

/**
 * message event
 * 
 * @author antoniohu
 * 
 */
public class MessageEvent extends Message {

	protected String event;
	protected String eventKey;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
