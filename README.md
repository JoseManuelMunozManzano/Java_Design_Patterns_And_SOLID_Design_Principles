# JAVA DESIGN PATTERNS & SOLID PRINCIPLES

Del curso de Udemy: https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects

Documentación del curso:

[SOLID](./SOLID+Design+Principles.pdf)

[Design Pattern](./Design+Patterns+in+Java+-+Summary+v2.pdf)

## SOLID Design Principles

Vamos a hablar de los principios SOLID.

SOLID es un acrónimo que representa cinco principios de diseño.

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

## Design Patterns

Los patrones de diseño se agrupan en:

- Creacionales: Dan soluciones a problemas comunes que tienen que ver con el proceso de creación de objetos de clases.
- Estructurales: Dan soluciones a problemas de como se organizan o componen las clases y los objetos para proveer funcionalidades.
  - Cuando indico componer quiero decir que podemos añadir otro objeto a nuestro objeto ya existente.
- Comportamiento: Describen como las clases y los objetos interaccionan y se comunican entre ellos, y como podemos diseñar la interacción entre grupos de objetos para poder derivar una funcionalidad, o conseguir el objetivo deseado con esos objetos.

Una vez que comprendemos como se crean estos grupos y lo que representan, es más fácil recordar donde pertenece cada patrón.

## Creational Design Patterns

Los patrones de diseño creacionales tratan con la creación de objetos de clases.

¿Por qué necesitamos patrones de diseño para crear un objeto de una clase? ¿No podemos usar el operador `new` y ya está?

Si, pero hay muchos requisitos adicionales que tienen lugar cuando estamos desarrollando una aplicación real.

Por ejemplo, nuestro objeto podría necesitar muchos otros objetos antes de que pueda crearse, o puede necesitar muchos pasos para poder crearse, como leer data de un fichero o hacer una query para leer de BD y de ahí crear el objeto final.

Otro requerimiento posible es que solo pueda existir un objeto de esa clase en toda la aplicación, porque esa clase puede ser una configuración.

Estos son los patrones de diseño creacionales que vamos a ver en el curso:

- Builder
- Simple Factory
- Factory Method
- Prototype
- Singleton
- Abstract Factory
- Object Pool

### Builder

[Builder](./design-patterns-playground/README.md#builder)

Ver proyecto `design-patterns-playground`:

- `sec02`
    - `builder`
        - `Address`: Clase normal usada en `User` para almacenar información de la dirección del usuario.
        - `User`: Clase Entity usada para construir el DTO. Este objeto lo obtendríamos desde una capa de persistencia, con data almacenada en BD.
        - `UserDTO`: Interface DTO.
        - `UserWebDTO`: Implementación de `UserDTO`. Cumple el rol `Product`, producto final a obtener del patrón `builder`. Vamos, lo que queremos construir.
        - `UserDTOBuilder`: Es una interface que provee los métodos necesarios para montar cada "parte" de nuestro objeto `UserDTO`. Es el rol `Builder`.
            - Cada uno de estos métodos devuelve una referencia a sí mismo (al builder), así que usaremos este tipo de implementación para usar `method chaining`.
            - El método `build()` es el que monta el objeto final.
        - `UserWebDTOBuilder`: Es una implementación concreta de `UserDTOBuilder`. Es el rol `ConcreteBuilder`.
            - Es lo que realmente construimos en esta clase.
        - `Client`: Es nuestra clase principal con el rol de `Director` que usa el builder `UserDTOBuilder`.
  - `builder2`: Otra implementación de `builder` muy usada en el mundo real.
      - `Address`: Clase normal usada en `User` para almacenar información de la dirección del usuario.
      - `User`: Clase Entity usada para construir el DTO. Este objeto lo obtendríamos desde una capa de persistencia, con data almacenada en BD.
      - `UserDTO`: Interface DTO. Cumple el rol de `Product`. Queremos crear objetos de esta clase.
          - No tenemos constructor, pero vamos a construir una instancia inmutable, usando métodos getter public y métodos setter private.
          - Es decir, esta forma de implementar el `builder` evita tener que tratar con constructores complejos.
          - Declaramos nuestro `builder` como una `inner static class`. Es decir, nuestro `builder` está contenido dentro de la clase cuyo objeto va a construir. Como es una clase interna, puede usar los métodos setter private.
          - Seguimos teniendo métodos para construir las partes de nuestro objeto.
          - Algo que también es común, pero no obligatorio, es un método estático dentro de nuestra clase `Product`, llamado `getBuilder()`, que devuelve una nueva instancia de nuestro `builder`.
      - `Client`: Es nuestra clase principal con el rol de `Director` que usa el método estático `getBuilder()`.

### Simple Factory

[Simple Factory](./design-patterns-playground/README.md#simple-factory)

Ver proyecto `design-patterns-playground`:

- `sec03`
    - `simplefactory`
        - `Post`: Es nuestra clase abstracta con rol `Product`. Representa un artículo que es publicado en un sitio web.
            - Se crean varias subclases de este rol `Product`.
        - `NewsPost`: Subclase de `Post`. Representa un artículo de noticias que es publicado es nuestro sitio web.
        - `BlogPost`: Subclase de `Post`. Representa un artículo publicado en nuestro blog.
        - `ProductPost`: Subclase de `Post`. Representa una página de información de un producto publicado en nuestro sitio web.
        - `PostFactory`: Toma el rol `Simple Factory`. Es la clase donde vamos a implementar el patrón `simple factory`.
            - Esta es la clase que se desarrolla en esta lección.
        - `Client`: Clase que usa nuestra implementación de `simple factory`.

### Factory Method

[Factory Method](./design-patterns-playground/README.md#factory-method)

Ver proyecto `design-patterns-playground`:

- `sec04`
    - `factorymethod`
        - `message`: Paquete con las clases que se quieren instanciar.
            - `Message`: Toma el rol `Product`. Es una clase abstracta y vamos a crear objetos de las subclases de `Message`.
            - `JSONMessage`: Toma el rol `Concrete Product`. Clase concreta que extiende de `Message`.
            - `TextMessage`: Toma el rol `Concrete Product`. Clase concreta que extiende de `Message`.
        - `MessageCreator`: Toma el rol `Creator`.
            - Clase abstracta que provee un `factory method` abstracto.
            - Esta clase se codifica en esta lección.
        - `JSONMessageCreator`: Toma el rol `Concrete Creator`.
            - Clase que implementa `MessageCreator` haciendo `override` del `factory method` y devolviendo un objeto de tipo `JSONMessage`.
            - Esta clase se codifica en esta lección.
        - `TextMessageCreator`: Toma el rol `Concrete Creator`.
            - Clase que implementa `MessageCreator` haciendo `override` del `factory method` y devolviendo un objeto de tipo `TextMessage`.
            - Esta clase se codifica en esta lección.
        - `Client`: Tiene un método `main()` donde usamos el patŕon de diseño `factory method`.