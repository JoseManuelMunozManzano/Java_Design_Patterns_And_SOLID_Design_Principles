package com.jmunoz.sec08.objectpool;

// Cualquier clase que implemente esta interface se puede agrupar en nuestro object pool.
public interface Poolable {

    // Resetea el state.
    void reset();
}
