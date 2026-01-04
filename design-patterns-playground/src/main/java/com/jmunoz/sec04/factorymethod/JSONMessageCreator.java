package com.jmunoz.sec04.factorymethod;

import com.jmunoz.sec04.factorymethod.message.JSONMessage;
import com.jmunoz.sec04.factorymethod.message.Message;

/**
 * Provides implementation for creating JSON messages
 */
public class JSONMessageCreator extends MessageCreator {

	@Override
	public Message createMessage() {
		return new JSONMessage();
	}
}
