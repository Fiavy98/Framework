- Controlleur Personnaliser
    - ControllerPerso
        - Controller.java
- Classe Utilitaire : qui scan le classpath
    - ControllerPerso
        - ControllerScanner.java
            - scan () : 
                - cherche le classe @Controlleur apartir du .class et .jar
                - Retourne la liste de controlleur trouve
            - scanDirectory()
                - Parcourt le dossier .class
            - scanJar()
                - Parcourt un .jar
- Point d'entre (comme main)
    - DispatcherServlet.java
        - init()
            - Preparer tout dont ce que servlet a besoin avant de recevoir des requete
        - protected void doGet(HttpServletRequest request, HttpServletResponse response)
            - lire leliste deja preparer par init()
       -          
Tomcat démarre
    ↓
init() appelé une fois
    ↓
scan() → trouve HelloController, UserController, ProductController
    ↓
liste stockée en mémoire (private List<String> controllers)
    ↓
Tomcat prêt à recevoir des requêtes
