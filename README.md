## Sprint 0
Dans un projet servlet on a un compilation suivant 
    et on ve faire un autre fonctionaliter 
- Dossier Framework 
    - src
        - main
            - java
                - HelloWorldServlet.java
            - webapp
                - WEB-INF
                    - web.xml
    - deploy.sh
- Dans Dossier Test
    - lib 
        - HeloWorldServlet.jar
    - webapp
        - WEB-INF
            - web.xml
    - deploy.sh

- le but est de fournir un .jar pour le Framework qui contient les servlets compilés
et on le deploye (le jar) dans le dossier Test qui va devenir un bibliotheque et le dossier Test va le deployer dans le webapp de tomcat.
alefa git ilay jar

- pour l'instant le Framework fournit directement un .war qui deploye directement dans 
le tomcat , qui doit modifier 

- voici le code precedent (a modifier )

public class HeloWorldServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out= response.getWriter();
        out.println("Hello World by Framework Test !");
}

}

- web.xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://java.sun.com/xml/ns/j2ee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
    http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
    version="2.4">
    <display-name>Application WEB affichant HelloWorld</display-name>

    <servlet>
    <servlet-name>HelloWorldServlet</servlet-name>
    <servlet-class>HeloWorldServlet</servlet-class>
    </servlet>

    <servlet-mapping>
    <servlet-name>HelloWorldServlet</servlet-name>
    <url-pattern>/zeze</url-pattern>
    </servlet-mapping>
    </web-app>

- deploy.sh
    
    #!/bin/bash

# Définition des variables
APP_NAME="Framework-Test"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/home/kamado/Tomcat/tomcat/apache-tomcat-10.0.16/webapps"

SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"
# Nettoyage et création du répertoire temporaire
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes

# Compilation des fichiers Java avec le JAR des Servlets
find $SRC_DIR -name "*.java" > sources.txt
javac -cp $SERVLET_API_JAR -d $BUILD_DIR/WEB-INF/classes @sources.txt
rm sources.txt

# Copier les fichiers web (web.xml, JSP, etc.)
cp -r $WEB_DIR/* $BUILD_DIR/

# Générer le fichier .war dans le dossier build
cd $BUILD_DIR || exit
jar -cvf $APP_NAME.war *
cd ..

# Déploiement dans Tomcat 
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo ""

echo "Déploiement terminé. Redémarrez Tomcat si nécessaire."

echo ""


cree un classe annotation controlleur personaliser 
Cree un classe Utilitaire qui parcourt la class path qui regarde la notation
    stocker anaty liste ilay controlleur hita
    fonction init()
        mi initialiser ny projet
        afficher la liste des classe 
        