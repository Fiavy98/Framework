package controllers;

import ControllerPerso.Controller;
import ControllerPerso.Mapping;

@Controller
public class TestController {

    @Mapping(value = "andrana", method = "GET")
    public void andrana() {
        System.out.println("GET andrana appelé !");
    }

    @Mapping(value = "andrana", method = "POST")
    public void andranaSave() {
        System.out.println("POST andrana appelé !");
    }

    @Mapping(value = "profil", method = "GET")
    public void profil() {
        System.out.println("GET profil appelé !");
    }
}