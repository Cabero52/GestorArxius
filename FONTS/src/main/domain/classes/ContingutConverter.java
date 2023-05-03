package domain.classes;

import domain.classes.exceptions.NullException;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Dídac Hispano Corbera (didac.hispano@estudiantat.upc.edu)
 */

public class ContingutConverter {
    /**
     * Estructura de dades que emmagatzema les paraules del document amb el seu TF
     */
    private TreeMap <String,Double> VecWords = new TreeMap<String,Double>();
    //totes les paraules existens del contingut i el seu TF
    private Integer SizeWords;
    // -------- Constructores --------

    /**
     * Crea un map de paraula, repeticio de cada una d'elles i guarda el nombre total de paraules.
     * @param contingut String on conté totes les paraules del document
     */
    public ContingutConverter(String contingut) throws NullException {
        String[] words = contingut.split("[ \",.;:!?]+");
        SizeWords = words.length;
        if (SizeWords == 0) throw new NullException();
        for (int i = 0; i<SizeWords; i++) {
            Double TF =  1.0/SizeWords;
            if(VecWords.containsKey(words[i])) TF += VecWords.get(words[i]);
            VecWords.put(words[i],TF);
        }

    }

    /**
     * @return el calcul del TF de les paraules
     */
    public Map<String,Double> ConsultaTF(){
        return VecWords;
    }

    /**
     * @return el nombre total de
     */
    public int getSizeWords(){
        return SizeWords;
    }


}