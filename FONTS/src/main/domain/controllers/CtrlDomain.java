package domain.controllers;

import domain.classes.*;
import domain.classes.exceptions.*;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;
import persistence.controllers.* ;

import javax.print.Doc;
import java.util.TreeSet;

/**
 * @author Jaume Alos Cuadrado(jaume.alos@estudiantat.upc.edu)
 */

public class CtrlDomain {
    private ConjuntAutors CjtAutors = new ConjuntAutors();
    private Estructura structure = new Estructura();
    private ConjuntDocuments CjtDocuments = new ConjuntDocuments(CjtAutors, structure);
    private Terminal ter = new Terminal();
    private ConjuntExpressions CjtExpressions= new ConjuntExpressions();
    private CtrlPersistence CP = CtrlPersistence.getInstance();
    private static CtrlDomain instance = new CtrlDomain();

    /**
     * Creadora per defecte
     */
    private CtrlDomain() {

    }

    /**
     * @return Retorna una instància de CtrlDomain
     */
    public static CtrlDomain getInstance() {
        return instance;
    }

    //------------------- DOCUMENT -------------------- //

    /**
     * Metode que permet la creacio d un document amb els parametres especificats
     * @param autor autor del document a crear
     * @param titol Titol del document a crear
     * @param contingut Contingut del document a crear
     * @param format Format del document a crear
     * @throws JaExisteixException Salta si ja existeix un document amb autor = autor i contingut = contingut
     * @throws NullException Salta si te valors nulls
     */
    public void crearDoc(String autor, String titol, String contingut, String format) throws JaExisteixException,
            NullException, DocumentNoTrobat {

        CjtDocuments.createDocument(autor, titol, contingut);

        ter.imprimirString("S'ha creat el Document.");
        CP.crearDocument(autor, titol, contingut, format);
    }

    /**
     * Metode que permet eliminar un document a partir dels parametres especificats
     * @param autor Autor del document a esborrar
     * @param titol Titol del document a esborrar
     * @throws DocumentNoExisteix Salta si el document amb autor = autor i titol = titol no existeix
     * @throws DocumentNoTrobat Salta si no es troba el document.
     */
    public void borrarDocument(String autor, String titol) throws DocumentNoExisteix, DocumentNoTrobat {

        CjtDocuments.removeDocumentDefinitivament(autor, titol);

        ter.imprimirString("S'ha esborrat el Document.");
        CP.deleteDocument(autor,titol,"txt");
        CP.deleteDocument(autor,titol,"xml");
    }

    /**
     * Metode que permet eliminar un document a partir del parametre indicat
     * @param path Esborra el document amb camí = path
     * @throws DocumentNoExisteix Salta si el document no existeix
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws NullPointerException Salta si la informacio donada és null
     * @throws InformacioBuida Salta si la informacio esta buida
     */
    public void borrarDocument(String path) throws DocumentNoExisteix, DocumentNoTrobat, NullPointerException, InformacioBuida {
        String info;
        info = CP.deleteDocument(path);
        String[] autorTitol = info.split(":titol:");
        CjtDocuments.removeDocumentDefinitivament(autorTitol[0], autorTitol[1]);
    }

    /**
     * @return Tots els autors del conjunt d autors
     */
    public String[] getAutors() {
        return CjtDocuments.getAutors();
    }

    /**
     * Metode que permet editar el contingut d un document identificat amb els parametres
     * @param autor Autor del document a editar
     * @param titol Titol del document a editar
     * @param contingut Nou contingut del document a editar
     */
    public void editarContingut(String autor, String titol, String contingut) throws DocumentNoTrobat {
        CP.editarContingutPerID(autor, titol, contingut);
        //CjtDocuments.editarContingut(autor, titol, contingut);
        ter.imprimirString("S'ha editat el contingut del Document");
    }

    /**
     * Metode que permet editar el contingut d un document amb el parametre indicat
     * @param path Path del document a editar
     * @param contingut Nou contingut del document a editar
     * @throws DocumentNoTrobat Salta si el path no existeix
     */
    public void editarContingut(String path, String contingut) throws DocumentNoTrobat, InformacioBuida {
        CP.editarContingutPerPath(path, contingut);
    }

    /**
     * Metode que permet renombrar el titol d un document
     * @param autor Autor del document que es vol editar
     * @param titol Titol del document que es vol editar
     * @param nouTitol Nou titol del document que es vol editar
     * @throws DocumentNoTrobat Salta si No es troba el document
     * @throws JaExisteixException Salta si ja hi ha un document amb autor = autor i titol = nou titol
     * @throws NullException Salta si un dels parametres es null
     * @throws DocumentNoExisteix Salta si no existeix el document amb autor = autor i titol = titol
     */
    public void editarTitol(String autor, String titol, String nouTitol) throws DocumentNoTrobat , JaExisteixException,
            NullException, DocumentNoExisteix {
        CjtDocuments.renameTitol(autor, titol, nouTitol);
        CP.editarTitol(autor,titol,nouTitol);
        ter.imprimirString("S'ha editat el títol del document");
    }

    /**
     * Metode que permet renombrar el nom del autor d un document
     * @param autor Autor del document a editar
     * @param titol Titol del document a editar
     * @param nouAutor Nou autor del document a editar
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws DocumentNoExisteix Salta si el document no existeix
     * @throws NullException Salta si algun dels parametres es null
     */
    public void editarAutor(String autor, String titol, String nouAutor) throws DocumentNoTrobat, DocumentNoExisteix,
            NullException {
        CP.editarAutor(autor, titol, nouAutor);
        CjtDocuments.renameAutor(autor, titol, nouAutor);
    }

    /**
     * Metode que permet editar tota la informacio d un document
     * @param autor Autor del document a modificar
     * @param nouAutor Nou autor del document editat
     * @param titol Titol del document a modificar
     * @param nouTitol Nou titol del document editat
     * @param contingut Contingut del document a modificar
     * @param nouContingut Nou contingut del document a modificar
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws DocumentNoExisteix Salta si el document no existeix
     * @throws NullException Salta si un dels camps es buit
     * @throws JaExisteixException Salta si ja existeix un document amb autor = nouAutor, titol = nouTitol
     *                              i contingut = nouContingut
     */
    public void editarDoc(String autor, String nouAutor, String titol, String nouTitol, String contingut,
                          String nouContingut) throws DocumentNoTrobat, DocumentNoExisteix, NullException,
                          JaExisteixException {
        if (!autor.equals(nouAutor)) editarAutor(autor,titol,nouAutor);
        if (!nouTitol.equals(titol)) editarTitol(nouAutor,titol,nouTitol);
        if (!nouContingut.equals(contingut)) editarContingut(nouAutor,nouTitol,nouContingut);
}

    //------------- EXPRESSIONS ----------------

    /**
     * Metode que permet la creacio d una expressio booleana
     * @param expressio Expressio booleana a crear
     * @return Conjunt d expressions booleanes sencer
     */
    public String[] crearExpressio (String expressio){
        CjtExpressions.creaExpressio(expressio);
        return getExpressionsBooleanes();
    }

    /**
     * Metode que permet la eliminacio d una expressio booleana
     * @param expressio Expressio a eliminar
     * @return Conjunt d expressions booleanes sencer
     */
    public String[] eliminaExpressio (String expressio){
        CjtExpressions.eliminaExpressio(expressio);
        return getExpressionsBooleanes();
    }

    /**
     * Metode que permet la modificacio d una expressio booleana
     * @param expressio Expressio a modificar
     * @param novaExpressio Expressio modificada
     * @return Conjunt d expressions booleanes sencer
     */
    public String[] modificaExpressio(String expressio, String novaExpressio){
        CjtExpressions.modificaExpressio(expressio,novaExpressio);
        return getExpressionsBooleanes();
    }

    //------------- LLISTATS ----------------

    /**
     * Metode que permet mostrar un document a partir d un autor i titol
     * @param autor Autor del document a llistar
     * @param titol Titol del document a llistar
     * @return Contingut del document
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws NullException Salta si Algun dels camps és buit
     */
    public String llistarDocumentPerTitolAutor(String autor, String titol) throws DocumentNoTrobat, NullException {
        if (autor == null | titol == null) {
            throw new NullException();
        }
        String contingut = CP.getContingut(autor, titol);
        return contingut;
    }

    /**
     * Metode que permet llistar els documents d un autor especific
     * @param autor Autor del que es volen llistar els titols dels seus documents
     * @param ordre Ordre en que es volen mostrar els titols
     * @return Conjunt de titol de l autor amb nom autor ordenats segons ordre
     */
    public String[] llistarTitolsAutor(String autor, String ordre){
        String[] titolsAutor = CjtDocuments.llistarTitolsAutor(autor, ordre);
        return titolsAutor;
    }

    /**
     * Metode que permet llistar els autors que contenen un prefix
     * @param prefix Prefix pel que es vol llistar
     * @param ordre Ordre en que es vol llistar
     * @return Conjunt d autors que comencen pel prefix = prefix
     */
    public String[] llistarAutorPrefix(String prefix , String ordre){
        String[] autorsPrefix = CjtAutors.llistarPerPrefix(prefix, ordre);

        ter.imprimirString("Autors:");
        for (int i = 0; i < autorsPrefix.length; i++) {
            System.out.println(autorsPrefix[i]);
        }
        return autorsPrefix;
    }

    /**
     * Metode que permet llistar tots els documents existents en el sistema
     * @param ordre Ordre en que es vol llistar
     * @return Conjunt de documents
     */
    public String[] llistarTotsDocs(String ordre){
        IDdocument[] docs = CjtDocuments.llistarTotsDocs(ordre);
        String[] docsString = new String[docs.length];
        for (int i = 0; i < docs.length; ++i) {
            docsString[i] = "Autor: " + docs[i].first() + " Títol: " + docs[i].second();
        }
        return docsString;
    }

    /**
     * Metode que permet llistar n documents semblants al document indicat
     * @param autor Autor del document
     * @param titol Titol del document
     * @param k Nombre de documents semblants que es volen llistar
     * @param ordre Ordre en que es vol llistar
     * @return Conjunt format pels de k documents mes semblants al document amb autor = autor i titol = titol
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws NullPointerException Salta si l autor o titol son buits
     */
    public String[] llistarDocSemblants(String autor, String titol, int k, String ordre) throws DocumentNoTrobat, NullPointerException {

        IDdocument[] docs = CjtDocuments.getIdDocuments();
        for(int i = 0; i < docs.length; ++i) {
            String contingut = CP.getContingut(docs[i].first(),docs[i].second());
            CjtDocuments.afegirContTemp(docs[i],contingut);
        }
        IDdocument[] ordenat = CjtDocuments.llistarDocsemblants(autor, titol, k, ordre);

        for(int i = 0; i < docs.length; ++i) {
            CjtDocuments.buidarContingut(docs[i].first(),docs[i].second());
        }

        String[] documents = new String[ordenat.length];
        for (int i = 0; i < ordenat.length; ++i) {
            documents[i] ="Autor: "+ ordenat[i].first() + " Titol: " + ordenat[i].second();
            System.out.println("Autor: " + ordenat[i].first() + " Titol: " + ordenat[i].second());
        }

        return documents;
    }

    /**
     * Metode que permet llistar n documents semblants al document indicat
     * @param path Path del document amb el que es vol llistar per documents semblants.
     * @param k Nombre de documents semblants que vols llistar els document
     * @param ordre Criteri d ordre en que vols llistar els document
     * @return Conjunt de strings amb els k documents més semblants al document amb path = path
     * @throws DocumentNoTrobat Salta si el path no existeix
     */
    public String[] llistarDocSemblants(String path, int k, String ordre) throws DocumentNoTrobat, InformacioBuida {
        String[] autor = CP.getTotaInfo(path).split(":titol:");
        String titol = autor[1].split(":cont:")[0];
        return llistarDocSemblants(autor[0], titol, k, ordre);
    }

    /**
     * Metode que permet llistar els documents a partir d una expressio booleana
     * @param expressio Expressio per la que es volen llistar els documents
     * @param ordre Ordre en que es volen llistar els documents
     * @return Conjunt de documents que compleixen l expressio
     * @throws DocumentNoTrobat Salta si no es troba algun document
     */
    public String[] llistarDocumentsPerExpressio(String expressio, String ordre) throws DocumentNoTrobat{

        IDdocument[] docs = CjtDocuments.getIdDocuments();
        TreeSet<IDdocument> idDocsCompleixen = new TreeSet<>();

        for(int i = 0; i < docs.length; ++i){
            String contingut = CP.getContingut(docs[i].first(),docs[i].second());
            CjtDocuments.afegirContTemp(docs[i],contingut);
            if(CjtDocuments.llistarDocumentsPerExpressio(docs[i].first(),docs[i].second(),expressio)){
                idDocsCompleixen.add(docs[i]);
            }
            CjtDocuments.buidarContingut(docs[i].first(),docs[i].second());
        }

        docs = CjtDocuments.ordenarSetId(idDocsCompleixen,ordre);
        String[] docsCompleixen = new String[docs.length];
        for (int i = 0; i < docs.length; ++i) {
            docsCompleixen[i] ="Autor: "+ docs[i].first() + " Titol: " + docs[i].second();
        }
        return docsCompleixen;
    }

    /**
     * Guardar tots els documents de programa a disc
     */
    private void guardarDocuments() {
        CP.guardarDocuments();
    }

    /**
     * Metode que permet emmagatzemar les expressions booleanas en el fitxer d expressions booleans propi
     * @throws ArrayIndexOutOfBoundsException Salta si s'accedeix a una posició incorrecte, no hauria de saltar
     */
    private void guardarExpressions() throws ArrayIndexOutOfBoundsException {
        TreeSet<String> expressionsAGuardar = CjtExpressions.getExpressions();
        for (String exp:expressionsAGuardar) {
            CP.guardarExpressio(exp);
        }
    }

    /**
     * Metode que emmagatzema la informacio del sistema al tancar l aplicacio
     * @throws ArrayIndexOutOfBoundsException Salta si s'accedeix a una posició incorrecte, no hauria de saltar
     */
    public void finalitzarAplicacio() throws ArrayIndexOutOfBoundsException {
        CP.buidarArxiuPropi();
        CP.buidarArxiuExpressions();
        guardarDocuments();
        guardarExpressions();
    }

    /**
     * Guarda tota la informació als arxius amb format propi
     */
    public void guardarInformacio() {
        ter.imprimirString("S'estan guardant els documents...");
        finalitzarAplicacio();
        ter.imprimirString("Documents guardats!");
    }

    /**
     * Metode que permet convertir el format del document
     * @param autor Autor del document
     * @param titol Titol del document
     * @param format dormat en que es vol guardar el document
     * @throws DocumentNoTrobat Salta si no es troba el document
     */
    public void exportarFormatDocument(String autor, String titol, String format) throws DocumentNoTrobat {
        String contingut = CP.getContingut(autor, titol);
        CP.deleteDocument(autor, titol, "xml");
        CP.deleteDocument(autor, titol, "txt");
        CP.crearDocument(autor, titol, contingut, format);
    }

    /**
     *
     * @param path Path del document a convertir
     * @param format Format al que volem convertir el document
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws NullPointerException Salta si no es troba el document
     * @throws InformacioBuida Salta si els paràmetres son buits
     */
    public void exportarFormatDocument(String path, String format) throws DocumentNoTrobat, NullPointerException, InformacioBuida {
        String contingut = CP.getContingut(path);
        String info = "";
        if (format == "txt") info = CP.deleteDocument(path);
        else if (format == "xml") info = CP.deleteDocument(path);
        String[] autorTitol = info.split(":titol:");
        CP.crearDocument(autorTitol[0], autorTitol[1], contingut, format);
    }

    /**
     * Inicialitza l app amb tots els documents emmagetzemats en el sistema
     * @throws DocumentNoTrobat Salta si no es troba el document
     */
    public void inicialitzarAplicacio() throws DocumentNoTrobat {
        cargarDocuments();
        cargarExpressions();
    }

    /**
     * Inicialitza la app amb totes les expressions existents en el sistema
     */
    private void cargarExpressions() {
        String[] expressions = CP.getExpressions();
        int numExps = expressions.length;
        String exp;
        if (!expressions[0].equals("")) {
            for (int i = 0; i < numExps; ++i) {
                CjtExpressions.creaExpressio(expressions[i]);
            }
        }
    }

    /**
     * Metode que permet carregar els documents del fitxer propi
     * @throws DocumentNoTrobat Salta si hi ha path erroni a l'arxiu propi
     */
    private void cargarDocuments() throws DocumentNoTrobat {
        String[] docsCodificats = CP.getDocuments();
        int numDocs = docsCodificats.length;
        String autor;
        String titol;
        String cont;
        if(docsCodificats[0] != null){
            for (int i = 0; i < numDocs; ++i) {
                String[] autorDocSeparat = docsCodificats[i].split(":titol:");
                autor = autorDocSeparat[0];
                String[] infoDoc = autorDocSeparat[1].split(":cont:");
                titol = infoDoc[0];
                cont = infoDoc[1];
                try {
                    CjtDocuments.createDocument(autor, titol, cont);
                } catch (NullException e) {
                    ter.imprimirString("No hauria de saltar mai, CtrlDomain->CargarDocs");
                } catch (JaExisteixException jee) {
                    ter.imprimirString("JEE a domini->cargar");
                }
                CjtDocuments.buidarContingut(autor, titol);
            }
        }
        CP.buidarArxiuPropi();
    }

    /**
     * Metode que permet importar un document del sistema
     * @param path Path del document a importar
     * @throws DocumentNoTrobat El path del document no existeix
     * @throws NullException Salta si l autor o el titol són buits
     * @throws InformacioErroneaException Salta si l informacio introduida es erronia
     * @throws JaExisteixException Salta si el document ja existeix
     */
    public void importarDocumnet(String path) throws DocumentNoTrobat, NullException, InformacioErroneaException,
            JaExisteixException, InformacioBuida {
        String infoDoc = CP.importarDocument(path);
        String[] autor = infoDoc.split(":titol:");
        String[] titolCont = autor[1].split(":cont:"); // [0] -> titol -- [1] -> cont
        try {
            CjtDocuments.createDocument(autor[0], titolCont[0], titolCont[1]);
        } catch (ArrayIndexOutOfBoundsException aiooe) {
            throw new DocumentNoTrobat(path);
        }
    }

    /**
     * Metode que permet obtenir la informacio d un document
     * @param path Path del document
     * @return Retorna tota la info del document identificat amb el path introduit com a parametre
     * @throws DocumentNoTrobat Salta si el path introduit és incorrecte
     */
    public String getTotaInfo(String path) throws DocumentNoTrobat, InformacioBuida {
        return CP.getTotaInfo(path);
    }

    /**
     * @return retorna un conjunt amb totes les expressions booleanes existents
     */
    public String[] getExpressionsBooleanes() {
        return CjtExpressions.getExpressionsBooleanes();
    }
}