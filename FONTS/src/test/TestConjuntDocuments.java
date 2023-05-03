import domain.classes.ConjuntDocuments;

import domain.classes.IDdocument;
import org.junit.Test;
import domain.classes.Estructura;
import domain.classes.ConjuntAutors;

import java.util.TreeSet;

import static org.junit.Assert.*;

public class TestConjuntDocuments {

    @Test
    /**
     * Objecte de la prova: Test del metode createDocument(autor,titol,contingut)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que ens permet comprovar si s'han creat els
     * documents especificats mitjançant els parametres necessaris
     * Operativa: En aquest test es comprova que es crean correctament els documents
     * Primer es crean 2 documents, i es comprova que el numero de documents va en relacio els que hem creat.
     */

    public void testCreateDocument() {

        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors,structure);
        assertEquals(0,CjtDocuments.getNumDocuments());

        String autor = "Marc";
        String titol = "Pokemon del dia";
        String contingut = "El pokemon del dia es Vileplume, un pokemon de 1a generación tipo planta veneno";
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix) {}
        assertTrue(CjtDocuments.existDocument(autor, titol));

        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix) {
    }

        String autor2 = "Quevedo";
        String titol2 = "Poesia romantica";
        String contingut2 = "Las luces que me iluminan cada mañana me tienen en una nube";
        try {
            CjtDocuments.createDocument(autor2, titol2, contingut2);
        } catch (Exception DocumentJaExisteix) {}

        assertTrue(CjtDocuments.existDocument(autor2, titol2));
        assertEquals(2,CjtDocuments.getNumDocuments());
    }

    @Test
    /**
     * Objecte de la prova: Test del metode removeDocumentDefinitivament(autor,titol)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que elimina un document del nostre conjunt
     * Operativa: En aquest test es comprova que s'elimina correctament un document especificat
     * Primer es crea un document i es comrpova si encara existeix un cop eliminat
     */
    public void TestRemoveDocumentDefinitivament() {
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors,structure);

        String autor = "Jorge";
        String titol = "Jaimito va a classe";
        String contingut = "Hoy es el primer dia de classe y Jaimito esta muy ilusionado. Tiene muchas ganas de ir" +
                "pero todo es cuestion de ver cuanto tiempo aguantara. Sera Jaimito capaz de terminar el curso?";
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}
        String autor2 = "Jaumito";
        String titol2 = "Chistes malos para pequeños";
        String contingut2 = "La mayor exportación de Australia son los boomerangs. También son la mayor importación.";
        try {
            CjtDocuments.createDocument(autor2, titol2, contingut2);
        } catch (Exception DocumentJaExisteix){}
        int tamany = CjtDocuments.getNumDocuments();

        try {
            CjtDocuments.removeDocumentDefinitivament(autor, titol);
        } catch (Exception DocumentNoExisteix) {}

        assertFalse(CjtDocuments.existDocument(autor,titol));
        assertTrue(CjtDocuments.existDocument(autor2,titol2));
        assertEquals(tamany-1,CjtDocuments.getNumDocuments());
    }

    @Test
    /**
     * Objecte de la prova: Test del metode editarContingut(autor,titol,contingut)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que edita el contingut d'un document existent
     * Operativa: En aquest test es comprova que el contingut d'un document es modificat pel nou contingut
     * Primer es crea un document amb els parametres necessaris, i despres es modifica el contingut
     */
    public void TestEditarContingut() {
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors,structure);

        String autor = "Marc";
        String titol = "Pokemon del dia";
        String contingut = "El pokemon del dia es Vileplume, un pokemon de 1a generación tipo planta veneno";

        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}
        assertEquals(contingut,CjtDocuments.findDocument(autor,titol).getCont());

        String contingut2 = "El pokemon del dia del 9 de novembre és Gloom";
        
        try {
            CjtDocuments.editarContingut(autor, titol, contingut2);
        } catch (Exception DocumentNoExisteix) {}
        
        assertNotEquals(contingut,CjtDocuments.findDocument(autor,titol).getCont());
        assertEquals(contingut2,CjtDocuments.findDocument(autor,titol).getCont());
    }

    @Test
    /**
     * Objecte de la prova: Test del metode editarAutor(autor,titol,contingut)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que edita l'autor d'un document existent
     * Operativa: En aquest test es comprova que l'autor d'un document es sel nou autor indicat
     * Primer es crea un document amb els parametres necessaris, i despres es modifica l'autor
     */
    public void TestRenameAutor() {
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors,structure);

        String autor = "Marc";
        String titol = "Pokemon del dia";
        String contingut = "El pokemon del dia es Vileplume, un pokemon de 1a generación tipo planta veneno";
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}
        assertEquals(CjtDocuments.findDocument(autor,titol).getAutor(),autor);

        String autor2 = "Jaume";
        try {
            CjtDocuments.renameAutor(autor, titol, autor2);
        } catch (Exception NoExisteixException) {}
        
        assertNotEquals(autor,CjtDocuments.findDocument(autor2,titol).getAutor());
        assertEquals(autor2,CjtDocuments.findDocument(autor2,titol).getAutor());
    }

    @Test
    /**
     * Objecte de la prova: Test del metode renameTitol(autor,titol,contingut)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que edita l'autor d'un document existent
     * Operativa: En aquest test es comprova que el titol d'un document es el nou titol indicat
     * Primer es crea un document amb els parametres necessaris, i despres es modifica el titol
     */
    public void TestRenameTitol() {
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors,structure);

        String autor = "Albert";
        String titol = "Poesia de los 90's";
        String contingut = "La influencia de la poesia podia apreciarse en los espectaculos que se realizaban en " +
                "las terrazas de las casas mas lujosas de la ciudad, ahi solian reunirse los autores mas" +
                "influyentes para tratar algunos temas como la innovacion metrica o la busqueda de nuevas fuentes" +
                "de inspiracion";
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}
        assertEquals(titol,CjtDocuments.findDocument(autor,titol).getTitol());

        String titol2 = "Poesia de los 80's";
        
        try {
            CjtDocuments.renameTitol(autor, titol, titol2);
        } catch (Exception DocumentNoExisteix) {}
        
        assertNotEquals(titol,CjtDocuments.findDocument(autor,titol2).getTitol());
        assertEquals(titol2,CjtDocuments.findDocument(autor,titol2).getTitol());
    }

    @Test
    /**
     * Objecte de la prova: Test del metode listDocumentPerTitolAutor(autor,titol)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que comprova  si el contingut del document llistat es correcte
     * Operativa: En aquest test es comprova que el document especificat sigui llistat correctament
     * Primer es creen dos documents, que posteriorment un sera llistat comprovant que es faci correctament.
     */
    public void TestlistDocumentPerTitolAutor(){
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors,structure);

        String autor = "Marc";
        String titol = "Pokemon del dia";
        String contingut = "El pokemon del dia es Vileplume, un pokemon de 1a generación tipo planta veneno";
        
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}
        
        String autor2 = "Albert";
        String titol2 = "Poesia de los 90's";
        String contingut2 = "La influencia de la poesia podia apreciarse en los espectaculos que se realizaban en " +
                "las terrazas de las casas mas lujosas de la ciudad, ahi solian reunirse los autores mas" +
                "influyentes para tratar algunos temSas como la innovacion metrica o la busqueda de nuevas fuentes" +
                "de inspiracion";
        try {
            CjtDocuments.createDocument(autor2, titol2, contingut2);
        } catch (Exception DocumentJaExisteix){}

        try {
            assertEquals(contingut, CjtDocuments.llistarDocumentPerTitolAutor(autor, titol));
        } catch (Exception DocumentNoExisteix) {}

        try {
            assertEquals(contingut2,CjtDocuments.llistarDocumentPerTitolAutor(autor2,titol2));
        } catch (Exception DocumentNoExisteix) {}
    }

    @Test
    /**
     * Objecte de la prova: Test del metode listTitolsAutor(autor)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que comprova si es llisten tots els documents d'un autor
     * Operativa: En aquest test es comprova que els documents llistats d'un autor s'incloguin tots
     * Primer es creen diferents documents amb autors diferents i despres es llisten els documents
     * d'un mateix autor
     */
    public void TestlistTitolsAutor(){
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors, structure);

        String autor = "Marc";
        String titol = "Pokemon del dia";
        String contingut = "El pokemon del dia es Vileplume, un pokemon de 1a generación tipo planta veneno";
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}

        String autor2 = "Gongora";
        String titol2 = "La poesia es un arte";
        String contingut2 = "Gongora, autor muy reconocido del panorama poetico español, participo en nuemerosos" +
                "encuentros literarios donde pudo expresar todo aquello que creia necessario";
        try {
            CjtDocuments.createDocument(autor2, titol2, contingut2);
        } catch (Exception DocumentJaExisteix){}

        String autor3 = "Gongora";
        String titol3 = "El arte de la poesia";
        String contingut3 = "Pocas personas pueden expressar todo aquello que he logrado plasmar en mis obras";
        try {
            CjtDocuments.createDocument(autor3, titol3, contingut3);
        } catch (Exception DocumentJaExisteix){}
        String[] expected = {titol2, titol3};

        //assertEquals(titols,CjtDocuments.llistarTitolsAutor("Gongora", "a"));
        String[] result = CjtDocuments.llistarTitolsAutor("Gongora", "d");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }

    }

    @Test
    /**
     * Objecte de la prova: Test del metode listDocumentsPerExpressio(expressio)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que comprova si es llisten els documents que compleixen una
     * expressio
     * Operativa: En aquest test es comprova que els documents llistats compleixin una expressio
     * Primer es creen diferents documents i posteriorment es comprova que compleixen la expressio epr ser llistats
     */
    public void TestlistDocumentsPerExpressio(){
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors, structure);

        String autor = "Marc";
        String titol = "Pokemon del dia";
        IDdocument id = new IDdocument(autor,titol);
        String contingut = "El pokemon del dia es Vileplume, un pokemon de 1a generación tipo planta veneno";
        try {
            CjtDocuments.createDocument(autor, titol, contingut);
        } catch (Exception DocumentJaExisteix){}

        String expressio = " {dia soleado} & \"tipo venenoso\" | !planta "; //No compleix

        try {
            assertFalse(CjtDocuments.llistarDocumentsPerExpressio(autor, titol, expressio ));
        }catch(Exception NoExisteixException) {}

        expressio = " {dia pokemon} | (\"tipo planta\" | !veneno) "; //Compleix

        assertTrue(CjtDocuments.llistarDocumentsPerExpressio(autor,titol,expressio));
    }

    @Test
    /**
     * Objecte de la prova: Test del metode listDocumentsSemblants(document, num)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Estratègia caixa gris. Metode que comprova si es llisten els documents semblants correctament
     * Operativa: En aquest test es comprova que els documents llistats siguin semblants
     * Primer es creen diferents documents i es llisten els documents mes semblants al especificat
     */
    public void TestllistarDocsemblants(){
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors, structure);

        String autor1 = "Marc";
        String titol1 = "Domini";
        String contingut1 = "Fixing ordre de parametres de funcions de domini";
        try {
            CjtDocuments.createDocument(autor1, titol1, contingut1);
        } catch (Exception DocumentJaExisteix){}

        String autor2 = "Dídac";
        String titol2 = "Prop";
        String contingut2 = "No he pogut trobar el motiu de perque domini no funciona, suposo que son el ordre de parametres de funcions";
        try {
            CjtDocuments.createDocument(autor2, titol2, contingut2);
        } catch (Exception DocumentJaExisteix){}

        String autor3 = "Elias";
        String titol3 = "Gitlab";
        String contingut3 = "Avui he fet push al GitLab amb l'historial de Github";
        try {
            CjtDocuments.createDocument(autor3, titol3, contingut3);
        } catch (Exception DocumentJaExisteix){}

        String autor4 = "Jaume";
        String titol4 = "Github";
        String contingut4 = "Ultimamente he hecho muschos push de Github sobre las funciones de domini";
        try {
            CjtDocuments.createDocument(autor4, titol4, contingut4);
        } catch (Exception DocumentJaExisteix){}

        IDdocument id1 = new IDdocument(autor1,titol1);
        IDdocument id2 = new IDdocument(autor2,titol2);
        IDdocument id3 = new IDdocument(autor3,titol3);
        IDdocument id4 = new IDdocument(autor4,titol4);


        IDdocument[] result = CjtDocuments.llistarDocsemblants(autor1, titol1,1, "a");
        IDdocument[] expected = {id2};
        assertEquals(expected[0].first(), result[0].first());
        assertEquals(expected[0].second(), result[0].second());

        result = CjtDocuments.llistarDocsemblants(autor1, titol1,2, "a");
        IDdocument[] expected2 = {id2, id4};
        for (int i = 0; i < 2; i++) { // k = 2
            assertEquals(expected2[i].first(), result[i].first());
            assertEquals(expected2[i].second(), result[i].second());
        }

        result = CjtDocuments.llistarDocsemblants(autor3, titol3,3, "a");
        IDdocument[] expected3 = {id2, id4, id1};
        for (int i = 0; i < 3; i++) {
            assertEquals(expected3[i].first(), result[i].first());
            assertEquals(expected3[i].second(), result[i].second());
        }
    }

    @Test
    /**
     * Objecte de la prova: Test del metode llistarTotsDocs(ordre)
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: Mètode que comprova si es llisten els documents.
     * Operativa: En aquest test es comprova que els documents es llistin.
     * Primer es creen diferents documents i es llisten.
     */
    public void testLlistarTotsDocs() {
        ConjuntAutors Cjt_Autors = new ConjuntAutors();
        Estructura structure = new Estructura();
        ConjuntDocuments CjtDocuments = new ConjuntDocuments(Cjt_Autors, structure);

        String autor1 = "Marc";
        String titol1 = "Domini";
        String contingut1 = "Fixing ordre de parametres de funcions de domini";
        try {
            CjtDocuments.createDocument(autor1, titol1, contingut1);
        } catch (Exception DocumentJaExisteix){}

        String autor2 = "Dídac";
        String titol2 = "Prop";
        String contingut2 = "No he pogut trobar el motiu de perque domini no funciona, suposo que son el ordre de parametres de funcions";
        try {
            CjtDocuments.createDocument(autor2, titol2, contingut2);
        } catch (Exception DocumentJaExisteix){}

        String autor3 = "Elias";
        String titol3 = "Gitlab";
        String contingut3 = "Avui he fet push al GitLab amb l'historial de Github";
        try {
            CjtDocuments.createDocument(autor3, titol3, contingut3);
        } catch (Exception DocumentJaExisteix){}

        IDdocument id1 = new IDdocument(autor1,titol1);
        IDdocument id2 = new IDdocument(autor2,titol2);
        IDdocument id3 = new IDdocument(autor3,titol3);

        IDdocument[] expected = {id2, id3, id1};
        IDdocument[] result = CjtDocuments.llistarTotsDocs("a");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].first(), result[i].first());
            assertEquals(expected[i].second(), result[i].second());
        }
    }
}
