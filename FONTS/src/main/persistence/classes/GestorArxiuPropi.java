package persistence.classes;
import java.io.*;

/**
 * @author Marc Cabero Armengol(marc.armengol.cabero@estudiantat.upc.edu)
 */

public class GestorArxiuPropi {

    private String pathDoc = "../../Documents/arxiu.prop";


    public GestorArxiuPropi(){
        File doc = new File(pathDoc);
    }
    /**
     *
     * @param info string amb tota la informacio de un document(autor,titol,contingut)
     */
    public void afegirDocument(String info){
        try {
            FileWriter escritor = new FileWriter(pathDoc, true);
            escritor.write(info);
            escritor.close();
        } catch (IOException e) {
            System.out.println("afegirDocument del gestor d'arxiuPropi ens ha petat");
        }
    }

    /**
     * Esborra el contingut d arxiu propi
     */
    public void buidarArxiuPropi() {
        try {
            FileWriter escritor = new FileWriter(pathDoc, false);
            escritor.write("");
        } catch (IOException e) {
            System.out.println("buidarArxiuPropi ens ha petat");
        }
    }

    /**
     *
     * @return totsDocs tots els documents que eren dintre d'arxiu propi els retornem com un array de Strings
     */
    public String[] getDocuments() {
        String[] totsDocs = null;
        try {
            FileReader lectorArxiu = new FileReader(pathDoc);
            BufferedReader entrada = new BufferedReader(lectorArxiu);
            String lectura = entrada.readLine();
            String documents = "";
            while (lectura != null) {
                documents += lectura;
                lectura = entrada.readLine();
            }
            totsDocs = documents.split(":fiDocu:");
        } catch (FileNotFoundException e) {
            System.out.println("L'arxiu no s'ha trobat");
        } catch (IOException ioe) {
            System.out.println("Ha saltat excepció a getDocuments de gestorArxiu");
        }
        return totsDocs;
    }


}