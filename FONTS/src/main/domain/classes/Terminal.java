package domain.classes;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.SortedSet;


/**
 @author Jaume Alos Cuadrado (jaume.alos.cuadrado@estudiantat.upc.edu)
 */

public class Terminal {

    //------------- CONSTRUCTORA ------------
    public Terminal() {

    }
    //------------- CONSULTORES -------------
    /**
     * @return El contingut que s'ha llegit a traves de la Terminal
     */
    public String getContingutTerminal() {
        System.out.println(" -Per finalitzar, prem la tecla Enter i a continuacio introdueix:':quit'");
        String cont = "";
        Scanner entrada = new Scanner(System.in);
        String frase = entrada.nextLine();
        while(!frase.contains(":quit")) {
            cont += frase + "\r\n";
            frase = entrada.nextLine();
        }
        return cont;
    }

    /**
     * @return la informacio que s'ha llegit a traves de la Terminal
     */
    public String getString() {
        Scanner entrada = new Scanner(System.in);
        String lectura = entrada.nextLine();
        return lectura;
    }

    /**
     * @return el nombre enter que s'ha llegit a traves de la Terminal
     */
    public Integer getInteger() {
        Scanner entrada = new Scanner(System.in);
        Integer k = Integer.valueOf(entrada.nextLine());
        return k;
    }

    //------------- IMPRIMIR -------------


    /**
     * Mostra per Terminal la infomracio indicada com a parametre
     * @param frase informacio que es vol mostrar
     */
    public void imprimirString(String frase){
        System.out.println(frase);
    }
}
