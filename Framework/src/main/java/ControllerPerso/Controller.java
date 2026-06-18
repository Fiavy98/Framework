package ControllerPerso;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)        // cette annotation est appliquer seulement sur les classes
@Retention(RetentionPolicy.RUNTIME) // lisible au moment de l'execution
public @interface Controller {
}

