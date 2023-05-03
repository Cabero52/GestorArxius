package persistence.controllers;
import persistence.classes.*;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;


import java.io.IOException;
import java.io.File;
import java.util.TreeSet;

/**
 * @author Dídac Hispano Corbera(didac.hispano@estudiantat.upc.edu)
 */

public class CtrlPersistence {

    private static CtrlPersistence instance = new CtrlPersistence();
    private String pathGeneral = "../../Documents/";
    private GestorArxiuPropi gestorArxiuPropi = new GestorArxiuPropi();
    private GestorArxiuExpressions gestorArxiuExpressions = new GestorArxiuExpressions();
    private ContextFormat CF = new ContextFormat();
    private ConjuntPaths CjtPaths = new ConjuntPaths();

    /**
     * Creadora per defecte
     */
    private CtrlPersistence() {

    }

    /**
     * Obte la instancia del CtrlPersistencia per tractar-lo com un singleton
     */
    public static CtrlPersistence getInstance() {
        return instance;
    }


    /**
     *
     * @param autor autor del document a crear
     * @param titol Titol del document a crear
     * @param contingut contingut del document a crear
     * @param format format en què volem crear el document
     */
    public void crearDocument(String autor, String titol, String contingut, String format) throws DocumentNoTrobat {
        String path = pathGeneral + autor + "_" + titol;
        if (format.equals("xml")) CF.setStrategy(new Xml(autor, titol, contingut, path));
        else if (format.equals("txt")) CF.setStrategy(new Txt(autor, titol, contingut, path));
        CF.crearDocument();
        CjtPaths.afegirPath(autor, titol, format);
    }

    
    /**
     * @throws IOException
     */
    public void guardarExpressio(String expressio) {
        gestorArxiuExpressions.afegirExpressio(expressio);
    }

    /**
     *
     * @param autor autor del document a esborrar
     * @param titol titol del document a esborrar
     * @param format format del document a esborrar
     * @throws DocumentNoTrobat Salta si no es troba el document amb autor = autor i titol = titol
     */
    public void deleteDocument(String autor, String titol, String format) throws DocumentNoTrobat {
        String extensio;
        if (format.equals("txt")) extensio = ".txt";
        else extensio = ".xml";
        String pathdoc = pathGeneral + "/" + autor + "_" + titol + extensio;
        File doc = new File(pathdoc);
        CjtPaths.eliminarPath(autor + "_" + titol);
        doc.delete();
    }

    /**
     * Borrar document de disc
     * @param path path del document a esobrrar
     * @return Informacio del document esborrat
     * @throws DocumentNoTrobat Salta si no troba el document amb path = path
     * @throws InformacioBuida Salta si el paràmetre path està buit
     */
    public String deleteDocument(String path) throws DocumentNoTrobat, InformacioBuida {
        return CjtPaths.deleteDocument(path);
    }

    /**
     *
     * @return retorna tots els documents de l arxiu propi
     * @throws DocumentNoTrobat Salta si no troba el document
     */
    public String[] getDocuments() throws DocumentNoTrobat {
        String[] paths = gestorArxiuPropi.getDocuments(); //autor titol path
        String[] docs = new String[paths.length];
        if (!paths[0].equals("")) {
            for (int i = 0; i < paths.length; ++i) {
                CjtPaths.afegirPath(paths[i]);
                docs[i] = CjtPaths.getDocumentsPerPath(paths[i]);
            }
        }
        return docs;
    }

    /**
     *
     * @return retorna totes les expressions de l arxiu propi
     */
    public String[] getExpressions() {
        String[] exps = gestorArxiuExpressions.getExpressions();
        return exps;
    }

    /**
     * Elimina el contingut d arxiu propi
     */
    public void buidarArxiuPropi(){
        gestorArxiuPropi.buidarArxiuPropi();
    }

    /**
     * Buida l arxiu d expressions
     */
    public void buidarArxiuExpressions(){
        gestorArxiuExpressions.buidarArxiuExpressio();
    }

    /**
     * Guarda els documents a l arxiu propi
     */
    public void guardarDocuments() {
        TreeSet<String> docs = CjtPaths.getDocuments();
        for (String doc:docs) {
            gestorArxiuPropi.afegirDocument(doc);
        }
    }

    /**
     *
     * @param autor Autor del document
     * @param titol Autor del document
     * @return Retorna el contingut del document amb autor = autor i titol = titol
     * @throws DocumentNoTrobat Salta si no es troba el document
     */
    public String getContingut(String autor, String titol) throws DocumentNoTrobat {
        return CjtPaths.getContingut(autor,titol);
    }

    /**
     *
     * @param path path del document a importar
     * @return Document importat
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws InformacioBuida Salta si el paràmetre path està buit
     */
    public String importarDocument(String path) throws DocumentNoTrobat, InformacioBuida {
        return CjtPaths.importarDocument(path);
    }

    /**
     *
     * @param path Path del document
     * @return Retorna el document en forma de String en el seguent format: autor_doc:titol:titol_doc:cont:contingut_doc
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws InformacioBuida Salta si el paràmetre està buit
     */
    public String getTotaInfo(String path) throws DocumentNoTrobat, InformacioBuida {
        return CjtPaths.getTotaInfo(path);
    }

    /**
     *
     * @param path Path del document
     * @return Contingut del document amb path = path
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws NullPointerException Salta si no es troba el document
     * @throws InformacioBuida Salta si el paràmetre path esta buit
     */
    public String getContingut(String path) throws DocumentNoTrobat, NullPointerException, InformacioBuida {
        return CjtPaths.getContingut(path);
    }

    /**
     *
     * @param autor Autor del contingut a editar
     * @param titol Titol del document a editar
     * @param contingut Nou contingut del document
     */
    public void editarContingutPerID(String autor, String titol, String contingut) throws DocumentNoTrobat {
        String path = CjtPaths.getPath(autor, titol);
        if (path.contains(".txt")) CF.setStrategy(new Txt(autor, titol, contingut, path.substring(0,path.length()-4)));
        else if (path.contains(".xml")) CF.setStrategy(new Xml(autor, titol, contingut, path.substring(0,path.length()-4)));
        CF.crearDocument();
    }

    /**
     *
     * @param path path del document a editar
     * @param cont Nou contingut del document
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws InformacioBuida Salta si el path o el contingut es buit
     */
    public void editarContingutPerPath(String path, String cont) throws DocumentNoTrobat, InformacioBuida {
        String[] autor = CjtPaths.getTotaInfo(path).split(":titol:");
        String titol = autor[1].split(":cont:")[0];
        editarContingutPerID(autor[0], titol, cont);
    }

    /**
     *
     * @param autor Autor del document a editar
     * @param titol Titol del document a editar
     * @param nouTitol Nou titol del document
     * @throws DocumentNoTrobat Salta si no es troba el document
     */
    public void editarTitol(String autor, String titol, String nouTitol) throws DocumentNoTrobat {
        CjtPaths.editarTitol(autor, titol, nouTitol);
    }

    /**
     *
     * @param autor Autor del document a editar
     * @param titol Titol del document a editar
     * @param nouAutor Nou autor del document
     * @throws DocumentNoTrobat Salta si no es troba el document
     */
    public void editarAutor(String autor, String titol, String nouAutor) throws DocumentNoTrobat {
        CjtPaths.editarAutor(autor, titol, nouAutor);
    }
}
