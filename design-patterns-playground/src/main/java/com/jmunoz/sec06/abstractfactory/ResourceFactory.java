package com.jmunoz.sec06.abstractfactory;

import com.jmunoz.sec06.abstractfactory.Instance.Capacity;

//Abstract factory with methods defined for each object type.
public interface ResourceFactory {

	Instance createInstance(Capacity capacity);
	
	Storage createStorage(int capMib);
}
