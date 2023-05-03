import persistence.classes.GestorArxiuExpressions;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGestorArxiuExpressions {
    @Test
    public void testCreateGestorArxiuExpresssions() {
        GestorArxiuExpressions GAE = new GestorArxiuExpressions();
        GAE.afegirExpressio("\"Test Persistencia\" | {Jaume Elias Dídac Marc} & (!Joan | Sergio):fiExp:");
        String[] result = GAE.getExpressions();
        int n = result.length;
        assertEquals("\"Test Persistencia\" | {Jaume Elias Dídac Marc} & (!Joan | Sergio)", result[n-1]);
        GAE.buidarArxiuExpressio();
        for (int i = 0; i < n-1; ++i) {
            GAE.afegirExpressio(result[i]);
        }
    }
}
