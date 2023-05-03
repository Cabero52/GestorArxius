package persistence.classes.exceptions;

/**
 * @author Jaume Cabero Corbera(elias.alos@upc.estudiantat.edu)
 */

public class InformacioBuida extends Exception {
    String auxP;
    public InformacioBuida(String path) {
        super("El path: " + path + " NO EXISTEIX");
        auxP = path;
    }

    public String toString() {
        return "El path: " + auxP + " NO EXISTEIX";
    }
}
