package com.jmunoz.sec01.di.good;

public class TextFormatter implements Formatter{

	public String format(Message message){
		return message.getTimestamp()+":"+message.getMsg();
	}
}
