package domain.classes.exceptions;

/**
 * La classe ExpressioErroneaException gestiona les excepcions generades al
 * intentar fer una factorial d'un nombre negatiu.
 */

public class NoGuardatCorrectament extends Exception {

    String aux;
    public NoGuardatCorrectament(String exp) {
        super("Document no s'ha pogut guardar");
        aux = exp;

    }
    public String toString() {
        return "Document no s'ha pogut guardar";
    }

}