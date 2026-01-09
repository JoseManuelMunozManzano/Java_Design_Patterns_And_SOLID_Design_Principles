package com.jmunoz.sec06.abstractfactory;

import com.jmunoz.sec06.abstractfactory.aws.AwsResourceFactory;
import com.jmunoz.sec06.abstractfactory.Instance.Capacity;
import com.jmunoz.sec06.abstractfactory.gcp.GoogleResourceFactory;

public class Client {

	private final ResourceFactory factory;
	
	public Client(ResourceFactory factory) {
		this.factory = factory;
	}
	
	public Instance createServer(Instance.Capacity cap, int storageMib) {
		Instance instance = factory.createInstance(cap);
		Storage storage = factory.createStorage(storageMib);
		instance.attachStorage(storage);
		return instance;
	}

	// Usamos las instancias concretas de nuestro Abstract Factory.
    static void main() {
    	Client aws = new Client(new AwsResourceFactory());
    	Instance i1 = aws.createServer(Instance.Capacity.micro, 20480);
    	i1.start();
    	i1.stop();
    	
    	System.out.println("***************************************");
    	Client gcp = new Client(new GoogleResourceFactory());
    	i1 = gcp.createServer(Capacity.micro, 20480);
    	i1.start();
    	i1.stop();
    }
}
