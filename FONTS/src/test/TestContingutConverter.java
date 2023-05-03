import domain.classes.ContingutConverter;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;

public class TestContingutConverter {
    @Test
    /**
     * Objecte de la prova: Test del mètode ContingutConverter(String contingut) de la classe Contingut Converter
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es converteixi un contingut correctament en l'encapsulament indicat
     * Operativa: En aquest test es comprova que s'encapsula correctament la informacio d'un contingut
     * Primer es crea un contingut juntament amb una instancia de la classe ContingutConverter i es fa
     * l'encapsulament de la informacio correctament
     */
    public void TestCreateContingutConverter(){
        try {
            String Contingut = "mi amiga marina recogió del mar una alga marina para hacer sushi marina";
            ContingutConverter c1 = new ContingutConverter(Contingut);
            Map<String, Double> mapC1 = c1.ConsultaTF();
            int numTotal = c1.getSizeWords();
            assertEquals(mapC1, c1.ConsultaTF());
            assertEquals(13, numTotal);
        }catch (Exception NullException) {}
    }
}
