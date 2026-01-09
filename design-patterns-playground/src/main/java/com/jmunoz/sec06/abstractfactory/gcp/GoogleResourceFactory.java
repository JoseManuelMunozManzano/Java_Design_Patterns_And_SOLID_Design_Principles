package com.jmunoz.sec06.abstractfactory.gcp;

import com.jmunoz.sec06.abstractfactory.Instance;
import com.jmunoz.sec06.abstractfactory.Instance.Capacity;
import com.jmunoz.sec06.abstractfactory.ResourceFactory;
import com.jmunoz.sec06.abstractfactory.Storage;

//Factory implementation for Google cloud platform resources
public class GoogleResourceFactory implements ResourceFactory {

	@Override
	public Instance createInstance(Capacity capacity) {
		return new GoogleComputeEngineInstance(capacity);
	}

	@Override
	public Storage createStorage(int capMib) {
		return new GoogleCloudStorage(capMib);
	}
}
