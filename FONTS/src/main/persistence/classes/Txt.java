package persistence.classes;

import persistence.classes.exceptions.DocumentNoTrobat;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Marc Cabero Armengol(marc.armengol.cabero@estudiantat.upc.edu)
 */

public class Txt implements Format{

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
    public Txt(String autor, String titol, String cont, String path){
        this.path = path + ".txt";
        this.autor = autor;
        this.titol = titol;
        this.cont = cont;
    }

    /**
     * Crea un document txt al path de la variable path
     */
    @Override
    public void crearDocument() throws DocumentNoTrobat {
        String info = autor + "\n" + titol + "\n" + cont;
        try {
            FileWriter escritor = new FileWriter(path, false);
            escritor.write(info);
            escritor.close();
        } catch (IOException ioe) {
            throw new DocumentNoTrobat("Suposo que el path fa coses rares");
        }
    }

}
