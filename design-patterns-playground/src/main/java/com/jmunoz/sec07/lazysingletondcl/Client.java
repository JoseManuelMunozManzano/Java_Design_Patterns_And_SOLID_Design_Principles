package com.jmunoz.sec07.lazysingletondcl;

public class Client {

	static void main() {
		// Obtenemos la única instancia existente en toda la app usando el méto-do estático que proveemos
		// en LazyRegistryWithDCL.
		LazyRegistryWithDCL lazySingleton1 = LazyRegistryWithDCL.getInstance();

		// Da igual las veces que llamemos al méto-do, que siempre obtendremos la MISMA instancia.
		LazyRegistryWithDCL lazySingleton2 = LazyRegistryWithDCL.getInstance();

		// El operador == solo devuelve true si ambas referencias apuntas a exactamente el mismo objeto.
		System.out.println(lazySingleton1 == lazySingleton2);
	}
}
