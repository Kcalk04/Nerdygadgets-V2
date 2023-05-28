import java.sql.*;

public class Database {
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://192.168.1.108:3306/NerdyGadgetsV2";
        String username = "GadgetMaster";
        String password = "Poepzakje123?";
        return DriverManager.getConnection(url, username, password);
    }

    public static void insertData(String naam, double prijs, double beschikbaarheid, String type) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO Component (naam, prijs, beschikbaarheid, type) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, naam);
                statement.setDouble(2, prijs);
                statement.setDouble(3, beschikbaarheid);
                statement.setString(4, type);

                statement.executeUpdate();
                System.out.println("Data opgeslagen");
            }
        } catch (SQLException e) {
            System.out.println("Error bij opslaan data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println("Connection successful");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Component");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getDouble(2) + " " + resultSet.getDouble(3));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

