package com.jmunoz.sec03.simplefactory;

/**
 * This class acts as a simple factory for creation of different posts on website.
 */
public class PostFactory {

	// Notar que el méto-do es estático. Esto es porque no suele necesitarse almacenar estado.
	// Notar también que se le pasa un parámetro y, comparándolo con un valor, devolvemos un objeto u otro.
	// Se le podrían pasar más parámetros que podríamos usar en el constructor de las clases que queremos instanciar.
	public static Post createPost(String type) {
        return switch (type) {
            case "blog" -> new BlogPost();
            case "news" -> new NewsPost();
            case "product" -> new ProductPost();
            default -> throw new IllegalArgumentException("Post type is unknown");
        };
	}
}
