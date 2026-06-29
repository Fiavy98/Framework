package ControllerPerso;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
    String value();                // l'URL  ex: "andrana"
    String method() default "GET"; // GET ou POST, GET par défaut
}