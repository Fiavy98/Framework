#!/bin/bash
# ─────────────────────────────────────────────
# Test/deploy.sh
# Rôle : assembler le .war à partir du .jar Framework
#        et déployer dans Tomcat
# ─────────────────────────────────────────────

APP_NAME="Framework-Test"
WEB_DIR="webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/home/kamado/Tomcat/tomcat/apache-tomcat-10.0.16/webapps"

# Vérification que le .jar Framework est présent
if [ -z "$(ls $LIB_DIR/*.jar 2>/dev/null)" ]; then
    echo "❌ Aucun .jar trouvé dans $LIB_DIR/. Lance d'abord Framework/deploy.sh"
    exit 1
fi

# Nettoyage
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR/WEB-INF/lib"

# ── Compilation des controllers de Test ───────────
mkdir -p "$BUILD_DIR/WEB-INF/classes"
find src/ -name "*.java" > sources.txt
javac -cp "$LIB_DIR/*.jar" -d "$BUILD_DIR/WEB-INF/classes" @sources.txt
rm sources.txt

if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation controllers. Abandon."
    exit 1
fi

# Copier le web.xml
cp "web.xml" "$BUILD_DIR/WEB-INF/"

# Copier le(s) .jar de lib/ dans WEB-INF/lib/
# Tomcat les chargera automatiquement comme bibliothèques
cp "$LIB_DIR/"*.jar "$BUILD_DIR/WEB-INF/lib/"

# Générer le .war
cd "$BUILD_DIR" || exit
jar -cvf "$APP_NAME.war" .
cd ..

# Déploiement dans Tomcat
cp -f "$BUILD_DIR/$APP_NAME.war" "$TOMCAT_WEBAPPS/"

echo ""
echo "✅ Déploiement de $APP_NAME.war terminé dans Tomcat."
echo "   Redémarrez Tomcat si nécessaire."
echo ""
echo "⏳ Attente du rechargement Tomcat..."
sleep 5
echo "Accueil             : http://localhost:8080/Framework-Test/zeze"
echo "Liste Contrôleurs   : http://localhost:8080/Framework-Test/LsControlleur"
echo "Gestion des Mappings: http://localhost:8080/Framework-Test/FrontServlet"