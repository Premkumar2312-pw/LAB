import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        URL url = new URL("http://localhost:8080/api/products");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String line;
        StringBuilder json = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        reader.close();

        request.setAttribute("data", json.toString());

        RequestDispatcher rd = request.getRequestDispatcher("display.jsp");
        rd.forward(request, response);
    }
}
