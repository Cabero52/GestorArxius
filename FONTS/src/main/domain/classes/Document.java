package domain.classes;

/**
 * @author Marc Armengol Cabero (marc.armengol.cabero@estudiantat.upc.edu)
 */

public class Document {
    private String titol;
    private String autor;
    private String cont;

    // -------- Creadora --------

    /**
     *
     * @param nouAutor Autor propietari del document
     * @param nouTitol Titol del Document que es vol crear
     * @param contingut Continugt de document que es vol crear format per frases i paraules
     * Creadora per defecte de la classe document, inicialitza els atributs cont,autor,titol.
     * Els altres atributs: Disponibilitat inicia a true ja que just es creat i es posara a false quan es borri
     * path inicial per defecte és:
     */
    public Document(String nouAutor, String nouTitol, String contingut) {
        cont = contingut;
        autor = nouAutor;
        titol = nouTitol;
    }

    // -------- Consultora --------

    /**
     * Aquesta funció retorna el titol del document
     * @return Titol del document.
     */
    public String getTitol(){
        return titol;
    }

    /**
     * Aquesta funció retorna l'Autor del document
     * @return Autor del document
     */
    public String getAutor(){
        return autor;
    }

    /**
     * Aquesta funció retorna el contingut del document
     * @return Contingut del document
     */
    public String getCont() {
        return cont;
    }


    // -------- Modificadora --------

    /**
     * Insertem el Contingut que ens passen per parametre.
     * @param nouContingut String del nou contingut
     */
    public void editarContingut(String nouContingut) {
        cont = nouContingut;
    }


    /**
     * Reasignem el titol del Document.
     * @param  nouTitol String nou titol del document
     */
    public void renameTitol(String nouTitol) {
        titol = nouTitol;
    }

    /**
     * Reasignem l autor del Document.
     * @param nouAutor String nou autor del document
     */
    public void renameAutor(String nouAutor) {
        autor = nouAutor;
    }


}