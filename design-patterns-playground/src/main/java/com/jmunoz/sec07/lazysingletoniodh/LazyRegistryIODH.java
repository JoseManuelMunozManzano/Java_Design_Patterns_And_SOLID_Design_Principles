package com.jmunoz.sec07.lazysingletoniodh;

/**
 * Singleton pattern using lazy initialization holder class. This ensures that, we have a lazy initialization
 * without worrying about synchronization.
 */
public class LazyRegistryIODH {

    // Esta instancia de singleton se creará cuando alguien pregunte por ella.
    // El constructor es privado, para evitar herencia y creación de objetos usando el operador new.
    private LazyRegistryIODH() {
        System.out.println("In LazyRegistryIODH singleton");
    }

    // Esta clase interna estática contendrá la instancia singleton.
    // Notar que no estamos declarando una variable estática es nuestra clase principal sino en una clase interna.
    // De esta forma nos aseguramos que, aunque inicialicemos la variable estática con nuestra instancia singleton,
    // esta no será creada a menos que alguien llama el méto-do getInstance().
    private static class RegistryHolder {
        static LazyRegistryIODH instance = new LazyRegistryIODH();
    }

    // Este méto-do público devuelve siempre la MISMA instancia.
    // Es la única forma de acceder a RegistryHolder y, por tanto, de obtener la instancia.
    public static LazyRegistryIODH getInstance() {
        return RegistryHolder.instance;
    }
}
