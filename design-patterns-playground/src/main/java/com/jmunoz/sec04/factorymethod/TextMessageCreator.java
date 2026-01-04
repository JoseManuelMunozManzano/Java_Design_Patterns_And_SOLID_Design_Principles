package com.jmunoz.sec04.factorymethod;

import com.jmunoz.sec04.factorymethod.message.Message;
import com.jmunoz.sec04.factorymethod.message.TextMessage;

/**
 * Provides implementation for creating Text messages
 */
public class TextMessageCreator extends MessageCreator {

	@Override
	public Message createMessage() {
		return new TextMessage();
	}
}
