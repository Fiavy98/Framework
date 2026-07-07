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



1. ModelView
    - mitondra ny anaran'ilay page afficher-na sy ny donnes 
    - Misy 
        - naran'ilay view 
        - donnes (a envoyer)
        - ato no mi cree ny methode addData  (ajouter le donnes)

2. FrontServlet.java
    - executerMethode() : ilay methode tadiavin'ny url de manome ilay page tadiaviny 
        - mijery izy raha mire tourne ModelView ilay methode 
            - de ampidiriny ao anaty requete ny donnes ao amin'y modelView
3. Ty le jsp 