package kz.runtime.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
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

        /*try {
            manager.getTransaction().begin(); // начинает тран

            // insert
            *//*Category category = new Category();
            category.setName("New cat");
            manager.persist(category); // сохраняет в локальном кэше, не в бд *//*

            // update
            *//*Category category = manager.find(Category.class, 16); // благодаря файнд не нужно персист
            category.setName("Смартфоны");*//*

            // delete
            Category category = manager.find(Category.class, 16);
            manager.remove(category);

            manager.getTransaction().commit(); // отправляет
        } catch (Exception e) {
            manager.getTransaction().rollback(); // откатывает изм
        }*/

        /*String query = "select c from Category c";
        TypedQuery<Category> categoriesQuery = manager.createQuery(query, Category.class);
        List<Category> categories = categoriesQuery.getResultList();
        for (Category category : categories) {
            System.out.println(category.getName());
        }*/

        /*int min = 1300;
        int max = 2000;
        TypedQuery<Product> productTypedQuery = manager.createQuery(
                "select p from Product p where p.price between ?1 and ?2", Product.class
        );
        // ?int - параметр и порядк номер, с 1 порядк
        productTypedQuery.setParameter(1, min);
        productTypedQuery.setParameter(2, max);
        List<Product> products = productTypedQuery.getResultList();
        for (Product product : products) {
            System.out.println(product.getName() + " " + product.getPrice() + " " + product.getId());
        }*/

        String category = "Процессоры";
        TypedQuery<Long> productsByCategoryQuery = manager.createQuery(
                "select count(p) from Product p where p.category.name = ?1", Long.class);
        productsByCategoryQuery.setParameter(1, category);
        Long sum = productsByCategoryQuery.getSingleResult();
        System.out.println(sum);
    }
}