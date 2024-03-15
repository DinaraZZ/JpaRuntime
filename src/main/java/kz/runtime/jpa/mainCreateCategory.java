package kz.runtime.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class mainCreateCategory {
    static Connection connection;

    public static void main(String[] args) throws Exception {
//        ioCategory();
        ioProduct();
    }

    public static void ioCategory() throws Exception {
        String category = new String();
        String characteristics = new String();

        String jdbcUrl = "jdbc:postgresql://localhost:5432/zeinolla_d_db_4_products";
        String jdbcUsername = "postgres";
        String jdbcPassword = "postgres";
        connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

        do {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            System.out.print("Введите название категории: ");
            category = bufferedReader.readLine();
            System.out.print("Введите характеристики: ");
            characteristics = bufferedReader.readLine();
        } while (!uniqueCategory(category.trim()));

        addCategory(category, characteristics);
    }

    public static void ioProduct() throws IOException, SQLException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String jdbcUrl = "jdbc:postgresql://localhost:5432/zeinolla_d_db_4_products";
        String jdbcUsername = "postgres";
        String jdbcPassword = "postgres";
        connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

        System.out.print("Введите название товара: ");
        String product = bufferedReader.readLine();
        System.out.println();

        System.out.print("Введите цену: ");
        String price = bufferedReader.readLine();
        System.out.println();

        System.out.println("Выберите категорию");
        Map<Long, String> categories = getCategories();
        int i = 1;
        for (Long key : categories.keySet()) {
            System.out.println(i++ + " - " + categories.get(key));
        }
        System.out.print("Введите номер категории: ");
        String category = bufferedReader.readLine();
        System.out.println();
        Long categoryOrder = Long.parseLong(category);
        i = 1;
        Long categoryId = -1L;
        for (Long key : categories.keySet()) {
            if (categoryOrder == i) {
                categoryId = key;
                break;
            } else i++;
        }

        System.out.println("Опишите характеристики");
        Map<Long, String> characteristics = getCharacteristicsByCategory(categoryId);
        Map<Long, String> characteristicsInput = new HashMap<>();
        for (Long key : characteristics.keySet()) {
            System.out.print(characteristics.get(key) + ": ");
            String input = bufferedReader.readLine();
            characteristicsInput.put(key, input);
        }

        addProduct(product, price, categoryId, characteristicsInput);
    }


    public static void addCategory(String category, String characteristics) throws SQLException {
        String query = """
                insert into category(name)
                values (?);
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, category.trim());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        addCharacteristics(characteristics, generatedKeys);
    }

    public static void addProduct(String name, String price, Long categoryId, Map<Long, String> productCharacteristics)
            throws SQLException {
        String query = """
                insert into product(category_id, name, price)
                values(?, ?, ?);
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, categoryId);
        preparedStatement.setString(2, name);
        preparedStatement.setDouble(3, Double.parseDouble(price.trim()));
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();

        for (Long characteristicId : productCharacteristics.keySet()) {
            query = """
                    insert into product_characteristics (product_id, characteristics_id, description)
                    values (?, ?, ?);
                    """;
            PreparedStatement preparedStatement2 = connection.prepareStatement(query);
            preparedStatement2.setLong(1, generatedKeys.getLong(1));
            preparedStatement2.setLong(2, characteristicId);
            preparedStatement2.setString(3, productCharacteristics.get(characteristicId));
            preparedStatement2.executeUpdate();
        }
    }

    public static void addCharacteristics(String characteristics, ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            long key = generatedKeys.getLong(1);

            String[] characteristicsArr = characteristics.split(", ");
            StringBuilder insertQuery = new StringBuilder();
            for (int i = 0; i < characteristicsArr.length; i++) {
                insertQuery.append("insert into characteristics(category_id, name) values (");
                insertQuery.append(key);
                insertQuery.append(", '");
                insertQuery.append(characteristicsArr[i]);
                insertQuery.append("');\n");
            }
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString());
            preparedStatement.executeUpdate();
        }
    }

    public static boolean uniqueCategory(String category) throws SQLException {
        String query = """
                select name
                from category
                where name = ?
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, category);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return false;
        else return true;
    }

    public static Map<Long, String> getCategories() throws SQLException {
        String query = """
                select id, name
                from category;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<Long, String> categories = new HashMap<>();
        while (resultSet.next()) {
            categories.put(resultSet.getLong("id"), resultSet.getString("name"));
        }
        return categories;
    }

    public static Map<Long, String> getCharacteristicsByCategory(Long categoryId) throws SQLException {
        String query = """
                select id, name
                from characteristics
                where category_id = ?
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, categoryId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<Long, String> characteristics = new HashMap<>();
        while (resultSet.next()) {
            characteristics.put(resultSet.getLong("id"), resultSet.getString("name"));
        }
        return characteristics;
    }
}
