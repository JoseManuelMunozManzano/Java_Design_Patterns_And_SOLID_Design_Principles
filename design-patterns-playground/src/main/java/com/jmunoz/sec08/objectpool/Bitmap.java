package com.jmunoz.sec08.objectpool;

// Concrete reusable.
// Hipotéticamente, lo que ocurre en esta clase es que, si alguien crea un objeto de esta clase,
// vamos a coger el nombre, vamos al disco, leemos el fichero, asignamos un array de bytes nuevo y
// almacenamos el contenido del fichero en ese fichero.
public class Bitmap implements Image {

    private Point2D location;

    // Representa un nombre de fichero del disco.
    private final String name;

    public Bitmap(String name) {
        this.name = name;
    }

    @Override
    public void draw() {
        System.out.println("Drawing "+name+" @ "+location);
    }

    @Override
    public Point2D getLocation() {
        return location;
    }

    @Override
    public void setLocation(Point2D location) {
        this.location = location;
    }

    // Tenemos que resetear el valor location, que corresponde al state.
    // El campo name no lo puede cambiar el cliente.
    @Override
    public void reset() {
        location = null;
        System.out.println("Bitmap is reset");
    }
}
