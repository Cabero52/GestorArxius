package persistence.classes.exceptions;

/**
 * @author Marc Hispano Alós(elias.el.mrabet@estudiantat.upc.edu)
 */

public class DocumentNoTrobat extends Exception {
    String auxP;
    public DocumentNoTrobat(String path) {
        super("El path: " + path + " NO EXISTEIX");
        auxP = path;
    }

    public String toString() {
        return "El path: " + auxP + " NO EXISTEIX";
    }
}
