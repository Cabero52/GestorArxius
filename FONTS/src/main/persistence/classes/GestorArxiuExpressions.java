package persistence.classes;

import java.io.*;
/**
 * @author Dídac Hispano Corbera(didac.hispano@estudiantat.upc.edu)
 */
public class GestorArxiuExpressions {
    private String pathDoc = "../../Documents/expressions.prop";

    /**
     * Creadora per defecte
     */
    public GestorArxiuExpressions(){
        File doc = new File(pathDoc);
    }

    /**
     *
     * @param expressio Expressio a afegir al document d expressions
     */
    public void afegirExpressio(String expressio){
        try {
            FileWriter escritor = new FileWriter(pathDoc, true);
            escritor.write(expressio);
            escritor.close();
        } catch (IOException e) {
            System.out.println("afegirExpressio del gestor d'expressions ens ha petat");
        }
    }

    /**
     * Esborra el contingut de l arxiu d expressions
     */
    public void buidarArxiuExpressio() {
        try {
            FileWriter escritor = new FileWriter(pathDoc, false);
            escritor.write("");
        } catch (IOException e) {
            System.out.println("buidarArxiuExpressio ens ha petat"); ///////////////////////////////////////////////////////
        }
    }

    /**
     *
     * @return Conjunt amb totes les expressions
     */
    public String[] getExpressions() {
        String[] totesExps = null;
        try {
            FileReader lectorArxiu = new FileReader(pathDoc);
            BufferedReader entrada = new BufferedReader(lectorArxiu);
            String lectura = entrada.readLine();
            String exps = "";
            while (lectura != null) {
                exps += lectura;
                lectura = entrada.readLine();
            }
            totesExps = exps.split(":fiExp:");
        } catch (FileNotFoundException e) {
            System.out.println("L'expressio no s'ha trobat");
        } catch (IOException ioe) {
            System.out.println("Ha saltat excepció a getExpressions de gestorArxiuExpressions");
        }
        return totesExps;
    }
}
