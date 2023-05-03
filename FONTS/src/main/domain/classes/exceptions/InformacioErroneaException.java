package domain.classes.exceptions;

/**
 * La classe InformacioErroneaException gestiona les excepcions generades al introduir informacio erronea
 */

public class InformacioErroneaException extends Exception{
    String nom_aux;
    public InformacioErroneaException(String nom) {
        super("Informació mal escrita. " + nom + " no pot començar amb aquest caràcter");
        nom_aux = nom;
    }
    public String toString() {
        return "Informació mal escrita. " + nom_aux + " no pot començar amb aquest caràcter";
    }
}