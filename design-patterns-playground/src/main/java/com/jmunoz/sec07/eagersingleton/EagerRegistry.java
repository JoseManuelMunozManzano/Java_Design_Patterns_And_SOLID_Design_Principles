package com.jmunoz.sec07.eagersingleton;

/**
 * This class uses eager initialization of singleton instance.
 */
public class EagerRegistry {

    // Usando un constructor privado evitamos que ninguna clase pueda extender Singleton (evitamos herencia)
    // Tampoco se va a poder crear una instancia desde otra cosa usando el operador new.
    private EagerRegistry() {
    }

    // Esta es la única instancia que se va a crear de esta clase.
    private static final EagerRegistry instance = new EagerRegistry();

    // Devuelve la instancia de esta clase.
    public static EagerRegistry getInstance() {
        return instance;
    }
}