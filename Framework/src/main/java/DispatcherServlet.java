package main.java;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import ControllerPerso.ControllerScanner;
/////
/// ///
/// ///
/// //
public class DispatcherServlet extends HttpServlet {

    private List<String> controllers;

    @Override
    public void init() throws ServletException {
        try {
            String webInfPath = getServletContext().getRealPath("/WEB-INF");
            controllers = ControllerScanner.scan(webInfPath);
            System.out.println("Controllers trouvés : " + controllers);
        } catch (Exception e) {
            throw new ServletException("Erreur scan controllers", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Liste Controllers detectes</h2>");
        out.println("<ul>");
        for (String c : controllers) {
            out.println("<li>" + c + "</li>");
        }
        out.println("</ul>");
        out.println("</body></html>");
    }
}