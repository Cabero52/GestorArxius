package domain.classes.exceptions;

/**
 * La classe NoExisteixException gestiona les excepcions generades al intentar obtenir un objecte amb parametres
 * que no exiteix
 */


public class NoExisteixException  extends Exception{
    private String aux;
    public NoExisteixException(String x) {
        super("No existeix: " +x);
        aux = x;
    }

    public String toString() {
        return "No existeix: " +aux;
    }
}
