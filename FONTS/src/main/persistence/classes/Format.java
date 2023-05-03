package persistence.classes;

import persistence.classes.exceptions.DocumentNoTrobat;

/**
 * @author Dídac Hispano Corbera(didac.hispano@estudiantat.upc.edu)
 */

public interface Format {
    /**
     * Crea un document. Funcio abstracta reimplementada a les subclasses
     */
    void crearDocument() throws DocumentNoTrobat;

}