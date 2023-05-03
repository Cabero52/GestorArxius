package persistence.classes;

import persistence.classes.exceptions.DocumentNoTrobat;

import java.io.FileWriter;
import java.io.*;

/**
 * @author Dídac Hispano Corbera(didac.hispano@estudiantat.upc.edu)
 */

public class Xml implements Format {

    private String etiquetaInici = "<doc>" + "\n";;
    private String etiquetaFi = "\n" + "</doc>";
    private String autor;
    private String titol;
    private String cont;
    private String path;

    /**
     *
     * @param autor autor del document
     * @param titol titol del document
     * @param cont contingut del document
     * @param path path del document
     */
    public Xml(String autor, String titol, String cont, String path){
        this.path = path + ".xml";
        this.autor = "  <autor> " + autor + " </autor>" +"\n";
        this.titol = "  <titol> " + titol + " </titol>" + "\n";
        this.cont = "  <contingut> " + cont + " </contingut>";
    }

    /**
     * Crea un document xml al path de la variable path
     */
    @Override
    public void crearDocument() throws DocumentNoTrobat {
        String info = etiquetaInici + autor + titol + cont + etiquetaFi;
        try {
            FileWriter escritor = new FileWriter(path, false);
            escritor.write(info);
            escritor.close();
        } catch (IOException ioe) {
            throw new DocumentNoTrobat("peta a xml");
        }
    }

}
