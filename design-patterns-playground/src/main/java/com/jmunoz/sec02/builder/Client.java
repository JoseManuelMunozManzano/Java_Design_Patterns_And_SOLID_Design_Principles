package com.jmunoz.sec02.builder;

import java.time.LocalDate;

// Este es nuestro cliente que además funciona como "director"
public class Client {

	public static void main(String[] args) {
		User user = createUser();
		UserDTOBuilder builder = new UserWebDTOBuilder();
		// El cliente tiene que proveer al director con un builder concreto.
		UserDTO dto = directBuild(builder, user);
		System.out.println(dto);
	}
	
	/**
	 * Este méto-do tiene el rol de director en el patrón builder.
	 */
	private static UserDTO directBuild(UserDTOBuilder builder, User user) {
		return builder.withFirstName(user.getFirstName()).withLastName(user.getLastName())
			   .withAddress(user.getAddress())
			   .withBirthday(user.getBirthday())
			   .build();
	}
	
	/**
	 * Devuelve un ejemplo de User.
	 */
	public static User createUser() {
		User user = new User();
		user.setBirthday(LocalDate.of(1960, 5, 6));
		user.setFirstName("Ron");
		user.setLastName("Swanson");
		Address address = new Address();
		address.setHouseNumber("100");
		address.setStreet("State Street");
		address.setCity("Pawnee");
		address.setState("Indiana");
		address.setZipcode("47998");
		user.setAddress(address);
		return user;
	}
}
