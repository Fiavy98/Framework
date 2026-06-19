## Sprint 0
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


## Sprint 1
cree un classe annotation pour controlleur (personaliser) 
Cree un classe Utilitaire qui parcourt la class path qui regarde la notation
    stocker dans  Liste<String> tous le controlleur qu on trouve
    afficher dans un pagela liste de controlleur
    on utilise 
    fonction init() : je ne comprend pas bien son role mais ce que j'ai entendue est :
        il initialiser ny projet
        but final => afficher la liste des classe  Conntroller dans un page au navigateur 

## Sprint 2
- annotation : @Mapping("url")
- target: method
- => **Afficher url miaraka @ Methode sy classe associer aminy (url : Methode->classe)**
    - url fantatra : maneho ny list ny url fantatra rehetra 
    - url ts fantatra : Exception miteny hoe tsy fantatro io fa reto ihany ho fantatro