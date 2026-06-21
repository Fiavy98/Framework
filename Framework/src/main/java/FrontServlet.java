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

    // Table de hachage : Clé = URL annotée, Valeur = {NomClasse, Méthode}
    private Map<String, MappingInfo> mappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            // 1. Récupérer le chemin de WEB-INF et scanner les contrôleurs
            String webInfPath = getServletContext().getRealPath("/WEB-INF");
            List<String> controllerNames = ControllerScanner.scan(webInfPath);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // 2. Parcourir chaque contrôleur trouvé pour chercher l'annotation @Mapping
            for (String className : controllerNames) {
                Class<?> clazz = classLoader.loadClass(className);
                
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Mapping.class)) {
                        Mapping mappingAnnotation = method.getAnnotation(Mapping.class);
                        String urlAnnotated = mappingAnnotation.value();
                        
                        // Enregistrement de l'association URL -> (Classe, Méthode)
                        mappingUrls.put(urlAnnotated, new MappingInfo(className, method));
                    }
                }
            }
            System.out.println(" [FrontServlet] Mappings chargés : " + mappingUrls.keySet());
        } catch (Exception e) {
            throw new ServletException("Erreur d'initialisation des mappings dans FrontServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Récupérer l'URL exacte saisie après le nom de l'application
        // Exemple : Si l'URL est "/Framework-Test/do/accueil", pathInfo sera "/accueil" (si mappé sur /do/*)
        // Pour faire simple avec votre structure actuelle, on va lire un paramètre "?path=..."
        String urlDemandee = request.getParameter("path");

        out.println("<html><body style='font-family: Arial, sans-serif; margin: 30px;'>");

        // --- CAS 1 : L'utilisateur ne demande rien (ex: /FrontServlet) -> On liste les URLs connues ---
        if (urlDemandee == null || urlDemandee.trim().isEmpty()) {
            out.println("<h2> FrontServlet : Liste des URLs disponibles</h2>");
            out.println("<table border='1' cellpadding='8' style='border-collapse: collapse; width: 50%;'>");
            out.println("<tr style='background-color: #f2f2f2;'><th>URL (path)</th><th>Classe associée</th><th>Méthode</th></tr>");
            
            for (Map.Entry<String, MappingInfo> entry : mappingUrls.entrySet()) {
                out.println("<tr>");
                out.println("  <td><a href='?path=" + entry.getKey() + "'><strong>" + entry.getKey() + "</strong></a></td>");
                out.println("  <td>" + entry.getValue().getClassName() + "</td>");
                out.println("  <td>" + entry.getValue().getMethod().getName() + "()</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } 
        
        // --- CAS 2 : L'URL demandée existe et est reconnue ---
        else if (mappingUrls.containsKey(urlDemandee)) {
            MappingInfo info = mappingUrls.get(urlDemandee);
            out.println("<h2 style='color: #2e7d32;'> URL Reconnue avec succès !</h2>");
            out.println("<div style='background-color: #e8f5e9; padding: 15px; border-left: 5px solid #2e7d32;'>");
            out.println("<p><strong>URL appelée :</strong> " + urlDemandee + "</p>");
            out.println("<p><strong>Classe cible :</strong> " + info.getClassName() + "</p>");
            out.println("<p><strong>Méthode cible :</strong> " + info.getMethod().getName() + "()</p>");
            out.println("</div>");
        } 
        
        // --- CAS 3 : L'URL est inconnue -> Exception simulée et affichage des solutions ---
        else {
            out.println("<h2 style='color: #c62828;'>❌ Erreur : URL Inconnue</h2>");
            out.println("<div style='background-color: #ffebee; padding: 15px; border-left: 5px solid #c62828; margin-bottom: 20px;'>");
            out.println("<strong>Exception :</strong> L'URL '<strong>" + urlDemandee + "</strong>' n'est associée à aucun contrôleur.");
            out.println("</div>");
            
            out.println("<p><strong>Voici les URLs valides disponibles dans l'application :</strong></p>");
            out.println("<ul>");
            for (String urlValide : mappingUrls.keySet()) {
                out.println("<li style='margin: 5px 0;'><a href='?path=" + urlValide + "'>" + urlValide + "</a></li>");
            }
            out.println("</ul>");
        }

        out.println("<br><hr>");
        out.println("<p><a href='FrontServlet'>Retour à l'accueil du FrontServlet</a> | <a href='LsControlleur'>Voir la liste brute des Contrôleurs</a></p>");
        out.println("</body></html>");
    }
}