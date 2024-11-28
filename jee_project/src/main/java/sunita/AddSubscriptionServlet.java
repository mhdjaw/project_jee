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
 * Servlet implementation class AddSubscriptionServlet
 */
@WebServlet("/AddSubscriptionServlet")
public class AddSubscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddSubscriptionServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberId = request.getParameter("member_id");
        String spaceId = request.getParameter("space_id");
        String subscriptionType = request.getParameter("subscription_type");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String price = request.getParameter("price");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            // Insérer un abonnement
            String query = "INSERT INTO subscriptions (member_id, space_id, subscription_type, start_date, end_date, price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, memberId);
            pst.setString(2, spaceId);
            pst.setString(3, subscriptionType);
            pst.setString(4, startDate);
            pst.setString(5, endDate);
            pst.setString(6, price);

            int result = pst.executeUpdate();

            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.println("<h3>Abonnement ajouté avec succès !</h3>");
            } else {
                out.println("<h3>Échec de l'ajout de l'abonnement.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}