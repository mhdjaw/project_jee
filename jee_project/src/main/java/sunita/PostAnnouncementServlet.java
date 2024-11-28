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
 * Servlet implementation class PostAnnouncementServlet
 */
@WebServlet("/PostAnnouncementServlet")
public class PostAnnouncementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PostAnnouncementServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberId = request.getParameter("member_id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            String insertQuery = "INSERT INTO announcements (member_id, title, description) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, memberId);
            pst.setString(2, title);
            pst.setString(3, description);

            int result = pst.executeUpdate();

            PrintWriter out = response.getWriter();
            if (result > 0) {
                out.println("<h3>Annonce publiée avec succès !</h3>");
            } else {
                out.println("<h3>Échec de la publication de l'annonce.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}