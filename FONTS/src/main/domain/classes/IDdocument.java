package domain.classes;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */

public class IDdocument implements Comparable<IDdocument>{
    private String autor;
    private String titol;

    //------------- CREADORA -------------

    /**
     * Creadora de l objecte IdDocument amb els seus atributs, nom Autor, nom Titol
     * @param nouA parametre que identifica l'autor
     * @param nouT parametre que identifica el titol
     */
    public IDdocument(String nouA, String nouT){
        autor = nouA;
        titol = nouT;
    }

    //------------- CONSULTORES -------------

    /**
     * Funció consultora que retorna l'autor del identificador
     * @return el nombre  del autor
     */
    public String first(){
        return autor;
    }

    /**
     * Funció consultora que retorna el titol del identificador
     * @return el nom del titol
     */
    public String second() {
        return titol;
    }

    //------------- MODIFICADORES -------------

    /**
     * Fucnio modificadora per canviar el nom del autor al identificador
     * @param nouAutor parametre que identifica el nou autor
     */
    public void modificarAutor(String nouAutor) {
        autor = nouAutor;
    }

    /**
     * Fucnio modificadora per canviar el nom Titol al identificador
     * @param nouTitol parametre que identifica el nou titol
     */
    public void modificarTitol(String nouTitol) {
        titol = nouTitol;
    }

    /**
     *
     * @param idd Objecte a ser comparat.
     * @return Un enter menor a zero si idd es major a b, major a zero si es menor i 0 si son iguals
     */
    @Override
    public int compareTo(IDdocument idd) {
        int b = this.autor.compareTo(idd.autor);
        if (b == 0) b = this.titol.compareTo(idd.titol);
        return b;
    }
}