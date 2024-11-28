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

/**
 * Servlet implementation class ViewAnnouncementsServlet
 */
@WebServlet("/ViewAnnouncementsServlet")
public class ViewAnnouncementsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewAnnouncementsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            String selectQuery = "SELECT a.id, a.title, a.description, m.name AS member_name, a.created_at " +
                                 "FROM announcements a " +
                                 "JOIN members m ON a.member_id = m.id " +
                                 "ORDER BY a.created_at DESC";

            PreparedStatement pst = con.prepareStatement(selectQuery);
            ResultSet rs = pst.executeQuery();

            PrintWriter out = response.getWriter();
            out.println("<h2>Liste des annonces</h2>");
            while (rs.next()) {
                out.println("<div>");
                out.println("<h3>" + rs.getString("title") + "</h3>");
                out.println("<p>Publié par : " + rs.getString("member_name") + " le " + rs.getString("created_at") + "</p>");
                out.println("<p>" + rs.getString("description") + "</p>");
                out.println("</div><hr>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}
