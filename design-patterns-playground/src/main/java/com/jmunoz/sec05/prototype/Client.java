package com.jmunoz.sec05.prototype;

public class Client {

	static void main() throws CloneNotSupportedException {
        Swordsman s1 = new Swordsman();
        s1.move(new Point3D(-10,0,0), 20);
        s1.attack();
        System.out.println(s1);

        // Aquí clonamos.
        Swordsman s2 = (Swordsman)s1.clone();
        System.out.println("Cloned swordsman" + s2);
	}
}
