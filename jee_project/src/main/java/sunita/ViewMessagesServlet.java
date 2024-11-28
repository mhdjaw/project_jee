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
 * Servlet implementation class ViewMessagesServlet
 */
@WebServlet("/ViewMessagesServlet")
public class ViewMessagesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewMessagesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String senderId = request.getParameter("sender_id");
        String receiverId = request.getParameter("receiver_id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            String selectQuery = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY created_at";
            PreparedStatement pst = con.prepareStatement(selectQuery);
            pst.setString(1, senderId);
            pst.setString(2, receiverId);
            pst.setString(3, receiverId);
            pst.setString(4, senderId);

            ResultSet rs = pst.executeQuery();
            PrintWriter out = response.getWriter();
            out.println("<h2>Messages échangés</h2>");
            while (rs.next()) {
                out.println("<p><strong>" + rs.getString("sender_id") + ":</strong> " + rs.getString("content") + " (" + rs.getString("created_at") + ")</p>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}
