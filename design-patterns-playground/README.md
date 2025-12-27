# JAVA DESIGN PATTERNS & SOLID PRINCIPLES

Vamos a estudiar principios SOLID y patrones de diseño.

## Project Setup

![alt Project Setup](./images/01-ProjectSetup.png)

## SOLID Design Principles

Vamos a hablar de los principios SOLID.

SOLID es un acronimo que representa cinco principios de diseño.

- Single Responsibility Principle.
- Open / Closed Principle.
- Liskov Substitution Principle.
- Interface Segregation Principle.
- Dependency Inversion Principle.

[Principios SOILD](../SOLID+Design+Principles.pdf)

### Single Responsibility Principle

**Nunca debería haber más de una razón para que una clase cambie.**

Esto significa que la clase debe proveer una funcionalidad muy focalizada o abordar una preocupación específica de nuestra funcionalidad deseada.

Al hacer una clase nos tenemos que preguntar ¿cuáles son las posibles razones para que esta clase tenga que cambiar? Si hay más de una razón, entonces no sigue este principio.

![alt Single Responsability Principle](./images/02-SingleResponsabilityPrinciple.png)

Si, por ejemplo, en una clase tenemos 3 responsabilidades distintas, entonces tenemos que separarlas en 3 clases, cada una gestionando una responsabilidad.

De esta forma, si algo cambia, nuestro código puede cambiarse de una manera organizada.

En `src/java/com/jmunoz` creamos los paquetes/clases siguientes:

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

Si queremos probar si un código o clase existente satisface el principio de responsabilidad único, tenemos que preguntarnos qué es lo que se supone que tiene que hacer la clase y qué es lo que está haciendo ahora mismo.

- En una aplicación MVC, un controller se supone que debe recibir peticiones de un cliente, entregarlas al resto de la aplicación para su procesamiento, obtener los resultados de ese procesamiento y devolver la respuesta al cliente.
- El controller no debe tener lógica de negocio en él.

Lo primero que tenemos que tener en cuenta a la hora de hacer algún cambio (refactor) es tener una buena batería de tests que prueben el código actual. Esto es porque no queremos introducir nuevos errores como resultado de la refactorizacion.

Nuestra clase `Main` sirve como clase de tests, pero en la vida real, necesitaremos usar la dependencia `JUnit` para crear tests.

Para hacer la refactorización, tenemos que identificar una responsabilidad que tenga la clase, en este caso `UserController`, que no debería tener.

Por ejemplo, la validación es algo que un controller no debe hacer. Entonces, creamos la clase `UserValidator` y nos llevamos ahí las validaciones. Nuestra clase `UserController` usará `UserValidator`.

Después de cada cambio, tenemos que ejecutar nuestros tests (en este ejemplo la clase `Main`) para comprobar que todo siga funcionando como antes de la refactorización.

**FAQ**

1. ¿Una utility class que contiene solo métodos estáticos viola SRP?

Una utility class es una colección de diferentes funciones. Así que, estrictamente hablando, si viola SRP.

En general, cuando se trata de métodos estáticos, la mayoría de los patrones y principios de POO quedan relegados a un segundo plano. Esto es porque un método estático no participa en ninguna característica importante de POO como la herencia, polimorfismo, etc.

Las utility classes son útiles para agrupar métodos reutilizables sin estado. Sin embargo, infringen tanto SRP como Open/Close.

La conclusión es que, para código reutilizable sin estado, se pueden usar utility classes. Sin embargo, deben diseñarse con cuidado para que los métodos estén al menos relacionados entre sí, como métodos de agrupación para trabajar con fechas, cadenas u otros tipos en una clase. Por lo tanto, incluso si violamos SRP y Open/Close, que exista al menos cierta cohesión. No crear nunca una única megaclase de utilidad donde se almacenan todos los métodos estáticos.

Estos métodos deben permanecer sin estado (no añadir variables non-final static que estos métodos utilicen o modifiquen) y siempre use métodos de utilidad solo si el método de instancia de una clase no tiene sentido, como último recurso.

2. En `UserController`, `UserValidator` es una variable local del método `createUser()`, mientras que `UserPersistanceStoreService` es un campo de `UserController`. ¿Cómo determinar si las clases recién creadas deben declararse como campos de Object o como variables locales en los métodos?

Es muy difícil establecer reglas precisas para esto, pero:

En general, si una variable se requiere en más de un método y ADEMÁS si su valor cambia durante la existencia del objeto y este cambio afecta también su comportamiento, podemos mantener la variable como un campo de esa clase.

Otro escenario estrechamente relacionado con el punto anterior es que el valor de la variable puede ser establecido por código externo tras la creación del objeto, y este nuevo valor es requerido por los métodos de dicho objeto para proporcionar funcionalidad. En este caso, la variable será un campo y otro código puede establecerlo mediante un setter. Para preservar ese valor para su uso posterior, se requiere un campo.

Un tercer escenario, no tan elegante, pero a veces práctico, es que el valor de la variable es costoso de establecer (por ejemplo, valor es un objeto nuevo cuya instanciación es costosa). Por lo tanto, aunque dicha variable pueda usarse en un solo método, la mantenemos como un campo para que las invocaciones posteriores de ese mismo método no requieran la creación/obtención de ese valor.

Las variables locales se utilizan si no tienen utilidad fuera del método, son económicas de crear y también si su estado después de su uso no es adecuado fuera de dicho método.

Tener en cuenta que estas no son las únicas condiciones para esta decisión, pero son algunas de las condiciones obvias para decidir entre un campo o una variable local.

Ahora, veamos nuestro ejemplo:

En muchas aplicaciones MVC del mundo real, el controlador recibe las dependencias requeridas, como el servicio de persistencia, mediante un contenedor IoC como Spring. Por lo tanto, normalmente encontrará controladores que declaran sus dependencias como campos, los cuales están conectados por algo como Spring.

Además, un servicio de persistencia se suele utilizar en más de un método, por ejemplo, guardar usuario, buscar un usuario, obtener todos los usuarios, etc.

Los validadores suelen ser objetos de bajo consumo, económicos de crear y no necesarios en todos los métodos. Por ejemplo, los métodos de búsqueda de usuario u obtención de todos los usuarios no requieren `UserValidator`.

Nuestro ejemplo de código no declara todos esos otros métodos, pero espero que esto aclare por qué necesitamos algunos objetos como campos y otros como variables locales.

3. En `UserValidator`, ver la línea `user.setName(user.getName().trim());`. `UserValidator` solo valida al usuario. No debería llamar a la función setter, ya que no forma parte de la validación.

¡Correcto!

Código como el anterior a veces se denomina "sanitización de entrada". Este código garantiza la seguridad de la entrada y elimina espacios en blanco y caracteres especiales añadidos inadvertidamente.

Marcar un espacio final como error de validación molestará a todos los usuarios, por lo que la mayoría de los sitios web sanearán al menos los espacios en blanco iniciales y finales antes de procesar la entrada para su validación. Normalmente, esto debería ocurrir en el lado del cliente, lo que significa que Angular/React o cualquier frontend que usemos debería realizar primero la `sanitization`. Sin embargo, el backend también debe realizar la misma `sanitization` como medida de seguridad. La razón es que algunos frontend nuevos podrían olvidar realizarla.

Siguiendo el SRP, un buen diseño consiste en tener una clase de `sanitization` de entrada adecuada cuya función sea SOLO tomar la entrada y sanitizarla por seguridad y conveniencia, eliminando espacios en blanco no deseados, caracteres especiales, etc. Sin embargo, en la vida real, la validación y la `sanitization` a menudo se combinan, ya que están relacionadas. Pero el punto sigue siendo válido: al menos en la lección sobre SRP, no deberían estar en la misma clase. Lo omitimos en nuestro ejemplo, ya que tener demasiadas clases complica los ejemplos para los nuevos usuarios.

### Open-Closed Principle

**Las entidades de software (clases, módulos, métodos, etc.) deben estar abiertas para la extensión, pero cerradas para la modificación**

Cuando decimos abierto para la extensión, significa que debemos ser capaces de extender el comportamiento existente.

Cuando decimos cerrado para la modificación, significa que el código que ya está escrito no debería tener que cambiarse.

![alt Open-Closed Principle](./images/03-OpenClosedPrinciple.png)

Imaginemos que tenemos una clase base en Java que ya está escrita y probada. Si queremos extenderla o modificar el comportamiento de uno de sus métodos, debemos ser capaces de hacerlo usando **herencia**, es decir, creando una clase derivada y haciendo override del método.

Cerrado para la modificación significa que no deberíamos tener que modificar el código del método directamente en la clase base.

En `src/java/com/jmunoz` creamos los paquetes/clases siguientes:

- `sec01`
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

Por tanto, lo que hemos hecho ha sido coger todo lo que hay común en clases y que no va a cambiar, en nuestro ejemplo la entidad de Subscriber. Hemos hecho una clase base (abstracta) `Subscriber` cuya idea es que esté cerrada para modificación.

Hemos creado en esa clase abstracta un método abstracto `calculateBill()` para que otras clases que hereden de `Subscriber` puedan extender el comportamiento de ese cálculo de factura a como se necesite.

### Liskov Substitution Principle

**Debemos poder sustituir objetos de clase base con objetos de clase hija y esto no debe alterar el comportamiento / características del programa.**

Si el objeto clase base provee un comportamiento específico y es sustituido por un objeto clase hijo, ese comportamiento debe ser el mismo.

En `src/java/com/jmunoz` creamos los paquetes/clases siguientes:

- `sec01`
  - `liskov`: Nuevo paquete para el principio de sustitución de Liskov.
    - `bad`: Nuevo paquete donde violamos el principio.
      - `Rectangle`: Clase rectángulo.
      - `Square`: Clase cuadrado. Es un tipo especial de rectángulo y extiende de él.
        - Podemos decir que un cuadrado es un rectángulo, si los campos width y height tienen el mismo valor.
      - `Main`: Clase principal para hacer las pruebas.
        - Para ejecutar y ver las aserciones, ir a la configuración de ejecución e indicar en los argumentos VM `-ea`.
        - ![alt VM Arguments](./images/04-Liskov-VMArguments.png)
        - Al ejecutar vemos que falla un test cuando se pasa a `useRectangle()` un cuadrado. Esto es porque en un cuadrado width y height tienen que tener el mismo valor, y ya no cumple los valores indicados en `useRectangle()`.
        - No se cumple Liskov.
    - `good`: Nuevo paquete donde seguimos el principio. Tenemos que romper la relación `Square extends Rectangle` porque cuando hablamos de programación orientada a objetos, un cuadrado NO es un rectángulo.
      - `Shape`: Interface con un solo comportamiento definido en ella, `computeArea()`.
      - `Rectangle`: Clase rectángulo que implementa `Shape`.
      - `Square`: Clase cuadrado que implementa `Shape`.
      - `Main`: Clase principal para hacer las pruebas.
        - Ahora el método `useRectangle()` solo aplica a rectángulos, para evitar violar el principio Liskov.
        - Para ejecutar y ver las aserciones, ir a la configuración de ejecución e indicar en los argumentos VM `-ea`.

### Interface Segregation Principle

**Los clientes no deber ser forzados a depender de interfaces que no usan**

En particular hablamos de métodos, es decir, los clientes no deberían tener que depender de métodos que están definidos en interfaces, pero que no usan.

Existe un término llamado `interface pollution` que significa que no deberíamos hacer nuestras interfaces muy grandes, atiborradas de métodos no relacionados entre sí y que luego nuestras clases implementen esa interface.

Una forma muy fácil de ver que este principio es violado, es buscar:

- Clases que tienen implementaciones de métodos vacías, sin código.
- Métodos que lanzan la excepción `UnsupportedOperationException` (o similar).
- Implementaciones de métodos que devuelven null o valores por defecto / dummy.

En todos estos casos, al final hablamos de métodos que no tienen sentido para esa clase en particular.

La solución para esto consiste en romper esas interfaces tan grandes en otras interfaces más pequeñas, pero no de cualquier modo.

Los métodos o comportamientos o contratos que estén definidos en estas nuevas interfaces más pequeñas deben estar relacionados entre ellos. Buscamos cohesión, porque no queremos que una clase esté forzada a proveer una implementación de un método que para esa clase no tiene sentido.

En `src/java/com/jmunoz` creamos los paquetes/clases siguientes:

- `sec01`
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

### Dependency Inversion Principle

Este principio viene en dos partes diferentes:

**A: Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones**

**B: Las abstracciones no deben depender de los detalles. Los detalles deben depender de abstracciones**

Cuando decimos módulo de alto nivel, queremos decir un módulo que provee o implementa reglas de negocio.

Cuando decimos módulo de bajo nivel, queremos indicar una funcionalidad que es tan básica que puede usarse en cualquier sitio, por ejemplo, escribir en disco o convertir un objeto Java en Json.

Una abstracción es algo tan sencillo como una interface.

Por último, tenemos que entender es que es una `dependencia`.

![alt Dependency](./images/05-Dependency.png)

Para esta función, las dependencias son `JSONFormatter()` y `FileWriter()`. Es decir, una dependencia es algo que necesitamos para proveer la funcionalidad que queremos a nuestro código.

Cuando hablamos de inversión de dependencias, hablamos de hacer lo contrario a lo que normalmente hacemos.

Lo que normalmente hacemos para crear una dependencia, tal y como podemos ver en la imagen, es crear un objeto usando el operador `new`.

El problema de usar `new` es que produce un acople fuerte de esa implementación en particular, y cualquier cambio en los requerimientos va a hacer que tengamos que modificar el código, lo que puede producir errores.

Para evitar esto es para lo que usamos la inversión de dependencias.

En vez de crear una instancia de un Object Mapper o de un FileWriter usando `new`, podemos usar dos interfaces, `Writer` y `Formatter`, por ejemplo, y escribiremos nuestro módulo de alto nivel usando esa interface.

![alt Dependency Inversion](./images/06-DependencyInversion.png)

Vemos en esta imagen que, en vez de usar `new`, aceptamos parámetros de esas interfaces y escribimos nuestro código usando estas interfaces.

De repente, nuestro código ya no queda fuertemente acoplado a ninguna clase concreta.

En vez de instanciar las dependencias nosotros, es otra cosa la las crea y nos da las dependencias.

En `src/java/com/jmunoz` creamos los paquetes/clases siguientes:

- `sec01`
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