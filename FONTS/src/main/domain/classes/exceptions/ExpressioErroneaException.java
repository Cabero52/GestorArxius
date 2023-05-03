package domain.classes.exceptions;

/**
 * La classe ExpressioErroneaException gestiona les excepcions generades al
 * intentar fer una factorial d'un nombre negatiu.
 */

public class ExpressioErroneaException extends Exception {

    String aux;
    public ExpressioErroneaException(String exp) {
        super("Expressio mal escrita");
        aux = exp;

    }
    public String toString() {
        return "Expressio mal escrita";
    }

}