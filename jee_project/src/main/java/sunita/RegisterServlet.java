package sunita;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    // Méthode doPost pour gérer l'inscription
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String experience = request.getParameter("experience");
        String equipment = request.getParameter("equipment");
        String typeMember = request.getParameter("type_member");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Charger explicitement le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données (MySQL dans cet exemple)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");
            
            String query = "INSERT INTO members (first_name, last_name, experience, equipment, type_member, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, experience);
            pst.setString(4, equipment);
            pst.setString(5, typeMember);
            pst.setString(6, email);
            pst.setString(7, password); // À sécuriser avec un algorithme de hachage en production

            int result = pst.executeUpdate();  // Exécuter la requête d'insertion

            // Réponse vers l'utilisateur
            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.println("<h3>Inscription réussie !</h3>");
            } else {
                out.println("<h3>Échec de l'inscription.</h3>");
            }

            // Fermer la connexion après l'opération
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}
