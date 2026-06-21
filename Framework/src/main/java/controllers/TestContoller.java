package controllers;

import ControllerPerso.Controller;
import ControllerPerso.Mapping;

@Controller
public class TestContoller {
    @Mapping("accueil")
    public void afficherAccueil() {
        System.out.println("Méthode accueil appelée !");
    }

    @Mapping("profil")
    public void afficherProfil() {
        System.out.println("Méthode profil appelée !");
    }
}