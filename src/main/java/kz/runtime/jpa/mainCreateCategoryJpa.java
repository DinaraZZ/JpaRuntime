package kz.runtime.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import kz.runtime.jpa.entity.Category;
import kz.runtime.jpa.entity.Characteristic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mainCreateCategoryJpa {
    static EntityManagerFactory factory;

    public static void main(String[] args) throws Exception {
        ioCategory();
    }

    public static void ioCategory() throws Exception {
        String category = new String();
        String characteristics = new String();

        factory = Persistence.createEntityManagerFactory("main");
        EntityManager entityManager = factory.createEntityManager();

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.print("Введите название категории: ");
        category = bufferedReader.readLine();
        System.out.print("Введите характеристики: ");
        characteristics = bufferedReader.readLine();

        String[] characteristicsArr = characteristics.split(", ");
        addCategoryCharacteristics(category, characteristicsArr);
    }

    public static void addCategoryCharacteristics(String categoryName, String[] characteristics) throws SQLException {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Category category = new Category();
            category.setName(categoryName);

            entityManager.persist(category);

            for (int i = 0; i < characteristics.length; i++) {
                Characteristic characteristic = new Characteristic();
                characteristic.setName(characteristics[i]);
                characteristic.setCategory(category);
                entityManager.persist(characteristic);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }
}
