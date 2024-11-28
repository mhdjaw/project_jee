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
import java.sql.ResultSet;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReservationServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String spaceId = request.getParameter("space_id");
        String equipmentId = request.getParameter("equipment_id");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données (MySQL dans cet exemple)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            // Vérification de la disponibilité de l'espace et de l'équipement
            String checkSpaceQuery = "SELECT availability FROM spaces WHERE id = ?";
            PreparedStatement pstSpace = con.prepareStatement(checkSpaceQuery);
            pstSpace.setString(1, spaceId);
            ResultSet rsSpace = pstSpace.executeQuery();

            if (rsSpace.next() && rsSpace.getBoolean("availability")) {
                // Si l'espace est disponible, vérifier la disponibilité de l'équipement
                String checkEquipmentQuery = "SELECT available FROM equipments WHERE id = ?";
                PreparedStatement pstEquipment = con.prepareStatement(checkEquipmentQuery);
                pstEquipment.setString(1, equipmentId);
                ResultSet rsEquipment = pstEquipment.executeQuery();

                if (rsEquipment.next() && rsEquipment.getBoolean("available")) {
                    // Si l'équipement est disponible, effectuer la réservation
                    String insertReservationQuery = "INSERT INTO reservations (user_id, space_id, equipment_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstInsert = con.prepareStatement(insertReservationQuery);
                    pstInsert.setString(1, userId);
                    pstInsert.setString(2, spaceId);
                    pstInsert.setString(3, equipmentId);
                    pstInsert.setString(4, startDate);
                    pstInsert.setString(5, endDate);

                    int result = pstInsert.executeUpdate();

                    // Réponse à l'utilisateur
                    PrintWriter out = response.getWriter();
                    if (result > 0) {
                        out.println("<h3>Réservation réussie !</h3>");
                    } else {
                        out.println("<h3>Échec de la réservation.</h3>");
                    }
                } else {
                    response.getWriter().println("<h3>Équipement non disponible.</h3>");
                }
            } else {
                response.getWriter().println("<h3>Espace non disponible.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}
