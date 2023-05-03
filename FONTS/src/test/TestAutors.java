import domain.classes.ConjuntAutors;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.TreeSet;
import java.util.Set;

public class TestAutors {

    /**
     * Objecte de la prova: Test del mètode addAutor(String nom_a) de la classe ConjuntAutors
     * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
     * Valors estudiats: S'afegeix un autor del conjunt d'autors.
     * Operativa: En aquest test es comprova que s'afegeix correctament un autor al ConjuntAutors
     * Primer es crea un objecte ConjuntAutors i se li afegeix un autor, després comprovem que el
     * s'hagi afegit aquest autor al conjunt.
     */
     @Test
     public void TestAdd_Autor() {
        ConjuntAutors ca = new ConjuntAutors();
        ca.addAutor("Shakespear");
        String s = "Shakespear";
        assertTrue(ca.existsAutor(s));
     }

     /**
      * Objecte de la prova: Test del mètode removeAutor(String nom_a) de la classe ConjuntAutors
      * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
      * Valors estudiats: S'esborra un autor del conjunt d'autors.
      * Operativa: En aquest test es comprova que s'esborra correctament un autor del ConjuntAutors
      * Primer es crea un objecte ConjuntAutors i se li afegeix un autor. Es crida a la funció encarregada
      * d'esborrar l'autor i es comprova que el resultat sigui el correcte.
      */
     @Test
     public void TestRemoveAutor() {
         ConjuntAutors ca = new ConjuntAutors();
         ca.addAutor("Shakespear");
         String s = "Shakespear";
         ca.removeAutor(s);
         assertFalse(ca.existsAutor(s));
     }

      /**
       * Objecte de la prova: Test del mètode llistarPerPrefix(String pref) de la classe ConjuntAutors
       * Fitxers de dades necessaris: Dades introduïdes manualment. No calen fitxers addicionals.
       * Valors estudiats: Es llisten els autors del conjunt que comencen pel prefix pref passat com a paràmetre.
       * Operativa: En aquest test es comprova que es filtren i llisten correctament els autors
       * del ConjuntAutors que comencen amb un prefix donat
       * Primer es crea un objecte ConjuntAutors i se li afegeixen autors. Es crida a la funció llistarPerPrefix()
       * amb diversos prefixs diferents per comprovar que el resultat sigui el correcte.
       */
     @Test
     public void TestllistarPerPrefix() {
        ConjuntAutors ca = new ConjuntAutors();
         ca.addAutor("Shakespear");
         ca.addAutor("Brandon Sanderson");
         ca.addAutor("Fray Luís de León");
         ca.addAutor("Cervantes");
         ca.addAutor("Eiichiro Oda");
         ca.addAutor("MEi");
         ca.addAutor("eiichiro");
         ca.addAutor("Oda Eiichiro");
         ca.addAutor("Eiblablabla");
         ca.addAutor("Elias");


         String[] expected = {"Eiblablabla", "Eiichiro Oda"};
         assertEquals(expected, ca.llistarPerPrefix("Ei", "a"));

         expected = new String[]{"eiichiro"};
         assertEquals(expected,ca.llistarPerPrefix("ei", "d"));

         expected = new String[]{"Oda Eiichiro"};
         assertEquals(expected,ca.llistarPerPrefix("Oda", "a"));

         expected = new String[]{};
         assertEquals(expected, ca.llistarPerPrefix("Me", "d"));

         expected = new String[]{"MEi"};
         assertEquals(expected, ca.llistarPerPrefix("ME", "a"));

         expected = new String[]{"Elias"};
         assertEquals(expected,ca.llistarPerPrefix("El", "a"));
     }
}