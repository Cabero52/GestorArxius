package domain.classes.exceptions;

/**
 * La classe JaExisteixException gestiona les excepcions generades intentar
 * crear un objecte amb parametres que ja existeixen.
 */

public class JaExisteixException extends Exception{
    private String aux;
    public JaExisteixException(String x) {
        super("Ja existeix: " +x);
        aux = x;
    }

    public String toString() {
        return "Ja existeix: " +aux;
    }
}