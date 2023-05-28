import java.sql.*;

public class Database {
    public static void main(String[] args) {
        String url="jdbc:mysql://192.168.1.108:3306/NerdyGadgetsV2";
        String username="GadgetMaster";
        String password="Poepzakje123?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connectie geslaagd");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Component");
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getDouble(2) + resultSet.getDouble(3));
            }
        }
        catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
