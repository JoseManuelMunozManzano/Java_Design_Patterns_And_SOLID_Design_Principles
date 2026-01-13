package com.jmunoz.sec09.classadapter;

public class Main {

	static void main() {
		/** Using Class/Two-way adapter **/ 
		EmployeeClassAdapter adapter = new EmployeeClassAdapter();

		// Esto es posible y por eso Class Adapter también se llama Two Way Adapter, porque
		// donde se esperaba un Adaptee usamos un Adapter (porque lo extiende).
		populateEmployeeData(adapter);

		// Esta es la clase que realmente queremos usar.
		BusinessCardDesigner designer = new BusinessCardDesigner();

		// Notar que podemos usar nuestra clase adapter, ya que implementa Customer.
		// De nuevo, por esto se le llama Two Way Adapter, porque donde se espera la Target Interface
		// usamos un Adapter (porque lo implementa).
		String card = designer.designCard(adapter);

		System.out.println(card);
	}

	private static void populateEmployeeData(Employee employee) {
		employee.setFullName("Elliot Alderson");
		employee.setJobTitle("Security Engineer");
		employee.setOfficeLocation("Allsafe Cybersecurity, New York City, New York");
	}
}
