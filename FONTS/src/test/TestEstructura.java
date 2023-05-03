import domain.classes.Estructura;

import org.junit.Test;
import domain.classes.Document;
import domain.classes.IDdocument;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TestEstructura {
    @Test
    /**
     * Objecte de la prova: Test del mètode creadora de la classe Estructura
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crea un nou objecte de Estructura.
     * Operativa: En aquest test es comprova que es crea el objecte correctament.
     * Primer es crea un nou Objecte i es comprova la seva existencia
     */
    public void TestCreateEstructura(){
        Estructura e = new Estructura();
        assertEquals(0,e.getTotalWords());
    }

    @Test
    /**
     * Objecte de la prova: Test del mètode Afegir de la classe Estructura
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es comprova la creacio correcte de la informacio
     * Operativa: En aquest test es comprova que es genera la informacio correctament
     * Es creen 3 docuemnts i mentre s'afageixen es realitzen assertEquals per comprovar
     * que el satributs s'actualitzen correctament
     */
    public void TestAfegirBorrarDoc(){
        Estructura e = new Estructura();
        Document d1 = new Document("Alejandra","Carnet","" +
                "Preguntas de teoria para el carnet de conducir para que no cree un accidente");
        Document d2 = new Document("Marc","Moto",
                "El marc te el carnet de conduir i de moment no ha tingut cap accident");
        Document d3 = new Document("Didac","Pokemon","" +
                "El didac encara no ha preguntat pel pokemon del dia");

        e.NewDoc(d1);
        assertEquals(14,e.getTotalWords());
        e.NewDoc(d2);
        assertEquals(29,e.getTotalWords());
        e.NewDoc(d3);
        assertEquals(39,e.getTotalWords());
        e.DeleteDoc(d3);
        assertEquals(29,e.getTotalWords());

    }

    @Test
    /**
     * Objecte de la prova: Test del mètode compararDocs(Doc, k) de la classe Estructura
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crean documents i es comprova que es llistin els documents semblants
     * Operativa: En aquest test es comprova que donat un document de referencia, es llisten els k documents
     * mes semblants
     * Primer es crean els documents, i a continuacio es pren un de referencia per a llsitar als mes semblants
     * a aquest.
     */
    public void TestDocumentsSimilars(){
        Estructura e = new Estructura();

        Document d1 = new Document("Alejandra","Accidente de tráfico",
                "Preguntas de teoria para el carnet de conducir para que no cree un accidente");
        Document d2 = new Document("Marc","Moto","El marc te el " +
                "carnet de conduir i de moment no ha tingut cap accident");
        Document d3 = new Document("Didac","Pokemon","El didac no sap el pokemon del dia");
        e.NewDoc(d1);
        e.NewDoc(d2);
        e.NewDoc(d3);
        Map<IDdocument,Double> r= e.compararDocs(d1,1);
        Set<IDdocument> archivosSim = new HashSet<IDdocument>();

        for(Map.Entry<IDdocument, Double> entry : r.entrySet()) {
            archivosSim.add(entry.getKey());
            assertEquals("Marc",entry.getKey().first());
            assertEquals("Moto",entry.getKey().second());
        }

        Document d4 = new Document("Elias","La vida 1","la vida 1 es la millor vida");
        Document d5 = new Document("Marc","La vida 2","la vida 3 no es la millor vida");
        e.NewDoc(d4);
        e.NewDoc(d5);

        r= e.compararDocs(d5,1);
        archivosSim = new HashSet<IDdocument>();

        for(Map.Entry<IDdocument, Double> entry : r.entrySet()) {
            archivosSim.add(entry.getKey());
            assertEquals("Elias",entry.getKey().first());
            assertEquals("La vida 1",entry.getKey().second());
        }

    }
}
