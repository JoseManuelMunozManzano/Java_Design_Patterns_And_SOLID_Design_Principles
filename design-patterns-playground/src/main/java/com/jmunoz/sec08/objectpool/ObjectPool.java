package com.jmunoz.sec08.objectpool;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Supplier;

public class ObjectPool<T extends Poolable> {

    // Proveemos una forma de cachear objetos en memoria.
    // BlockingQueue permite thread safety al añadir o eliminar objetos de esta cola.
    private final BlockingDeque<T> availablePool;

    // Aprovechamos el constructor para crear objetos en nuestro object pool, usando un Supplier que nos pase
    // el cliente para llamar al creator y obtener objetos Bitmap, y una variable count para indicar el número
    // de objetos a crear.
    public ObjectPool(Supplier<T> creator, int count) {
        this.availablePool = new LinkedBlockingDeque<>();
        for (int i = 0; i < count; i++) {
            availablePool.offer(creator.get());
        }
    }

    // Proveemos una forma de obtener objetos de este object pool.
    public T get() {
        // Se bloquea si el object pool está vacío e intentamos coger un objeto.
        // Y, si está bloqueado y alguien interrumpe el bloqueo, lanza la excepción InterruptedException.
        // Si el object pool está vacío podemos:
        //   - Crear objetos nuevos, añadirlos al pool y devolverlo.
        //   - Si el objeto representa un recurso externo limitado, como una conexión a BBDD o un socket, podemos esperar.
        // En este ejemplo vamos a esperar hasta que un objeto quede disponible.
        try {
            return availablePool.take();
        } catch (InterruptedException e) {
            System.err.println("take() was interrupted");
        }

        return null;
    }

    // Proveemos una forma de liberar el objeto, reseteándolo y devolviéndolo al object pool (a availablePool)
    public void release(T obj) {
        obj.reset();
        try {
            availablePool.put(obj);
        } catch (InterruptedException e) {
            System.err.println("put() was interrupted");
        }
    }
}
