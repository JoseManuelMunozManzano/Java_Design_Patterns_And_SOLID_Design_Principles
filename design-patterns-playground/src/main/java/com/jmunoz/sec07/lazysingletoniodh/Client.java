package com.jmunoz.sec07.lazysingletoniodh;

public class Client {

	static void main() {
		// Con esta instrucción nuestro constructor NO SE LLAMA.
		LazyRegistryIODH singleton;

		// Se obtiene la única instancia existente.
		// Si además es la primera llamada se llama al constructor y crea dicha instancia (lazy).
		singleton = LazyRegistryIODH.getInstance();

		// Para estas llamadas NO se llama al constructor ni se crea instancia. Se devuelve la instancia ya existente.
		singleton = LazyRegistryIODH.getInstance();
		singleton = LazyRegistryIODH.getInstance();
	}
}
