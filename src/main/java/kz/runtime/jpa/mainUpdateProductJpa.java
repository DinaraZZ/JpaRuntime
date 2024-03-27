package kz.runtime.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import kz.runtime.jpa.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mainUpdateProductJpa {
    static EntityManagerFactory factory;

    public static void main(String[] args) throws IOException {
        // редактировние товара
        // id +
        // название +
        // стоимость +
        // все значения характеристики
        ioProduct();
    }

    public static void ioProduct() throws IOException {
        factory = Persistence.createEntityManagerFactory("main");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        List<Product> products = getProducts();
        Map<Integer, Product> productMap = new HashMap<>();
        int i = 1;
        for (Product product : products) {
            System.out.printf("%-3d %s\n", i, product.getName());
            productMap.put(i++, product);
        }
        System.out.print("Выберите номер товара: ");
        String productNumber = bufferedReader.readLine();
        Product product = productMap.get(Integer.parseInt(productNumber.trim()));

        System.out.println();
        System.out.println("Текущее название товара: " + product.getName());
        System.out.print("Введите новое название товара: ");
        String productNameNew = bufferedReader.readLine();

        System.out.println();
        System.out.println("Текущая цена товара: " + product.getPrice());
        System.out.print("Введите новую цену товара: ");
        String productPriceNew = bufferedReader.readLine();

        System.out.println();
        List<ProductCharacteristic> productCharacteristics = getProductCharacteristics(product.getId());
        /*for (ProductCharacteristic productCharacteristic : productCharacteristics) {
            System.out.println(productCharacteristic.getId() + " " +
                    productCharacteristic.getProduct().getName() +
                    " " + productCharacteristic.getCharacteristic().getName() +
                    " " + productCharacteristic.getDescription());
        }*/
        for (ProductCharacteristic characteristic : productCharacteristics) {
            System.out.printf("%s \"%s\": %s\n", "Текущее значение характеристики",
                    characteristic.getCharacteristic().getName(), characteristic.getDescription());

            System.out.print("Введите новое значение: ");
            String characteristicDescriptionNew = bufferedReader.readLine();
            characteristic.setDescription(characteristicDescriptionNew);
            System.out.println();
        }

        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();

            for (ProductCharacteristic characteristic : productCharacteristics) {
                ProductCharacteristic productCharacteristic = manager.find(ProductCharacteristic.class, characteristic.getId());
                productCharacteristic.setDescription(characteristic.getDescription());
                manager.persist(productCharacteristic);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
    }

    public static List<Product> getProducts() {
        EntityManager manager = factory.createEntityManager();
        TypedQuery<Product> productTypedQuery = manager.createQuery(
                "select p from Product p", Product.class
        );
        return productTypedQuery.getResultList();
    }

    public static List<ProductCharacteristic> getProductCharacteristics(Long productId) {
        EntityManager manager = factory.createEntityManager();

        TypedQuery<ProductCharacteristic> characteristicTypedQuery = manager.createQuery(
                "select p from ProductCharacteristic p where p.product.id = ?1", ProductCharacteristic.class
        );
        characteristicTypedQuery.setParameter(1, productId);
        return characteristicTypedQuery.getResultList();
    }

}