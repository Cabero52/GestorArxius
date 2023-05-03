package domain.classes;

import domain.classes.exceptions.*;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */

public class ConjuntDocuments {
    /**
     * Estructura que emmagetzema l'IDdocument i cadascun dels documents
     */
    private TreeMap<IDdocument, Document> CjtDocuments = new TreeMap<>();

    private ConjuntAutors CjtAutors;
    private Estructura structure;
    private Ordre o = new Ordre();

    //------------- CONSTRUCTORA ------------

    public ConjuntDocuments(ConjuntAutors Cjt_a, Estructura estruct) {
        CjtAutors = Cjt_a;
        structure = estruct;
    }
    

    //------------- CONSULTORES -------------

    /**
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     * @return si existeix el document identificat amb els parametres autor i titol dintre del conjunt
     */
    public boolean existDocument(String autor, String titol) {
        return CjtDocuments.containsKey(new IDdocument(autor,titol));
    }

    /**
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     * @return el document identificat amb els parametres autor i titols dintre del conjunt
     */
    public Document findDocument(String autor, String titol) {
        return CjtDocuments.get(new IDdocument(autor,titol));
    }

    /**
     * S'usa per presentació
     * @return Retorna tots els autors del conjunt d'autors
     */
    public String[] getAutors() {
        return CjtAutors.getAutors();
    }

    /**
     * @return el nombre de documents que hi ha en el conjunt
     */
    public int getNumDocuments() {
        return CjtDocuments.size();
    }

    /**
     * @param autor nom del autor
     * @return el num de documents que te un autor
     */
    private int getNumDocumentsAutor(String autor) {
        int docs = 0;
        if ( CjtAutors.existsAutor(autor)) {
            for (IDdocument idDoc : CjtDocuments.keySet()) {
                if (idDoc.first().equals(autor)) ++docs;
            }
        }
        return docs;
    }

    /**
     *
     * @return retorna tots els documents convertits en strings amb el format: autor_doc:titol:titol_doc:cont:contingut
     */
    public TreeSet<String> getDocuments() {
        TreeSet<String> documents = new TreeSet<>();
        for (IDdocument idDoc: CjtDocuments.keySet()) {
            Document doc = findDocument(idDoc.first(), idDoc.second());
            String contingut = doc.getCont();
            documents.add(idDoc.first() + ":titol:" + idDoc.second() + ":cont:" + contingut + ":fiDocu:");
        }
        return documents;

    }

    //------------- MODIFICADORES -----------

    /**
     * Elimina del conjunt el document identificat amb els parametres indicats
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     */
    public void removeDocumentDefinitivament(String autor, String titol) throws DocumentNoExisteix {

        if (! existDocument(autor, titol))  throw new DocumentNoExisteix(autor, titol);

        structure.DeleteDoc(findDocument(autor,titol));
        CjtDocuments.remove(new IDdocument(autor, titol));
        if (getNumDocumentsAutor(autor) == 0) {
            CjtAutors.removeAutor(autor);
        }
    }

    /**
     * Crea un document a partir dels parametres introduits i l'afegeix al conjunt
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     * @param contingut contingut del document
     */
    public void createDocument(String autor, String titol, String contingut) throws JaExisteixException, NullException {
        if (comprovarTitolAutor(titol, autor)) throw new NullException();
        if (comprovarParametre(contingut)) throw new NullException();
        if (existDocument(autor, titol)) {
            throw new JaExisteixException("el document amb l'autor "+ autor + "amb titol " + titol);
        }

        if (! CjtAutors.existsAutor(autor)) CjtAutors.addAutor(autor);
        Document doc = new Document(autor, titol, contingut);
        structure.NewDoc(doc);
        CjtDocuments.put(new IDdocument(autor,titol),doc);
    }

    /**
     * Modifica el nom de l autor d'un document identificat pels parametres introduits
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     * @param nouAutor nom del nou autor de
     */
    public void renameAutor(String autor, String titol, String nouAutor ) throws DocumentNoExisteix, NullException {
        if (! existDocument(autor, titol)) throw new DocumentNoExisteix(autor, titol);
        if (comprovarParametre(nouAutor)) throw new NullException();

        CjtAutors.addAutor(nouAutor);
        Document doc = CjtDocuments.remove(new IDdocument(autor, titol));
        structure.DeleteDoc(doc);

        doc.renameAutor(nouAutor);
        CjtDocuments.put(new IDdocument(nouAutor,titol), doc);
        structure.NewDoc(doc);

        if (getNumDocumentsAutor(autor) == 0) CjtAutors.removeAutor(autor);

    }

    /**
     * Modifica el titol d'un document identificat pels parametres introduits
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     * @param nouTitol nou nom de titol pel document
     */
    public void renameTitol(String autor, String titol, String nouTitol) throws DocumentNoExisteix, NullException,
            JaExisteixException{
        if ( comprovarTitolAutor(titol, autor) | comprovarParametre(nouTitol)) throw new NullException();
        if (!existDocument(autor, titol)) throw new DocumentNoExisteix(autor, titol);
        if (titol == nouTitol) throw new JaExisteixException(titol);
        Document doc = CjtDocuments.remove(new IDdocument(autor,titol));
        structure.DeleteDoc(doc);
        doc.renameTitol(nouTitol);
        CjtDocuments.put(new IDdocument(autor, nouTitol), doc);
        structure.NewDoc(doc);


    }

    /**
     * Modifica el contingut d'un document identificat pels parametres introduits
     * @param autor nom de l autor del document
     * @param titol nom del titol del docuement
     * @param contingut contingut editat del document
     */
    public void editarContingut(String autor, String titol, String contingut) {
        Document doc = findDocument(autor,titol);
        structure.DeleteDoc(doc);
        doc.editarContingut(contingut);
        structure.NewDoc(doc);

    }

    //------------- LLISTATS ----------------//

    /**
     * @param autor nom de l autor del document
     * @param titol nom del titol del document
     * @return El contingut del document identificat pels parametres especificats
     */
    public String llistarDocumentPerTitolAutor(String autor, String titol) throws DocumentNoExisteix {
        if (! existDocument(autor,titol)) throw new DocumentNoExisteix(autor, titol);
        return findDocument(autor,titol).getCont();
    }

    /**
     *
     * @param autor Autor del que vols saber els titols dels seus documents
     * @param ordre Ordre en que vols que es llisti
     * @return Tots els títols de l autor amb nom = autor
     */
    public String[] llistarTitolsAutor(String autor, String ordre) {

        TreeSet<String> titols = new TreeSet<String>();

        for (IDdocument idDoc : CjtDocuments.keySet()) {
            if (idDoc.first().equals(autor)) titols.add(idDoc.second());
        }
        return o.ordenarStrings(titols, ordre);
    }

    /**
     *
     * @param autor Autor del document pel que es comprovara l expressio
     * @param titol Titol del document pel que es comprovara l expressio
     * @param expressio String amb l expressio a comprovar
     * @return Cert si el document compleix l expressio, fals altrament
     */
    public boolean llistarDocumentsPerExpressio(String autor, String titol, String expressio) {
        boolean result = false;
        Document doc = CjtDocuments.get(new IDdocument(autor, titol));
        String cont = doc.getCont();
        Node exBool = new Node(expressio);
        GestorArbre GA = new GestorArbre();
        if (GA.gestionarArbreBoolea(exBool, cont)) result = true;
        return result;
    }

    /**
     * @param autor nom de l autor del document que vol ser comparat amb la resta
     * @param titol nom del titol del document que vol ser comparat amb la resta
     * @param k valor enter que identifica el nombre de documents que volen ser llistats
     * @param ordre Ordre en que es volen llistar els documents
     * @return La informacio dels k documents mes semblants al document especificat com a parametre
     */
    public IDdocument[] llistarDocsemblants(String autor, String titol, Integer k, String ordre) {
        //if (!existDocument(autor,titol)) throw new DocumentNoExisteix(autor, titol);
        Document d = findDocument(autor, titol);
        Map<IDdocument, Double> result = structure.compararDocs(d, k);
        TreeSet<IDdocument> resultSet = new TreeSet<>(result.keySet());

        return o.ordenarDocuments(resultSet, ordre);
    }


    /**
     * @param titol titol a comprovar
     * @param autor autor a comprovar
     * @return cert si el títol i l autor no són buits, fals altrament
     */
    private boolean comprovarTitolAutor(String titol, String autor) {
        return (comprovarParametre(autor) | comprovarParametre(titol));
    }

    /**
     * @param parametre parametre a comprovar
     * @return retorna cert si el títol no és buit
     */
    private boolean comprovarParametre(String parametre) {
        return (parametre == null | parametre.length() == 0);
    }


    /**
     *
     * @param ordre ordre en que es volen llistar
     * @return autor i titol de tots els documents del sistema
     */
    public IDdocument[] llistarTotsDocs(String ordre) {
        TreeSet<IDdocument> resultSet = new TreeSet<>(CjtDocuments.keySet());
        IDdocument[] ordenat = o.ordenarDocuments(resultSet, ordre);
        for (int i = 0; i < ordenat.length; i++) {
            System.out.println("Autor: " + ordenat[i].first() + " Títol: " + ordenat[i].second());
        }
        return ordenat;
    }

    /**
     * Buida el contingut de un document per tal que no estiguin sempre carregats a memoria de programa
     * @param autor Autor del document
     * @param titol Titol del document
     * @throws NullPointerException Salta si no existeix el document
     */
    public void buidarContingut(String autor, String titol) throws NullPointerException {
        Document doc = findDocument(autor, titol);
        doc.editarContingut("");
    }

    /**
     *
     * @return Autor i titol de tots els documents
     */
    public IDdocument[] getIdDocuments() {
        IDdocument[] ids = new IDdocument[CjtDocuments.size()];
        int i = 0;
        for (IDdocument id:CjtDocuments.keySet()) {
            ids[i] = id;
            ++i;
        }
        return ids;
    }

    /**
     *
     * @param idDoc Autor i titol del document al que volem posar el contingut
     * @param contingut Contingut del document que volem carregar a memòria de programa
     * @throws NullPointerException Salta si no existeix el document
     */
    public void afegirContTemp(IDdocument idDoc, String contingut) throws NullPointerException {
        Document doc = findDocument(idDoc.first(), idDoc.second());
        doc.editarContingut(contingut);
    }

    /**
     *
     * @param idDocsCompleixen Conjunt de autors i titols de documents
     * @param ordre ordre en que es volen llistar
     * @return els documents de idDocsCompleixen en l ordre correcte
     */
    public IDdocument[] ordenarSetId(TreeSet<IDdocument> idDocsCompleixen, String ordre) {
        IDdocument[] idDocsOrdre = o.ordenarDocuments(idDocsCompleixen,ordre);
        for (int i = 0; i < idDocsOrdre.length; i++) {
            System.out.println("Autor: " + idDocsOrdre[i].first() + " Títol: " + idDocsOrdre[i].second());
        }
        return idDocsOrdre;
    }

}
