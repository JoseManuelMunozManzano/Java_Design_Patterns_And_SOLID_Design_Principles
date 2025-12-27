package com.jmunoz.sec01.di.good;

public interface Formatter {
	
	public String format(Message message) throws FormatException;
	
}
