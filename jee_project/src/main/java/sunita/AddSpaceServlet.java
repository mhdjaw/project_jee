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

@WebServlet("/AddSpaceServlet")
public class AddSpaceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddSpaceServlet() {
        super();
    }

    // Méthode doPost pour gérer l'ajout d'un espace
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String spaceName = request.getParameter("space_name");
        String description = request.getParameter("description");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            // Insertion des informations dans la table spaces
            String query = "INSERT INTO spaces (name, description, capacity, availability) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, spaceName);
            pst.setString(2, description);
            pst.setInt(3, capacity);
            pst.setBoolean(4, availability);

            int result = pst.executeUpdate(); // Exécution de la requête

            // Réponse à l'utilisateur
            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.println("<h3>Espace ajouté avec succès !</h3>");
            } else {
                out.println("<h3>Échec de l'ajout de l'espace.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}
