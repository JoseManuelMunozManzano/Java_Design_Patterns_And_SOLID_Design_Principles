# JAVA DESIGN PATTERNS & SOLID PRINCIPLES

Del curso de Udemy: https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects

## SOLID Design Principles

Vamos a hablar de los principios SOLID.

SOLID es un acronimo que representa cinco principios de diseño.

- Single Responsibility Principle.
- Open / Closed Principle.
- Liskov Substitution Principle.
- Interface Segregation Principle.
- Dependency Inversion Principle.

[SOLID Design Principles](./design-patterns-playground/README.md#solid-design-principles)

Ver proyecto `design-patterns-playground`:

- `sec01`
    - `singleresp`: Nuevo paquete para el principio de responsabilidad única.
        - `bad`: Nuevo paquete donde violamos el principio.
            - `UserController`: Simula un controller hecho en una aplicación Spring Web MVC.
            - `User`
            - `Store`: Clase que simula algo que se almacena en base de datos.
            - `Main`: Clase que prueba toda la funcionalidad de `UserController`.
        - `good`: Nuevo paquete donde seguimos el principio.
            - `UserController`: Simula un controller hecho en una aplicación Spring Web MVC.
            - `User`
            - `Store`: Clase que simula algo que se almacena en base de datos.
            - `Main`: Clase que prueba toda la funcionalidad de `UserController`.
            - `UserValidator`: Clase que contiene las validaciones.
            - `UserPersistenceService`: Clase que contiene la lógica para guardar en BD (en el ejemplo un HashMap)
    - `openclosed`: Nuevo paquete para el principio de open-closed.
        - `bad`: Nuevo paquete donde violamos el principio.
            - `PhoneSubscriber`: Clase de suscriptores de una compañía telefónica.
                - Tiene un método `calculateBill()` y los campos que identifican un suscriptor.
            - `ISPSubscriber`: Clase de suscriptores a servicios de Internet.
                - Tiene un método `calculateBill()` y los campos que identifican un suscriptor.
            - `CallHistory`: Clase para llevar la cuenta del uso de data del móvil. No es importante para Open/Close y está para dar la sensación de una app completa.
            - `InternetSessionHistory`: Clase para llevar la cuenta del uso de data de Internet. No es importante para Open/Close y está para dar la sensación de una app completa.
        - `good`: Nuevo paquete donde seguimos el principio. En concreto eliminamos la duplicidad `calculateBill()`, pero además vemos que distintos tipos de subscriber básicamente solo tienen como diferencia la forma de calcular la factura.
            - `Subscriber`: Clase abstracta que representa un suscriptor. No indicamos de que es suscriptor.
                - Formada por los campos comunes que identifican un suscriptor en las clases `PhoneSubscriber` y `ISPSubscriber` y el cálculo de la factura (método abstracto).
                - Cerrada para modificación.
            - `PhoneSubscriber`: Clase de suscriptores de una compañía telefónica.
                - Ahora extiende de `Subscriber` y solo implementa el método `calculateBill()`, abierto a extensión.
            - `ISPSubscriber`: Clase de suscriptores a servicios de Internet.
                - Ahora extiende de `Subscriber` y solo implementa el método `calculateBill()`, abierto a extensión.
            - `CallHistory`: Clase para llevar la cuenta del uso de data del móvil. No es importante para Open/Close y está para dar la sensación de una app completa.
            - `InternetSessionHistory`: Clase para llevar la cuenta del uso de data de Internet. No es importante para Open/Close y está para dar la sensación de una app completa.
  - `liskov`: Nuevo paquete para el principio de sustitución de Liskov.
      - `bad`: Nuevo paquete donde violamos el principio.
          - `Rectangle`: Clase rectángulo.
          - `Square`: Clase cuadrado. Es un tipo especial de rectángulo y extiende de él.
              - Podemos decir que un cuadrado es un rectángulo, si los campos width y height tienen el mismo valor.
          - `Main`: Clase principal para hacer las pruebas.
              - Para ejecutar y ver las aserciones, ir a la configuración de ejecución e indicar en los argumentos VM `-ea`.
              - Al ejecutar vemos que falla un test cuando se pasa a `useRectangle()` un cuadrado. Esto es porque en un cuadrado width y height tienen que tener el mismo valor, y ya no cumple los valores indicados en `useRectangle()`.
              - No se cumple Liskov.
      - `good`: Nuevo paquete donde seguimos el principio. Tenemos que romper la relación `Square extends Rectangle` porque cuando hablamos de programación orientada a objetos, un cuadrado NO es un rectángulo.
          - `Shape`: Interface con un solo comportamiento definido en ella, `computeArea()`.
          - `Rectangle`: Clase rectángulo que implementa `Shape`.
          - `Square`: Clase cuadrado que implementa `Shape`.
          - `Main`: Clase principal para hacer las pruebas.
              - Ahora el método `useRectangle()` solo aplica a rectángulos, para evitar violar el principio Liskov.
              - Para ejecutar y ver las aserciones, ir a la configuración de ejecución e indicar en los argumentos VM `-ea`.
  - `segregation`: Nuevo paquete para el principio de segregación de interfaces.
      - `bad`
          - `entity`:
              - `Entity`: Clase base para nuestras entidades JPA.
              - `User`: Clase que extiende de `Entity`.
              - `Order`: Clase que extiende de `Entity`.
          - `service`:
              - `PersistenceService`: Interface común para las operaciones de persistencia.
                  - Tenemos `save()`, `delete()`, `findById()` y `findByName()`.
              - `UserPersistenceService`: Clase que realiza operaciones de persistencia para un `User`.
                  - Tenemos `save()`, `delete()`, `findById()` y `findByName()` y las utilizamos todas.
              - `OrderPersistenceService`: Clase que realiza operaciones de persistencia para un `User`.
                  - Tenemos `save()`, `delete()` y `findById()`.
                  - El método `findByName()` no tiene sentido para un pedido, y lanza una excepción `UnsupportedOperationException`.
                  - No cumple el principio de segregación de interfaces.
      - `good`
          - `entity`:
              - `Entity`: Clase base para nuestras entidades JPA.
              - `User`: Clase que extiende de `Entity`.
              - `Order`: Clase que extiende de `Entity`.
          - `service`:
              - `PersistenceService`: Interface común para las operaciones de persistencia.
                  - Tenemos `save()`, `delete()` y `findById()`.
                  - Ya no tenemos `findByName()`. Como es solo un método no creamos otra interface.
              - `UserPersistenceService`: Clase que realiza operaciones de persistencia para un `User`.
                  - Tenemos `save()`, `delete()` y `findById()`.
                  - `findByName()` la creamos para esta clase.
              - `OrderPersistenceService`: Clase que realiza operaciones de persistencia para un `User`.
                  - Tenemos `save()`, `delete()` y `findById()`.
                  - Ya no tenemos que implementar `findByName()`, porque no aparece en la interface.
  - `di`: Nuevo paquete para el principio de inversión de dependencias.
      - `bad`
          - `MessagePrinter`: Clase que recibe un objeto `Message` y un nombre de fichero, lo convierte a formato JSON y lo escribe en disco.
              - Tiene dependencias que hacen el código fuertemente acoplado a esas implementaciones (`JSONFormatter()` y `PrintWritter()`).
          - `Message`: Solo para completar la app.
          - `TextFormatter`: Solo para completar la app.
          - `FormatException`: Solo para completar la app.
          - `Formatter`: Solo para completar la app.
          - `JSONFormatter`: Solo para completar la app.
          - `Main`: Clase principal para las pruebas.
      - `good`
          - `MessagePrinter`: Clase que recibe un objeto `Message`, un objeto `Formatter` y un objeto `PrintWritter` para convertir el mensaje a formato JSON y escribirlo en disco.
              - Usa inversión de dependencias que hacen que el código no quede acoplado a implementaciones concretas.
          - `Message`: Solo para completar la app.
          - `TextFormatter`: Solo para completar la app.
          - `FormatException`: Solo para completar la app.
          - `Formatter`: Solo para completar la app.
          - `JSONFormatter`: Solo para completar la app.
          - `Main`: Clase principal para las pruebas. Crea y pasa las instancias de `Formatter` y `PrintWritter`.