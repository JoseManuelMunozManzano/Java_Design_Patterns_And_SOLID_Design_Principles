package com.jmunoz.sec09.objectadapter;

public class Main {

	static void main() {
		/** Using Object Adapter **/
		// Esta es la clase que realmente queremos usar.
		BusinessCardDesigner designer = new BusinessCardDesigner();

		Employee employee = new Employee();
		populateEmployeeData(employee);

		EmployeeObjectAdapter objectAdapter = new EmployeeObjectAdapter(employee);

		// Notar que podemos usar nuestra clase adapter, ya que implementa Customer.
		String card = designer.designCard(objectAdapter);

		System.out.println(card);
	}

	private static void populateEmployeeData(Employee employee) {
		employee.setFullName("Elliot Alderson");
		employee.setJobTitle("Security Engineer");
		employee.setOfficeLocation("Allsafe Cybersecurity, New York City, New York");
	}
}
