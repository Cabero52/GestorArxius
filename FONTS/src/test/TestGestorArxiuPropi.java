
import persistence.classes.GestorArxiuPropi;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGestorArxiuPropi {
    @Test
    public void testCreateGestorArxiuPropi() {
        GestorArxiuPropi GAP = new GestorArxiuPropi();
        String infoDoc = "Marc:titol:Test1:path:./Documents/Marc_Test1.txt:fiDocu:";
        GAP.afegirDocument(infoDoc);
        String[] result = GAP.getDocuments();
        int n = result.length;
        assertEquals("Marc:titol:Test1:path:./Documents/Marc_Test1.txt", result[n-1]);
        GAP.buidarArxiuPropi();
        for (int i = 0; i < n-1; ++i) {
            GAP.afegirDocument(result[i]);
        }
    }
}
