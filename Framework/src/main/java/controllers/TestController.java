package controllers;

import ControllerPerso.Controller;
import ControllerPerso.Mapping;
import ControllerPerso.ModelView;

@Controller
public class TestController {

    @Mapping(value = "andrana", method = "GET")
    public ModelView andrana() {
        ModelView mv = new ModelView("emp/list");
        mv.addData("ETU", "004373");
        mv.addData("nom", "Tsinjo");
        return mv;
    }

    @Mapping(value = "profil", method = "GET")
    public ModelView profil() {
        ModelView mv = new ModelView("emp/profil");
        mv.addData("titre", "Page Profil");
        return mv;
    }
}