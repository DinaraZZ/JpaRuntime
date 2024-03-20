package kz.runtime.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import kz.runtime.jpa.entity.Category;

public class mainJpa {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main"); // из xml файла
        EntityManager manager = factory.createEntityManager();
        Category category = manager.find(Category.class, 2);
        System.out.println(category.getName());
    }
}
