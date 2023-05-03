import org.junit.Test;
import persistence.classes.ConjuntPaths;

import static org.junit.Assert.*;

public class TestConjuntPaths {
    @Test
    public void testCreateConjuntPath() {
        ConjuntPaths CP = new ConjuntPaths();
        String autor1 = "Dídac";
        String titol1 = "Pokemon";
        String path1 = "./Documents/Dídac_Pokemon.xml";
        String format1 = "xml";
        CP.afegirPath(autor1,titol1,format1);
        assertEquals(path1, CP.getPath(autor1, titol1));
        String autor2 = "Elias";
        String titol2 = "Marruecos";
        String path2 = "./Documents/Elias_Marruecos.txt";
        String format2 = "txt";
        CP.afegirPath(autor2,titol2,format2);
        assertEquals(path2, CP.getPath(autor2, titol2));
    }
    
}