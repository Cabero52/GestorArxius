package domain.classes;

/**
 * @author Marc Cabero Armengol(marc.armengol.cabero@estudiantat.upc.edu)
 */

public class GestorArbre {

    private static char OBRIR_CORCH = '{';
    private static char OBRIR_COMETES = '"';
    private static char PORTA_NOT = '!';
    private static char VALOR_OR = 'o';
    private static char VALOR_AND = 'y';

    /**
     * Constructora per defecte
     */
    public GestorArbre() {}

    /**
     *
     * @param n Node de l'arbre que s'esta gestionant
     * @param contingut Contingut amb el que comparar
     * @return Cert si es compleix la condició del node.
     *         En cas de ser fulla, és cert si contingut conté el valor de n
     *         En cas de tenir un fill, retorna cert si el teu fill et retorna cert
     *         En cas de tenir dos fills retorna cert si els fills compleixen l operacio logica assignada al node n
     */
    public boolean gestionarArbreBoolea(Node n, String contingut) {
        boolean result;
        // Caso base: ser hoja
        if (n.esFulla()) {
            result = gestionarContingut(n.getValue(), contingut);
        }
        else if (n.teFillEsquerra() & !n.teFillDret()) {
            Node esq = n.getEsq();
            result = gestionarArbreBoolea(esq, contingut);
        }
        else if (!n.teFillEsquerra() & n.teFillDret()) {
            Node dre = n.getDre();
            result = gestionarArbreBoolea(dre, contingut);
        }
        else { // te els dos fills
            boolean resultEsq;
            boolean resultDre;
            Node esq = n.getEsq();
            resultEsq = gestionarArbreBoolea(esq, contingut);

            int resultat = comprovarSiTincResultat(resultEsq, n.getPortaLogica());

            if (resultat == 0) result = false;
            else if (resultat == 1) result = true;
            else {
                Node dre = n.getDre();
                resultDre = gestionarArbreBoolea(dre, contingut);

                char portaLogica = n.getPortaLogica();
                if (portaLogica == VALOR_OR) result = resultEsq | resultDre;
                else result = resultEsq & resultDre;
            }
        }
        return result;
    }

    /**
     *
     * @param resultEsq Resultat amb que es necessiten fer les comprovacions
     * @param portaLogica Operador logic assignat
     * @return Retorna -1 si amb la informació que tinc no puc dir el resultat de la porta logica
     *          0 si sí que puc dir el resultat i és fals i 1 si el resultat és cert
     */
    private int comprovarSiTincResultat(boolean resultEsq, char portaLogica) {
        int result;
        if (portaLogica == VALOR_AND & !resultEsq) result = 0;      //0 -> Tinc resultat i es false
        else if (portaLogica == VALOR_OR & resultEsq) result = 1;   //1 -> Tinc resultat i es true
        else result = -1;                                           //-1 -> No tinc resultat
        return result;
    }

    /**
     *
     * @param exp Conjunt de paraules separades per un espai. Subconjunt de l expressio original
     * @param cont Contingut a comprovar si es compleix l expressio
     * @return Retorna cert si conte totes les paraules de exp
     */
    private boolean gestionarCorchetes(String exp, String cont) {
        String[] paraules = exp.split(" ");
        boolean b = true;
        for (int i = 0; i < paraules.length && true; ++i) {
            if (!cont.contains(paraules[i])) b = false;
        }
        return b;
    }

    /**
     *
     * @param expressio Expressio a comprovar
     * @param contingut Contingut on comprovar l expressio
     * @return Retorna cert si el contingut compleix l expressio
     */
    public boolean gestionarContingut(String expressio, String contingut) {
        boolean result;
        if (expressio.charAt(0) == OBRIR_COMETES) {
            result = contingut.contains(expressio.substring(1,expressio.length()-1));
        }
        else if (expressio.charAt(0) == OBRIR_CORCH) {
            result = gestionarCorchetes(expressio.substring(1,expressio.length()-1), contingut);
        }
        else if (expressio.charAt(0) == PORTA_NOT) {
            if (expressio.charAt(1) == OBRIR_CORCH) result = !(gestionarCorchetes(expressio.substring(2,expressio.length()-1), contingut));
            else if (expressio.charAt(1) == OBRIR_COMETES) result = !(contingut.contains(expressio.substring(1,expressio.length()-1)));
            else result = !contingut.contains(expressio.substring(1,expressio.length()));
        }
        else result = contingut.contains(expressio);
        return result;
    }
}
