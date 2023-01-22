package alps.java.api;

import alps.java.api.model.Product;

public class Main {
    public static void main(String[] args) {
        String helloWorld = "Hello world!";
        System.out.println(helloWorld);
        Product newproduct= new Product("Keyboard");
        System.out.println(newproduct.getName());
    }
}