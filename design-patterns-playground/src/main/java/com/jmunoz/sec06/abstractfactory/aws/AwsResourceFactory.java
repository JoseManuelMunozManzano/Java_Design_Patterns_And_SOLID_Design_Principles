package com.jmunoz.sec06.abstractfactory.aws;

import com.jmunoz.sec06.abstractfactory.Instance;
import com.jmunoz.sec06.abstractfactory.Instance.Capacity;
import com.jmunoz.sec06.abstractfactory.ResourceFactory;
import com.jmunoz.sec06.abstractfactory.Storage;

//Factory implementation for Amazon Web Services platform resources
public class AwsResourceFactory implements ResourceFactory {

	@Override
	public Instance createInstance(Capacity capacity) {
		return new Ec2Instance(capacity);
	}

	@Override
	public Storage createStorage(int capMib) {
		return new S3Storage(capMib);
	}
}
