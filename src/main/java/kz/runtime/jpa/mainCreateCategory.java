package kz.runtime.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class mainCreateCategory {
    static PreparedStatement preparedStatement;
    static Connection connection;

    public static void main(String[] args) throws Exception {
        ioCategory();
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

    public static void ioProduct() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        System.out.println("Введите название товара: ");
        String product = bufferedReader.readLine();

        System.out.println("Введите цену: ");
        String price = bufferedReader.readLine();

        System.out.println("Выберите категорию: ");
        // showCategories();
        System.out.println("Опишите характеристики");
        // в общую таблицу
    }


    public static void addCategory(String category, String characteristics) throws SQLException {
        String query = """
                insert into category(name)
                values (?);
                """;
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, category.trim());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        addCharacteristics(characteristics, generatedKeys);
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
            preparedStatement = connection.prepareStatement(insertQuery.toString());
            preparedStatement.executeUpdate();
        }
    }

    public static boolean uniqueCategory(String category) throws SQLException {
        String query = """
                select name
                from category
                where name = ?
                """;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, category);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return false;
        else return true;
    }

}
