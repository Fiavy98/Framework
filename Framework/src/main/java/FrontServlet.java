package main.java;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ControllerPerso.ControllerScanner;
import ControllerPerso.Mapping;
import ControllerPerso.MappingInfo;

public class FrontServlet extends HttpServlet {

    private Map<String, MappingInfo> mappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            String webInfPath = getServletContext().getRealPath("/WEB-INF");
            List<String> controllerNames = ControllerScanner.scan(webInfPath);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            for (String className : controllerNames) {
                Class<?> clazz = classLoader.loadClass(className);
                
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Mapping.class)) {
                        Mapping mappingAnnotation = method.getAnnotation(Mapping.class);
                        String urlAnnotated = mappingAnnotation.value();
                        
                        mappingUrls.put(urlAnnotated, new MappingInfo(className, method));
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Erreur d'initialisation des mappings", e);
        }
    }

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 1. Récupération du chemin relatif demandé
        String pathInfo = request.getPathInfo();
        String urlDemandee = "";
        if (pathInfo != null && pathInfo.length() > 1) {
            urlDemandee = pathInfo.substring(1); // ex: "accueil"
        }

        // 2. Reconstruction dynamique de l'URL de base (http://localhost:8080/Framework-Test/)
        String requestUrl = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        // On coupe l'URL juste après le nom de l'application pour avoir la racine
        String baseUrl = requestUrl.substring(0, requestUrl.indexOf(contextPath) + contextPath.length()) + "/";

        out.println("<html><body style='font-family: Arial, sans-serif; margin: 30px;'>");

        // --- CAS 1 & 2 : L'URL est vide OU elle est parfaitement connue ---
        if (urlDemandee.isEmpty() || mappingUrls.containsKey(urlDemandee)) {
            
            if (!urlDemandee.isEmpty()) {
                out.println("<p style='color: #555;'>Liste des url avec methode et classe:</p>");
            } else {
                out.println("<h2>Tableau des Mappings Disponibles</h2>");
            }

            // Affichage du tableau
            out.println("<table border='1' cellpadding='10' style='border-collapse: collapse; width: 75%; box-shadow: 0 2px 5px rgba(0,0,0,0.1);'>");
            out.println("<tr style='background-color: #f2f2f2; text-align: left;'>");
            out.println("  <th>URL Complète</th><th>Classe Associée</th><th>Méthode</th>");
            out.println("</tr>");
            
            for (Map.Entry<String, MappingInfo> entry : mappingUrls.entrySet()) {
                // Génération de l'URL absolue (ex: http://localhost:8080/Framework-Test/accueil)
                String urlComplete = baseUrl + entry.getKey();
                
                // Surligner la ligne de l'URL actuellement demandée si elle correspond
                String rowStyle = entry.getKey().equals(urlDemandee) ? "style='background-color: #e8f5e9; font-weight: bold;'" : "";
                
                out.println("<tr " + rowStyle + ">");
                // On affiche l'URL complète de manière visible dans le lien
                out.println("  <td><a href='" + urlComplete + "'>" + urlComplete + "</a></td>");
                out.println("  <td>" + entry.getValue().getClassName() + "</td>");
                out.println("  <td>" + entry.getValue().getMethod().getName() + "()</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } 
        
        // --- CAS 3 : L'URL est inconnue ---
        else {
            out.println("<h2 style='color: #c62828;'>URL Inconnue</h2>");
            out.println("<div style='background-color: #ffebee; padding: 15px; border-left: 5px solid #c62828; margin-bottom: 20px; max-width: 75%;'>");
            out.println("<strong>Erreur :</strong> L'URL '<strong>" + baseUrl + urlDemandee + "</strong>' n'existe pas dans le registre.");
            out.println("</div>");
            
            out.println("<p><strong>Veuillez utiliser l'une des URLs valides suivantes :</strong></p>");
            out.println("<ul style='line-height: 1.8;'>");
            for (String urlValide : mappingUrls.keySet()) {
                String urlCompleteValide = baseUrl + urlValide;
                out.println("<li><a href='" + urlCompleteValide + "'>" + urlCompleteValide + "</a></li>");
            }
            out.println("</ul>");
        }

        out.println("<br><hr>");
        out.println("<p><a href='" + baseUrl + "'>Retour à la gestion des Mappings</a> | <a href='LsControlleur'>Voir la liste brute des contrôleurs</a></p>");
        out.println("</body></html>");
    }
}