package persistence.classes;

import persistence.classes.exceptions.DocumentNoTrobat;

/**
 * @author Marc Cabero Armengol(marc.armengol.cabero@estudiantat.upc.edu)
 */

public class ContextFormat {
    private Format format;

    /**
     * Creadora del ContextFormat
     */
    public ContextFormat() {

    }

    /**
     *
     * @param format format del document que volem tractar
     */
    public void setStrategy(Format format) {
        this.format = format;
    }

    /**
     * Crea un document
     */
    public void crearDocument() throws DocumentNoTrobat {
        format.crearDocument();
    }

}
