package com.jmunoz.sec04.factorymethod.message;

public class TextMessage extends Message {
	
	@Override
	public String getContent() {
		return "Text";
	}
}
