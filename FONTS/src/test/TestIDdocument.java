import java.io.*;
import java.util.Map;

import domain.classes.Document;
import domain.classes.Estructura;
import domain.classes.IDdocument;
import org.junit.Test;
import domain.classes.ContingutConverter;
import static org.junit.Assert.*;

public class TestIDdocument {

    @Test
    /**
     * Objecte de la prova: Test de la creadora (IDdocument(nouAutor,nouTitol)) de la classe IDdocument
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crea un objecte IDdocument.
     * Operativa: En aquest test es comprova que es crea correctament un objecte IDdocument amb
     * titol = nouTitol, autor = nouAutor
     * Primer es crea un objecte IDdocument i es comprova que el resultat és el correcte.
     */

    public void TestCreateIDdocument() {
        Document d1 = new Document("Gustavo Adolfo Bécquer","Volverán las oscuras golondrinas"
                ,"Tinc Son i jo tambe jaja");
        Document d2 = new Document("William Shakespeare","Hamlet",
                "Vull acabar per anar a la dutxa otako");
        IDdocument id1 = new IDdocument(d1.getAutor(), d1.getTitol());
        IDdocument id2 = new IDdocument(d2.getAutor(), d2.getTitol());
        assertEquals(d1.getAutor(),id1.first());
        assertEquals(d2.getAutor(),id2.first());
        assertEquals(d1.getTitol(),id1.second());
        assertEquals(d2.getTitol(),id2.second());
    }

    @Test
    /**
     * Objecte de la prova: Test del mètode modificarAutor(nouAutor) i del mètode modificarTitol
     * (String nou_titol) de la classe IDdocument
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es modifica un objecte IDdocument.
     * Operativa: En aquest test es comprova que es modifica correctament un objecte IDdocument tant si
     * es modifica el seu titol com si es modifica el seu autor
     * Primer es crea un Objecte IDdocument i després es criden les funcions modificarAutor i
     * modificarTitol, finalment es comprova que s'hagin modificat els valors
     */
    public void TesModificaIDdocument() {
        Document d1 = new Document("Gustavo Adolfo Bécquer",
                "Volverán las oscuras golondrinas","Tinc Son i jo tambe jaja");
        Document d2 = new Document("William Shakespeare","Hamlet","" +
                "Vull acabar per anar a la dutxa otako");
        IDdocument id1 = new IDdocument(d1.getAutor(), d1.getTitol());
        IDdocument id2 = new IDdocument(d2.getAutor(), d2.getTitol());
        id1.modificarAutor("Marc");
        id2.modificarTitol("Romeo");
        assertEquals("Marc",id1.first());
        assertEquals(d1.getTitol(),id1.second());
        assertEquals(d2.getAutor(),id2.first());
        assertEquals("Romeo",id2.second());
    }

    @Test
    /**
     * Objecte de la prova: Test del mètode toCompare(IDdocument idd) de la classe Document
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es fa una comparació de dos IDdocument.
     * Operativa: En aquest test es comprova que es comparin correctament dos IDdocument.
     * Primer es creen dos objectes IDdocument, es crida al mètode toCompare i retorna el valor de la comparació
     */
    public void TestComparar(){
        IDdocument id1 = new IDdocument("Autor", "Titol");
        IDdocument id2 = new IDdocument("Autor", "Cara");
        assertEquals(17,id1.compareTo(id2));
        assertEquals(-17,id2.compareTo(id1));
        IDdocument id3 = new IDdocument("Autora", "Titol");
        IDdocument id4 = new IDdocument("Autor", "Cara");
        assertEquals(1,id3.compareTo(id4));
        IDdocument id5 = new IDdocument("Autor", "Cara");
        assertEquals(0,id5.compareTo(id4));
    }

} /*
    si a < b -> a.compareTo(b) resultat negatiu
    si a = b -> a.compareTo(b) resultat 0
    si a > b -> a.compareTo(b) resultat positiu
*/
