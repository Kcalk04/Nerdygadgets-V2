import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

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
            }
        } catch (SQLException e) {
            System.out.println("Error bij opslaan data: " + e.getMessage());
        }
    }

    public static void haalComponentenOp(ArrayList<Component> catalogusComponenten) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM Component";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                 ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String naam  = resultSet.getString("naam");
                    double prijs = resultSet.getDouble("prijs");
                    double beschikbaarheid = resultSet.getDouble("beschikbaarheid");

                    String typeString = resultSet.getString("type");
                    ComponentType type = null;
                    if (typeString != null) {
                        try {
                            type = ComponentType.valueOf(typeString);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e);
                        }
                    }
                    Component component = new Component(naam, prijs, beschikbaarheid, type);
                    catalogusComponenten.add(component);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
    }

    public static void verwijderComponent(String componentNaam) {
        String deleteQuery = "DELETE FROM Component WHERE naam = ?";

        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                String naam = SimulatieFrame.catalogusPanel.componentNaam;
                statement.setString(1, naam);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    Component componentToRemove = null;
                    for (Component component : SimulatieFrame.catalogusPanel.catalogusComponenten) {
                        if (component.getNaam().equals(componentNaam)) {
                            componentToRemove = component;
                            break;
                        }
                    }
                    if (componentToRemove != null) {
                        SimulatieFrame.catalogusPanel.catalogusComponenten.remove(componentToRemove);
                        SimulatieFrame.catalogusPanel.tekenCatelogus();
                        SimulatieFrame.catalogusPanel.repaint();
                        System.out.println("Gelukt");
                    }
                } else {
                    System.out.println("Niet gelukt");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

