import domain.classes.Document;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDoc {
    @Test
    /**
     * Objecte de la prova: Test del mètode createDoc(nouAutor,nouTitol,contingut) de la classe Document.
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crean documents amb els parametres introduits
     * Operativa: En aquest test es comprova que es creen els documents correctament
     * Primer es crean documents i a continuacio es comprova l'existencia d'aquests.
     */
    public void TestCreateDocs(){
        Document d1 = new Document("Gustavo Adolfo Bécquer","Volverán las oscuras golondrinas",
                "Las golondrinas no les gusta mi valcon");
        Document d2 = new Document("William Shakespeare","Hamlet","Hamlet es un " +
                "principe de Dinamarca");
        assertEquals("Gustavo Adolfo Bécquer", d1.getAutor());
        assertEquals("William Shakespeare", d2.getAutor());
        assertEquals("Volverán las oscuras golondrinas", d1.getTitol());
        assertEquals("Hamlet", d2.getTitol());
        assertEquals("Las golondrinas no les gusta mi valcon", d1.getCont());
        assertEquals("Hamlet es un principe de Dinamarca", d2.getCont());

    }

    @Test
    /**
     * Objecte de la prova: Test del mètode editarContingut(String) de la classe Document.
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crea un nou contingut i es remplaça amb el que hi havia anteriorment.
     * Operativa: En aquest test es comprova que el contingut es remplaça correctament
     * Primer es crea un document al qual modifiquem el seu contingut
     */
    public void TestEditarContingut(){
        Document d1 = new Document("Albert Einstein","Teoria de la velocitat",
                "Tinc Son i jo tambe jaja");
        d1.editarContingut("la velocitat és relativa!");
        assertEquals("la velocitat és relativa!", d1.getCont());
    }

    @Test
    /**
     * Objecte de la prova: Test del mètode editarAutor(String) de la classe Document.
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crea un nou autor i es remplaça amb el que hi havia anteriorment.
     * Operativa: En aquest test es comprova que el nom d'autor es remplaça correctament
     * Primer es crea un document al qual modifiquem el seu nom d'autor
     */
    public void TestEditarAutor(){
        Document d1 = new Document("Marc Cabero","Teoria de la velocitat",
                "la velocitat és relativa!");
        d1.renameAutor("Albert Einstein");
        assertEquals("Albert Einstein", d1.getAutor());
    }

    @Test
    /**
     * Objecte de la prova: Test del mètode editarTitol(String) de la classe Document.
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Es crea un nou titol i es remplaça amb el que hi havia anteriorment.
     * Operativa: En aquest test es comprova que el titol es remplaça correctament
     * Primer es crea un document al qual modifiquem el seu nom titol
     */
    public void TestEditarTitol(){
        Document d1 = new Document("Albert Einstein","Projecte de Programació",
                "la velocitat és relativa!");
        d1.renameTitol("Projecte de Programacio");
        assertEquals("Projecte de Programacio", d1.getTitol());
    }

}
