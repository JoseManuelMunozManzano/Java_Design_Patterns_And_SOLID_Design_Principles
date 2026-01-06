package com.jmunoz.sec05.prototype;

/**
 * This class represents an abstract prototype & defines the clone method
 */
public abstract class GameUnit implements Cloneable {
	
	private Point3D position;
	
	public GameUnit() {
		position = Point3D.ZERO;
	}

	// Hacemos el méto-do público (en vez de protected) para que puedan usarlo clases
	// que estén fuera de este paquete.
	// En vez de Object, hacemos que devuelva un tipo covariante, es decir, una subclase o clase hija de Object,
	// en este caso GameUnit.
	// Mantenemos la cláusula throws para poder usarlo en clases que no soporten la clonación.
	@Override
	public GameUnit clone() throws CloneNotSupportedException {
		// Hacemos shallow copy del estado (super.clone()), porque la clase Point3D es inmutable.
		GameUnit unit = (GameUnit)super.clone();
		// Reseteamos los valores de la copia.
		unit.initialize();
		return unit;
	}

	protected void initialize() {
		this.position = Point3D.ZERO;
		// Como las clases hijas tienen su propio estado, también hay que resetearlo.
		reset();
	}

	// Abstracto porque el reseteo se hace en cada clase hija.
	protected abstract void reset();
	
	public GameUnit(float x, float y, float z) {
		position = new Point3D(x, y, z);
	}

	public void move(Point3D direction, float distance) {
		Point3D finalMove = direction.normalize();
		finalMove = finalMove.multiply(distance);
		position = position.add(finalMove);
	}
	
	public Point3D getPosition() {
		return position;
	}
}
