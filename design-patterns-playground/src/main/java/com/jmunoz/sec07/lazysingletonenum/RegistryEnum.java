package com.jmunoz.sec07.lazysingletonenum;

/**
 * (Ref. Google I/O 2k8 Joshua Bloch)
 * Since Java 1.5 using enum we can create a singleton. It handles serialization using java's in-built
 * mechanism and still ensure single instance
 */
public enum RegistryEnum {

    // El uso de enum para crear singleton tiene estas ventajas:
    // Como no se puede extender de un enumerado, no tenemos que preocuparnos de la herencia.
    // Tampoco podemos crear objetos de enum nuevos, por lo que también evitamos ese problema.
    // Maneja el problema de serializacion/deserialización.
    //   En los ejemplos de singleton anteriores, que usan clases, si se deserializa una instancia
    //   de singleton previamente serializado, obtenemos otro objeto.
    //   Dicho esto, no es necesario serializar una instancia de singleton, y nadie lo hace.
    INSTANCE;

    public void getConfiguration() {

    }
}