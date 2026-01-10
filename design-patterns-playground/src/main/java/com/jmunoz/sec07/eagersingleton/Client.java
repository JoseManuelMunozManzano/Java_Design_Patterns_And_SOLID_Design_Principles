package com.jmunoz.sec07.eagersingleton;

public class Client {

	static void main() {
		// Obtenemos la única instancia existente en toda la app usando el méto-do estático que proveemos
		// en EagerRegistry.
		EagerRegistry registry = EagerRegistry.getInstance();

		// Da igual las veces que llamemos al méto-do, que siempre obtendremos la MISMA instancia.
		EagerRegistry registry2 = EagerRegistry.getInstance();

		// El operador == solo devuelve true si ambas referencias apuntas a exactamente el mismo objeto.
		System.out.println(registry == registry2);
	}
}
