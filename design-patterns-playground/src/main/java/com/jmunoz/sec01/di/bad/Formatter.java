package com.jmunoz.sec01.di.bad;

//Common interface for classes formatting Message object
public interface Formatter {
	
	public String format(Message message) throws FormatException;
	
}
