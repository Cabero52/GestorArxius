package domain.classes;

import java.util.TreeSet;

public class ConjuntExpressions {
    //Estructura de dades que conté els noms de totes les expressions ordenades alfabèticament

    private TreeSet<String> conjuntExpressions = new TreeSet<>();

    //------------- CONSTRUCTORA -------------

    /**
     * Creadora per defecte
     */
    public ConjuntExpressions() {

    }

    //------------- CONSULTORES -------------

    /**
     * @param  nom_e nom d'una expressio
     * @return cert si existeix l'expressio amb nom_e.
     */
    public boolean existsExpressio(String nom_e) {
        return conjuntExpressions.contains(nom_e);
    }

    //------------- MODIFICADORES -------------

    /**
     * @param nom_e nom de l'expressio a afegir
     * S'ha afegit l'expressio nom_e al conjunt d'autors.
     * En cas de ja existir mostra un missatge d'error.
     */
    public void creaExpressio(String nom_e) {
        conjuntExpressions.add(nom_e);
    }

    /**
     * @param nom_e nom de l'expressió a esborrar
     * Elimina l'expressió nom_e del conjunt d'expressions.
     * En cas de no existir mostra un missatge d'error.
     */
    public void eliminaExpressio(String nom_e) {
        if (existsExpressio(nom_e)) conjuntExpressions.remove(nom_e);
    }

    /**
     * @param nom_e nom de l'expressió a esborrar
     * Elimina l'expressió nom_e del conjunt d'expressions.
     * En cas de no existir mostra un missatge d'error.
     */
    public void modificaExpressio(String nom_e, String nouNom_e) {
        if (existsExpressio(nom_e)){
            conjuntExpressions.remove(nom_e);
            conjuntExpressions.add(nouNom_e);
        }
    }

    /**
     *
     * @return Totes les expressions del conjunt. al final s afegeix: ":fiExp:". S usa per a gestionar l arxiu d expressions
     */
    public TreeSet<String> getExpressions() {
        TreeSet<String> exps = new TreeSet<>();
        for (String expressio:conjuntExpressions) {
            exps.add(expressio + ":fiExp:");
        }
        return exps;
    }

    /**
     *
     * @return Totes les expressions afegides al conjunt. S usa a presentacio
     */
    public String[] getExpressionsBooleanes() {
        String[] expressions = new String[conjuntExpressions.size()];
        int i = 0;
        for (String exp:conjuntExpressions) {
            expressions[i] = exp;
            ++i;
        }
        return expressions;
    }
}