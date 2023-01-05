package alps.net.api;

import alps.net.api.model.Product;

public class Main {
    public static void main(String[] args) {
        String helloWorld = "Hello world!";
        System.out.println(helloWorld);
        Product newproduct= new Product("Keyboard");
        System.out.println(newproduct.getName());
    }
}