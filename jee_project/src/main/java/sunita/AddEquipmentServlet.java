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

@WebServlet("/AddEquipmentServlet")
public class AddEquipmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddEquipmentServlet() {
        super();
    }

    // Méthode doPost pour gérer l'ajout d'un équipement
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String equipmentName = request.getParameter("equipment_name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        boolean available = Boolean.parseBoolean(request.getParameter("available"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            // Insertion des informations dans la table equipments
            String query = "INSERT INTO equipments (name, type, description, available) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, equipmentName);
            pst.setString(2, type);
            pst.setString(3, description);
            pst.setBoolean(4, available);

            int result = pst.executeUpdate(); // Exécution de la requête

            // Réponse à l'utilisateur
            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.println("<h3>Équipement ajouté avec succès !</h3>");
            } else {
                out.println("<h3>Échec de l'ajout de l'équipement.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}