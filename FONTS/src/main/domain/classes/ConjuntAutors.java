package domain.classes;

import java.util.TreeSet;

/**
 * @author Dídac Hispano Corbera (didac.hispano@estudiantat.upc.edu)
 */

public class ConjuntAutors {
    //Estructura de dades que conté els noms de tots els autors ordenats alfabèticament
    private TreeSet<String> conjuntAutors = new TreeSet<>();
    private Ordre o = new Ordre();

    //------------- CONSTRUCTORA -------------

    /**
     * Creadora per defecte
     */
    public ConjuntAutors() {

    }

    //------------- CONSULTORES -------------

    /**
     * @param  nom_a nom d'un autor
     * @return cert si existeix l'autor nom_a.
     */
    public boolean existsAutor(String nom_a) {
        return conjuntAutors.contains(nom_a);
    }

    /**
     *
     * @return retorna els autors del conjunt
     */
    public String[] getAutors() {
        String[] autors = new String[conjuntAutors.size()];
        int i = 0;
        for (String autor:conjuntAutors) {
            autors[i] = autor;
            ++i;
        }
        return autors;
    }


    //------------- MODIFICADORES -------------

    /**
     * @param nom_a nom de l'autor a afegir
     * S'ha afegit l'autor nom_a al conjunt d'autors.
     * En cas de ja existir mostra un missatge d'error.
     */
    public void addAutor(String nom_a) {
        conjuntAutors.add(nom_a);
    }

    /**
     * @param nom_a nom del autor a esborrar
     * Elimina l'autor nom_a del conjunt d'autors.
     * En cas de no existir mostra un missatge d'error.
     */
    public void removeAutor(String nom_a) {
        if (existsAutor(nom_a)) conjuntAutors.remove(nom_a);
    }

    //------------- LLISTATS -------------

    /**
     * @param prefix String que conté un prefix
     *               Llista els autors del Conjunt que comencen pel prefix pref.
     */
    public String[] llistarPerPrefix(String prefix, String ordre) {
        TreeSet<String> conjunt = new TreeSet<>();
        int tam = prefix.length();
        for (String iterador : conjuntAutors) {
            if (tam <= iterador.length())
                if (iterador.substring(0, tam).equals(prefix)) conjunt.add(iterador);
        }
        return o.ordenarStrings(conjunt, ordre);
    }

}
