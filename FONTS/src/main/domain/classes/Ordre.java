package domain.classes;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Marc Armengol Cabero (marc.armengol.cabero@estudiantat.upc.edu)
 */

public class Ordre {

    /**
     * Constructora per defecte
     */
    public Ordre(){}

    /**
     *
     * @param documents Conjunt de documents a ordenar
     * @param ordre Ordre en que es vol ordenar
     * @return Autor i titol de tots els documents de documents ordenats en l'ordre desitjat
     */
    public IDdocument[] ordenarDocuments(TreeSet<IDdocument> documents, String ordre) {
        IDdocument[] ordenatArray;
        if(ordre.equals("a")) {
            ordenatArray = documents.toArray(IDdocument[]::new);
        }
        else{
            SortedSet<IDdocument> ordenat = documents.descendingSet();
            ordenatArray = ordenat.toArray(IDdocument[]::new);
        }
        return ordenatArray;
    }

    /**
     *
     * @param aOrdenar Conjunt de documents a ordenar
     * @param ordre Ordre en que es vol ordenar
     * @return Conjunt de String ordenats segons l ordre ordre
     */
    public String[] ordenarStrings(TreeSet<String> aOrdenar, String ordre) {
        String[] ordenatArray;
        if(ordre.equals("a")) {
            ordenatArray = aOrdenar.toArray(String[]::new);
        }
        else{
            SortedSet <String> ordenat = aOrdenar.descendingSet();
            ordenatArray = ordenat.toArray(String[]::new);
        }
        return ordenatArray;
    }
}
