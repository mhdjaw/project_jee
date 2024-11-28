package com.jee_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
        try {
            // Charger le driver MySQL pour les versions 8.0 et supérieures
            Class.forName("com.mysql.cj.jdbc.Driver"); // Assurez-vous que le nom du driver est correct
            String url = "jdbc:mysql://localhost:3306/project_jee"; // Remplacez par le nom de votre base de données
            String user = "root";  // Nom d'utilisateur par défaut
            String password = ""; // Mot de passe par défaut (vide sur WAMP)
            
            // Retourner la connexion à la base de données
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver MySQL non trouvé", e);
        }
    }
}
