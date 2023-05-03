import domain.classes.Node;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestNode {
    private char VALOR_OR = 'o';
    private char VALOR_AND = 'y';
    @Test
    /**
     *
     */
    public void TestCreateNode(){
        Node arbre = new Node("Alex & !Marc & (!\"Jordi Colomé\" | Elias)");
        assertEquals("Alex & !Marc & (!\"Jordi Colomé\" | Elias)",arbre.getValue());
        assertFalse(arbre.esFulla());
        assertTrue(arbre.teFillEsquerra());
        assertTrue(arbre.teFillDret());
        assertEquals(VALOR_AND,arbre.getPortaLogica());

        assertFalse(arbre.getDre().esFulla());
        assertFalse(arbre.getEsq().esFulla());
        assertEquals(VALOR_AND,arbre.getEsq().getPortaLogica());
        assertEquals(VALOR_OR,arbre.getDre().getPortaLogica());

        assertTrue(arbre.getDre().getDre().esFulla());
        assertTrue(arbre.getDre().getEsq().esFulla());

        assertTrue(arbre.getEsq().getEsq().esFulla());
        assertTrue(arbre.getEsq().getDre().esFulla());
    }
}
