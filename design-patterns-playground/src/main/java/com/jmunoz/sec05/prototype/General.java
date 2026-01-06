package com.jmunoz.sec05.prototype;

//Doesn't support cloning
public class General extends GameUnit{

	private String state = "idle";
	
	public void boostMorale() {
		this.state = "MoralBoost";
	}
	
	@Override
	public String toString() {
		return "General " + state + " @ " + getPosition();
	}

	// Al no soportar la clonación, lanzamos la excepción.
	@Override
	public GameUnit clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Generals are unique");
	}

	// Al no soportar la clonación, este méto-do no debe llamarse. Lanzamos la excepción.
	@Override
	protected void reset() {
		throw new UnsupportedOperationException("Reset not supported");
	}
}
