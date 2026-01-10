package com.jmunoz.sec07.lazysingletondcl;

/**
 * This class demonstrates singleton pattern using Double Checked Locking or "classic" singleton.
 * This is also a lazy initializing singleton.
 * Although this implementation solves the multi-threading issue with lazy initialization using volatile
 * and double check locking, the volatile keyword is guaranteed to work only after JVMs starting with
 * version 1.5 and later.
 */
public class LazyRegistryWithDCL {

    // Esta instancia de singleton se creará cuando alguien pregunte por ella.
    // El constructor es privado, para evitar herencia y creación de objetos usando el operador new.
    private LazyRegistryWithDCL() {
    }

    // Esta variable estática contendrá la instancia singleton.
    // Se indica la palabra clave volatile para que, cuando trabajemos con threads, el valor no se cachee
    // en registros de CPU. Es decir, volatile fuerza la lectura/escritura del valor desde la memoria principal
    // en vez de desde caché. Con esto siempre obtenemos el último valor presente.
    private static volatile LazyRegistryWithDCL instance;

    // Este méto-do público devuelve siempre la MISMA instancia.
    // La primera vez que se le llama la crea y la devuelve, y las siguientes veces ya solo devuelve la instancia.
    // Se tiene en cuenta la posibilidad de que la llamen distintos threads.
    public static LazyRegistryWithDCL getInstance() {
        // Este doble if para ver si es null es a lo que se le llama double check locking.
        // Comprobamos dos veces para asegurarnos de que la instancia sigue siendo null cuando obtenemos el bloqueo
        // usando synchronized.
        if (instance == null) {
            synchronized (LazyRegistryWithDCL.class) {
                if (instance == null) {
                    instance = new LazyRegistryWithDCL();
                }
            }
        }
        return instance;
    }
}
