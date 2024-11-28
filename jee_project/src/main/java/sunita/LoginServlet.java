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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    // Méthode doPost pour gérer la connexion
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données (MySQL dans cet exemple)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_jee", "root", "");

            String query = "SELECT * FROM members WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            
            PrintWriter out = response.getWriter();
            
            if (rs.next()) {
                // Si l'utilisateur existe, rediriger vers la page d'accueil ou une autre page
                out.println("<h3>Connexion réussie ! Bienvenue " + rs.getString("first_name") + ".</h3>");
                // Ici, vous pouvez utiliser une redirection vers une page d'accueil par exemple :
                // response.sendRedirect("home.jsp");
            } else {
                // Si l'utilisateur n'est pas trouvé ou les informations sont incorrectes
                out.println("<h3>Email ou mot de passe incorrect.</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Erreur : " + e.getMessage() + "</h3>");
        }
    }
}
