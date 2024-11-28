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
 * Servlet implementation class AddCreditServlet
 */
@WebServlet("/AddCreditServlet")
public class AddCreditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddCreditServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberId = request.getParameter("member_id");
        String creditsBought = request.getParameter("credits_bought");
        String price = request.getParameter("price");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            // Insérer un achat de crédits
            String query = "INSERT INTO credit_purchases (member_id, credits_bought, price, purchase_date) VALUES (?, ?, ?, NOW())";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, memberId);
            pst.setString(2, creditsBought);
            pst.setString(3, price);

            int result = pst.executeUpdate();

            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.println("<h3>Crédits ajoutés avec succès !</h3>");
            } else {
                out.println("<h3>Échec de l'achat de crédits.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }

}
