package com.jmunoz.sec02.builder;

import java.time.LocalDate;

// Abstract builder
public interface UserDTOBuilder {

    // Métodos para construir las "partes" de un producto de uno en uno.
    // Gracias a que devuelven UserDTOBuilder podemos usar method chaining.

    UserDTOBuilder withFirstName(String fname);
    UserDTOBuilder withLastName(String lname);
    UserDTOBuilder withBirthday(LocalDate date);
    UserDTOBuilder withAddress(Address address);

    // Méto-do para montar el producto final. Lo devuelve.
    UserDTO build();

    // (Opcional) Méto-do para traer un objeto ya construido.
    UserDTO getUserDTO();
}
