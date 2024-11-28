package com.jee_project;

import com.jee_project.DatabaseUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/testConnection")
public class TestConnection extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Tester la connexion à la base de données
            Connection connection = DatabaseUtil.getConnection();

            // Si la connexion est réussie, afficher un message
            if (connection != null) {
                response.getWriter().write("Connexion à la base de données réussie !");
            }

            // Fermer la connexion après le test
            connection.close();
        } catch (SQLException e) {
            // Si une erreur de connexion survient, afficher l'erreur
            response.getWriter().write("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}
