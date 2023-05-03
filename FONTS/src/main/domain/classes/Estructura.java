package domain.classes;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * @author Dídac Hispano Corbera (didac.hispano@estudiantat.upc.edu)
 */

public class Estructura {
    /**
     * Estructura de dades que emmagatzema les paraules i el nombre de vegades que surt en tot el conjunt
     */
    private Map<String, Integer> dicc;

    /**
     * Estructura de dades que emmagatzema el idDocument i els calculs necessaris per l'algorisme
     */
    private Map<IDdocument, ContingutConverter> arxiuConvert = new TreeMap<IDdocument,ContingutConverter>();
    private int totalWords;

    public Estructura(){
        dicc = new TreeMap<String, Integer>();
        totalWords = 0;
    }

    /**
     * Funció consultora per retornar el numero de paraules totals que hi ha en tots els documents
     * @return totalWords int paraules totals del sistema
     */
    public int getTotalWords() {
        return totalWords;
    }

    /**
     * Passats dos arrays de Doubles, on vectfa es el vector del documentA, amb el que es volen comparar,
     * i un vectfb d'un altre document
     * on només queda aplicar el cosino per veure com de semblants son els dos documents
     *cos(a,b) = sumatori total/(sqrt(vectfa^2)*sqrt(vectfb^2)).
     * @param vectf vector de tfs d'un document
     * @return sumvec double suma de tot el vector de tfs
     */
    private Double suma(Double[] vectf) {
        double sumvec=0.0;
        for(int i = 0; i<dicc.size();i++){
            sumvec +=  vectf[i]*vectf[i];
        }
        return sumvec;
    }

    /**
     * Calcula el coseno passats la suma de cada array i es calcula el total
     * aplicar el cosino per veure com de semblants son els dos documents
     * cos(a,b) = sumatori total/(sqrt(vectfa^2)*sqrt(vectfb^2)).
     * @param sumA es la suma total de tots els tfs^2 del documentA
     * @param sumB es la suma total de tots els tfs^2 del documentB
     * @param vecA vector de tfs del documentA
     * @param vecB vector de tfs del documentB
     * @return total/(Math.sqrt(sumA)*Math.sqrt(sumB)) divisio equivalent a aplicar el cosino
     */
    private Double cosSuma(Double sumA, Double sumB, Double[] vecA, Double[] vecB){
        double total=0.0;
        for(int i = 0; i<dicc.size();i++){
            total = total + vecA[i] * vecB[i];
        }
        return total/(Math.sqrt(sumA)*Math.sqrt(sumB));
    }

    /**
     * Es vol afegir un nou document a la estructura, per poder comparar documents similars.
     * Al afegir-se es suma les paraules totals, es crea l'identificador del document i es converteix
     * el contingut en un map de paraules i tf.
     * Per últim s'actualitza diccionari.
     * @param document document que volem afegir a l'estructura
     */
    public void NewDoc(Document document) {
        try {
            ContingutConverter cc = new ContingutConverter(document.getCont());
            IDdocument id = new IDdocument(document.getAutor(), document.getTitol());
            arxiuConvert.put(id, cc);
            Map<String, Double> new_words = cc.ConsultaTF();
            totalWords += cc.getSizeWords();
            for (String word : new_words.keySet()) {
                if (!dicc.containsKey(word)) dicc.put(word, 1);
                else dicc.put(word, dicc.get(word) + 1);
            }
        } catch (Exception NullException) {}
    }



    /**
     * Al contrari que la funció anterior, s'ha de borrar un document de la estructura
     * Primer es borra el numero total de paraules
     * Es borra les paraules del dicionari que contenia el contingut
     * Per ultim s'elimina del map arxiuConvert
     * @param document document que volem eliminar perquè ja no és disponible a l'estructura
     */
    public void DeleteDoc(Document document) {
        IDdocument id = new IDdocument(document.getAutor(),document.getTitol());
        ContingutConverter cc = arxiuConvert.get(id);
        Map<String,Double> new_words = cc.ConsultaTF();
        totalWords -= cc.getSizeWords();
        for (String word : new_words.keySet()) {
            dicc.put(word, dicc.get(word) -1);
            if (dicc.get(word) == 0) dicc.remove(word);
        }
        arxiuConvert.remove(id);
    }

    /**
     * Amb el Identificador del document, agafem el seu map d'arxiuConvert
     * ens declarem un arraay del size del diccionari, i anem posant omplint de 0 totes les paraules que no apareixin,
     * les que si apareixen apliquem el calcul de TF*(1+log(paraules totals/numero de documents))
     * @param id identificador del document que volem calcular els seus tfs
     * @return vectf vector de tfs del document identificst per id
     */
    private Double[] calcularArray(IDdocument id) {
        Map<String,Double>TF = arxiuConvert.get(id).ConsultaTF();
        Double[] vectf = new Double[dicc.size()];
        int pos = 0;
        Iterator<Map.Entry<String, Double>> it = TF.entrySet().iterator();
        Map.Entry<String,Double> word = it.next();
        for(Map.Entry<String,Integer> entry : dicc.entrySet()){

            if(!word.getKey().equals(entry.getKey())){
                vectf[pos] = 0.0;
            }
            else{//significa que son iguals
                double tf = word.getValue();
                vectf[pos] = tf+Math.log(arxiuConvert.size()/ entry.getValue())*tf;
                if(it.hasNext()) word = it.next();
            }
            ++pos;
        }
        return vectf;
    }

    /**
     * Busca el Value minim del map. Recorre el map i retorna un double
     * @param arxiu conte un calcul intermig del tf-idf de cada arxiu
     * @return min double minim del map arxiu
     */
    private double calcularMinim(Map<IDdocument,Double> arxiu) {
        double min = 1.0;
        for(Map.Entry<IDdocument, Double> entry : arxiu.entrySet()) {
            if (entry.getValue()<min) min = entry.getValue();
        }
        return min;
    }

    /**
     * per parametre es passa, un identificador del document que volem afegir, la seva semblança
     * el map de documents semblants, i la sembalnça minima del map.
     * Busquem quin és el primer en tenir aquesta semblança mínima i la canviem per la nova
     * @param idB identificador del document que volem afegir al map
     * @param semb semblança entre els arxius
     * @param arxiu conte el cosinus del tfidf de cada document
     * @param minim conte el valor minim del map arxiu
     */
    private void changeMinimPerNou(IDdocument idB, double semb, Map<IDdocument, Double> arxiu, double minim) {
        Iterator<Map.Entry<IDdocument,Double>> it = arxiu.entrySet().iterator();
        boolean canviat = false;

        while (!canviat & it.hasNext()) {
            Map.Entry<IDdocument, Double> entry = it.next();
            if (entry.getValue() == minim) {
                it.remove();
                arxiu.put(idB, semb);
                canviat = true;
            }
        }
    }

    /**
     * Per parametre passen el document amb el que es vol comparar, i el número de documents semblants que vol llistar
     * @param documentA Document amb el que volem comparar
     * @param k Numero de documents que es vol buscar
     * @return un Map d'indentificadors de document, amb la seva semblança
     */
    public Map<IDdocument,Double> compararDocs (Document documentA, int k) {
        IDdocument idA = new IDdocument(documentA.getAutor(),documentA.getTitol());

        TreeMap<IDdocument,Double> result = new TreeMap<>() ;
        double semblantMinim = 0.0;
        Double[] vecA= {0.0};
        vecA = calcularArray(idA);
        Double sumaA = suma(vecA);
        int cont=0;

        for(Map.Entry<IDdocument, ContingutConverter> entry : arxiuConvert.entrySet()){
            IDdocument idB = entry.getKey();
            double semblant = 0.0;
            if((idB.first() != idA.first()) &(idB.second() != idA.second())) {
                Double[] vecB= {0.0};
                if (cont < k) {
                    vecB = calcularArray(idB);
                    Double sumaB = suma(vecB);
                    semblant = cosSuma(sumaA, sumaB, vecA, vecB);
                    result.put(idB, semblant);
                    ++cont;
                    if (cont == k) semblantMinim = calcularMinim(result);
                }
                else {
                    vecB = calcularArray(idB);
                    Double sumaB = suma(vecB);
                    semblant = cosSuma(sumaA, sumaB, vecA, vecB);
                    if (semblant > semblantMinim) {
                        changeMinimPerNou(idB, semblant, result, semblantMinim);
                        semblantMinim = calcularMinim(result);
                    }
                }
            }
        }
        return result;
    }

}