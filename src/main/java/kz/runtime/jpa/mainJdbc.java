package kz.runtime.jpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mainJdbc {
    public static void main(String[] args) throws Exception {
        // 1
        /*String jdbcUrl = "jdbc:postgresql://localhost:5432/zeinolla_d_db_4_products";
        String jdbcUsername = "postgres";
        String jdbcPassword = "postgres";
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        String query = "select c.* from category c";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.next()); // true
        System.out.println(resultSet.getLong("id"));
        System.out.println(resultSet.getString("name"));
        while (resultSet.next()) {
            System.out.print(resultSet.getLong("id") + ": ");
            System.out.println(resultSet.getString("name"));
        }*/

        // 2
        /*String jdbcUrl = "jdbc:postgresql://localhost:5432/zeinolla_d_db_4_products";
        String jdbcUsername = "postgres";
        String jdbcPassword = "postgres";
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        String query = """
                select p.name p_name, p.price p_price, c.name c_name
                from product p
                join category c on c.id=p.category_id
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String productName = resultSet.getString("p_name");
            String categoryName = resultSet.getString("c_name");
            Double productPrice = resultSet.getDouble("p_price");

//            Formatter formatter = new Formatter();
            System.out.printf("%-25s%-15s%-6.2f\n", productName, categoryName, productPrice);
//            System.out.println(formatter);
        }*/

        // 3
        /*System.out.print("Введите название категории: ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String category = bufferedReader.readLine();

        String jdbcUrl = "jdbc:postgresql://localhost:5432/zeinolla_d_db_4_products";
        String jdbcUsername = "postgres";
        String jdbcPassword = "postgres";
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        String query = """
                select sum(p.price) / count(p.id)
                      from product p
                               join category c on c.id = p.category_id
                      where c.name = ?
                group by c.name""";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, category);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            System.out.println("Средняя стоимость товаров категории " + category + ": " + resultSet.getString(1));
        else System.out.println("Вы ввели несуществующую категорию");*/

        // 4
        System.out.print("Введите название категории: ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String category = bufferedReader.readLine();
        System.out.print("Введите процент увеличения стоимости: ");
        String percentStr = bufferedReader.readLine();
        int percent = Integer.parseInt(percentStr);
        String jdbcUrl = "jdbc:postgresql://localhost:5432/zeinolla_d_db_4_products";
        String jdbcUsername = "postgres";
        String jdbcPassword = "postgres";
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        String query = """
                update product
                set price = price * (100 + ?) / 100
                where category_id = (select c.id
                                     from category c
                                     where c.name = ?)
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, percent);
        preparedStatement.setString(2, category);
        preparedStatement.executeUpdate(); //update & delete & insert
    }
}
