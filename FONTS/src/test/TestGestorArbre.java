import domain.classes.GestorArbre;

import org.junit.Test;
import domain.classes.Node;

import static org.junit.Assert.*;

public class TestGestorArbre {
    @Test
    /**
     */
   public void TestgestorArbre() {

        GestorArbre GA = new GestorArbre();
        String test1 = "Esquí Snow";
        String test2 = "Esquí";
        String test3 = "Snow";
        String test4 = "Futbol";
        //testAndBase
        Node testand1 = new Node("Esquí & Snow");
        Node testand2 = new Node("Esquí & !Snow");
        Node testand3 = new Node("!Esquí & Snow");
        Node testand4 = new Node("!Esquí & !Snow");
        assertTrue(GA.gestionarArbreBoolea(testand1,test1));
        assertFalse(GA.gestionarArbreBoolea(testand1,test2));
        assertFalse(GA.gestionarArbreBoolea(testand1,test3));
        assertFalse(GA.gestionarArbreBoolea(testand1,test4));

        assertTrue(GA.gestionarArbreBoolea(testand2,test2));
        assertFalse(GA.gestionarArbreBoolea(testand2,test1));
        assertFalse(GA.gestionarArbreBoolea(testand2,test3));
        assertFalse(GA.gestionarArbreBoolea(testand2,test4));

        assertTrue(GA.gestionarArbreBoolea(testand3,test3));
        assertFalse(GA.gestionarArbreBoolea(testand3,test1));
        assertFalse(GA.gestionarArbreBoolea(testand3,test2));
        assertFalse(GA.gestionarArbreBoolea(testand3,test4));

         assertTrue(GA.gestionarArbreBoolea(testand4,test4));
         assertFalse(GA.gestionarArbreBoolea(testand4,test1));
         assertFalse(GA.gestionarArbreBoolea(testand4,test2));
         assertFalse(GA.gestionarArbreBoolea(testand4,test3));

        //testOrBase
        Node testor1 = new Node("Esquí | Snow");
        Node testor2 = new Node("Esquí | !Snow");
        Node testor3 = new Node("!Esquí | Snow");
        Node testor4 = new Node("!Esquí | !Snow");

         assertTrue(GA.gestionarArbreBoolea(testor1,test1));
         assertTrue(GA.gestionarArbreBoolea(testor1,test2));
         assertTrue(GA.gestionarArbreBoolea(testor1,test3));
         assertFalse(GA.gestionarArbreBoolea(testor1,test4));

        assertTrue(GA.gestionarArbreBoolea(testor2,test1));
        assertTrue(GA.gestionarArbreBoolea(testor2,test2));
        assertFalse(GA.gestionarArbreBoolea(testor2,test3));
        assertTrue(GA.gestionarArbreBoolea(testor2,test4));

        assertTrue(GA.gestionarArbreBoolea(testor3,test1));
        assertFalse(GA.gestionarArbreBoolea(testor3,test2));
        assertTrue(GA.gestionarArbreBoolea(testor3,test3));
        assertTrue(GA.gestionarArbreBoolea(testor3,test4));

        assertTrue(GA.gestionarArbreBoolea(testor4,test2));
        assertTrue(GA.gestionarArbreBoolea(testor4,test3));
        assertTrue(GA.gestionarArbreBoolea(testor4,test4));
        assertFalse(GA.gestionarArbreBoolea(testor4,test1));

        //testOrAnd
        Node testorand1 = new Node("(Esquí & Futbol) | Snow");
        Node testorand2 = new Node("Esquí | (!Snow & Futbol)");
        Node testorand3 = new Node("(!Esquí | Snow) & Futbol");
        Node testorand4 = new Node("(!Esquí | !Snow) & !Futbol");
        assertTrue(GA.gestionarArbreBoolea(testorand1,test1));
        assertFalse(GA.gestionarArbreBoolea(testorand1,test2));
        assertTrue(GA.gestionarArbreBoolea(testorand1,test3));
        assertFalse(GA.gestionarArbreBoolea(testorand1,test4));

        assertTrue(GA.gestionarArbreBoolea(testorand2,test1));
        assertTrue(GA.gestionarArbreBoolea(testorand2,test2));
        assertFalse(GA.gestionarArbreBoolea(testorand2,test3));
        assertTrue(GA.gestionarArbreBoolea(testorand2,test4));

        assertFalse(GA.gestionarArbreBoolea(testorand3,test1));
        assertFalse(GA.gestionarArbreBoolea(testorand3,test2));
        assertFalse(GA.gestionarArbreBoolea(testorand3,test3));
        assertTrue(GA.gestionarArbreBoolea(testorand3,test4));

        assertFalse(GA.gestionarArbreBoolea(testorand4,test1));
        assertTrue(GA.gestionarArbreBoolea(testorand4,test2));
        assertTrue(GA.gestionarArbreBoolea(testorand4,test3));
        assertFalse(GA.gestionarArbreBoolea(testorand4,test4));

        //testAndParentesi
        Node testandp1 = new Node("(Esquí & Snow)");
        Node testandp2 = new Node("(Esquí & !Snow)");
        Node testandp3 = new Node("(!Esquí & Snow)");
        Node testandp4 = new Node("(!Esquí & !Snow)");

        assertTrue(GA.gestionarArbreBoolea(testandp1,test1));
        assertFalse(GA.gestionarArbreBoolea(testandp1,test2));
        assertFalse(GA.gestionarArbreBoolea(testandp1,test3));
        assertFalse(GA.gestionarArbreBoolea(testandp1,test4));

        assertTrue(GA.gestionarArbreBoolea(testandp2,test2));
        assertFalse(GA.gestionarArbreBoolea(testandp2,test1));
        assertFalse(GA.gestionarArbreBoolea(testandp2,test3));
        assertFalse(GA.gestionarArbreBoolea(testandp2,test4));

        assertTrue(GA.gestionarArbreBoolea(testandp3,test3));
        assertFalse(GA.gestionarArbreBoolea(testandp3,test1));
        assertFalse(GA.gestionarArbreBoolea(testandp3,test2));
        assertFalse(GA.gestionarArbreBoolea(testandp3,test4));

        assertTrue(GA.gestionarArbreBoolea(testandp4,test4));
        assertFalse(GA.gestionarArbreBoolea(testandp4,test1));
        assertFalse(GA.gestionarArbreBoolea(testandp4,test2));
        assertFalse(GA.gestionarArbreBoolea(testandp4,test3));

        //testOrParentesi
        Node testorp1 = new Node("(Esquí | Snow)");
        Node testorp2 = new Node("(Esquí | !Snow)");
        Node testorp3 = new Node("(!Esquí | Snow)");
        Node testorp4 = new Node("(!Esquí | !Snow)");

        assertTrue(GA.gestionarArbreBoolea(testorp1,test1));
        assertTrue(GA.gestionarArbreBoolea(testorp1,test2));
        assertTrue(GA.gestionarArbreBoolea(testorp1,test3));
        assertFalse(GA.gestionarArbreBoolea(testorp1,test4));

        assertTrue(GA.gestionarArbreBoolea(testorp2,test1));
        assertTrue(GA.gestionarArbreBoolea(testorp2,test2));
        assertFalse(GA.gestionarArbreBoolea(testorp2,test3));
        assertTrue(GA.gestionarArbreBoolea(testorp2,test4));

        assertTrue(GA.gestionarArbreBoolea(testorp3,test1));
        assertFalse(GA.gestionarArbreBoolea(testorp3,test2));
        assertTrue(GA.gestionarArbreBoolea(testorp3,test3));
        assertTrue(GA.gestionarArbreBoolea(testorp3,test4));

        assertTrue(GA.gestionarArbreBoolea(testorp4,test2));
        assertTrue(GA.gestionarArbreBoolea(testorp4,test3));
        assertTrue(GA.gestionarArbreBoolea(testorp4,test4));
        assertFalse(GA.gestionarArbreBoolea(testorp4,test1));

        //testcompost
        Node testFinal = new Node("({Esquí Pals Snow} & Pista | (\"El Dídac no coneix el Pokemon del dia\" | !Futbol)) & Basket");
        String test5 = "Esquí , Pals , Snow,  son objectes que estan a una Pista d'esquí no a una cancha de Basket i per cert, El Dídac no coneix el Pokemon del dia";
        assertFalse(GA.gestionarArbreBoolea(testFinal,test1));
        assertFalse(GA.gestionarArbreBoolea(testFinal,test2));
        assertFalse(GA.gestionarArbreBoolea(testFinal,test3));
        assertFalse(GA.gestionarArbreBoolea(testFinal,test4));
        assertTrue(GA.gestionarArbreBoolea(testFinal,test5));

    }
}