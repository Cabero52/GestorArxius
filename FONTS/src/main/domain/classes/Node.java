package domain.classes;

/**
 * @author Marc Cabero Armengol(marc.armengol.cabero@estudiantat.upc.edu)
 */

public class Node {

    public Node(String value) {
        this.value = value;
        montarArbre();
    }
    private static char OBRIR_PAR = '(';
    private static char TANCAR_PAR = ')';
    private static char PORTA_OR = '|';
    private static char PORTA_AND = '&';
    private static char VALOR_AND = 'y';
    private static char VALOR_OR = 'o';
    private String value;
    private char portaLogica;
    private Node left;
    private Node right;

    /**
     *
     * @return Cert si el node es fulla
     */
    public boolean esFulla() {
        return (left == null & right == null);
    }

    /**
     *
     * @return Node esquerra del node
     */
    public Node getEsq() {
        return left;
    }

    /**
     *
     * @return Node dret del node
     */
    public Node getDre() {
        return right;
    }

    /**
     *
     * @return Valor del node
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @return Valor de la porta lògica del node
     */
    public char getPortaLogica() {
        return portaLogica;
    }

    /**
     *
     * @return Cert si te fill esquerra, fals si no
     */
    public boolean teFillEsquerra() {
        return left != null;
    }

    /**
     *
     * @return Cert si té fill dret, fals si no
     */
    public boolean teFillDret() {
        return right != null;
    }

    /**
     * Monta l arbre a partir del valor del node
     */
    public void montarArbre() {
        int iti = 0;
        int itf = 0;
        while (itf < value.length()) {
            if (value.charAt(itf) == OBRIR_PAR) {
                iti = itf;
                itf = buscaFinalpar(itf);
                int tam = value.length();
                if (iti == 0 & itf == tam) {
                    value = value.replace(value.substring(0,1), "");
                    value = value.replace(value.substring(tam-2,tam-1), "");
                    itf = 0;
                }
            }
            else if (value.charAt(itf) == PORTA_OR) {
                portaLogica = VALOR_OR;
                if (value.charAt(itf-2) == TANCAR_PAR) left = new Node(value.substring(iti+1, itf - 2));
                else left = new Node(value.substring(iti, itf - 1));

                if (value.charAt(itf+2) == OBRIR_PAR) right = new Node(value.substring(itf + 3, value.length()-1));
                else right = new Node(value.substring(itf + 2, value.length()));
            }
            else if (value.charAt(itf) == PORTA_AND) {
                portaLogica = VALOR_AND;
                if (value.charAt(itf-2) == TANCAR_PAR) left = new Node(value.substring(iti+1, itf - 2));
                else left = new Node(value.substring(iti, itf - 1));

                if (value.charAt(itf+2) == OBRIR_PAR) right = new Node(value.substring(itf + 3, value.length()-1));//else+par
                else right = new Node(value.substring(itf + 2, value.length())); // +2 = +1 de la & + 1 de l'espai
            }
            ++itf;
        }
    }

    /**
     *
     * @param itf iterador final
     * @return Posició on es tanca el parèntesis obert a la posició en la que es troba itf al cridar la funció
     */
    private int buscaFinalpar(int itf) {
        boolean trobat = false;
        int parsIntermitjos = -1;
        while (itf < value.length() & !trobat) {
            if (value.charAt(itf) == TANCAR_PAR & parsIntermitjos == 0)  {
                trobat = true;
            }
            else if (value.charAt(itf) == TANCAR_PAR) --parsIntermitjos;
            else if (value.charAt(itf) == OBRIR_PAR) ++parsIntermitjos;
            ++itf;
        }
        return itf;
    }
}
