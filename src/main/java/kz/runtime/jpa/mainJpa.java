package kz.runtime.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import kz.runtime.jpa.entity.Category;
import kz.runtime.jpa.entity.Product;

import java.util.List;

public class mainJpa {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main"); // из xml файла
        EntityManager manager = factory.createEntityManager();

        /*Category category = manager.find(Category.class, 2);
        System.out.println(category.getName());

        Product product = manager.find(Product.class, 2);
        System.out.println(product.getName() + " - " + product.getCategory().getName());*/

        // Вывести товары по категории 1
        /*Long categoryId = 1L;
        Category category = manager.find(Category.class, categoryId);
        List<Product> productsByCategory = category.getProducts();
        System.out.println(category.getName() + ":");
        for (Product product : productsByCategory) {
            System.out.println(product.getName());
        }*/

        try {
            manager.getTransaction().begin(); // начинает тран

            // insert
            /*Category category = new Category();
            category.setName("New cat");
            manager.persist(category); // сохраняет в локальном кэше, не в бд */

            // update
            /*Category category = manager.find(Category.class, 16); // благодаря файнд не нужно персист
            category.setName("Смартфоны");*/

            // delete
            Category category = manager.find(Category.class, 16);
            manager.remove(category);

            manager.getTransaction().commit(); // отправляет
        } catch (Exception e) {
            manager.getTransaction().rollback(); // откатывает изм
        }


    }
}