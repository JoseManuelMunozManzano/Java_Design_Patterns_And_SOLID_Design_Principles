package com.jmunoz.sec04.factorymethod;

import com.jmunoz.sec04.factorymethod.message.Message;

/**
 * This is our abstract "creator". 
 * The abstract method createMessage() has to be implemented by
 * its subclasses.
 */
public abstract class MessageCreator {

	// Lo que devolvemos es una instancia del tipo abstracto Message.
	public Message getMessage() {
		Message msg = createMessage();

		// Estos son procesamientos adicionales.
		// El rol Creator normalmente suele realizar procesamientos adicionales
		// sobre el objeto devuelto por el factory method.
		msg.addDefaultHeaders();
		msg.encrypt();
		
		return msg;
	}
	
	// Este es nuestro Factory method.
	// Suele ser protected porque queremos que el cliente use el méto-do getMessage()
	protected abstract Message createMessage();
}
