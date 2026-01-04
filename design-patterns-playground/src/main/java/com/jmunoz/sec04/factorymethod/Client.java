package com.jmunoz.sec04.factorymethod;

import com.jmunoz.sec04.factorymethod.message.Message;

public class Client {

	static void main(String[] args) {
		printMessage(new JSONMessageCreator());
		
		printMessage(new TextMessageCreator());
	}
	
	public static void printMessage(MessageCreator creator) {
		Message msg = creator.getMessage();
		System.out.println(msg);
	}
}
