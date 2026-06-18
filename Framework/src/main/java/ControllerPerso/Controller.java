package ControllerPerso;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)        // s'applique sur une CLASS
@Retention(RetentionPolicy.RUNTIME) // lisible au moment de l'exécution
public @interface Controller {
}

