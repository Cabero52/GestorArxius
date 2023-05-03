package persistence.classes;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;

import java.util.TreeMap;
import java.io.*;
import java.util.TreeSet;

/**
 * @author Marc Cabero Armengol(marc.armengol.cabero@estudiantat.upc.edu)
 */

public class ConjuntPaths {

    public ConjuntPaths(){}
    /**
     *  Map que conté el id de un document (en forma de Autor_Titol) com a clau i el seu path com a valor
     */
    private TreeMap<String, String> CjtPaths = new TreeMap<>();

    private String pathGeneral = "../../Documents/";


    private boolean existsPath(String autor, String titol) {
        return CjtPaths.containsKey(autor + "_" + titol);
    }

    /**
     * @param autor Autor del document del que es vol consultar el path
     * @param titol Titol del document del que es vol consultar el path
     * @return Retorna el path del document amb autor = autor i titol = titol
     */
    public String getPath(String autor, String titol) {
        return CjtPaths.get(autor + "_" + titol);
    }

    /**
     * @param autor Autor del document del que vols afegir el path al conjunt
     * @param titol Titol del document del que vols afegir el path al conjunt
     * @param format Format del document del que vols afegir el path al conjunt
     * Afegeix un document amb el seu path al conjunt de paths
     */
    public void afegirPath(String autor, String titol, String format) {
        String path = pathGeneral + autor + "_" + titol + "." + format;
        CjtPaths.put(autor + "_" + titol, path);
    }

    /**
     * @param info String que conté tota la informació necessària per introduir un path al sistema
     *             en el següent format: Autor_del_document:titol:Titol_del_document:path:Path_del_document
     * Aquesta funció s'utilitza per a restaurar l'arxiu propi
     */
    public void afegirPath(String info) {
        String[] autorTitol_path = info.split(":path:");
        String[] autor_titol = autorTitol_path[0].split(":titol:");

        CjtPaths.put(autor_titol[0] + "_" + autor_titol[1], autorTitol_path[1]);
    }

    /**
     * @param path Path del document
     * @return Informació del document en format: Autor_doc:titol:Titol_doc:cont:
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws InformacioBuida Salta si els parametres donats estan buits
     */
    public String getTotaInfo(String path) throws DocumentNoTrobat, InformacioBuida {
        String titol = getTitol(path);
        String autor = getAutor(path);
        String contingut = getContingut(autor, titol);
        return autor + ":titol:" + titol + ":cont:" + contingut;
    }

    /**
     * @param autor autor del document del que vols obtenir el contingut
     * @param titol titol del document del que vols obtenie el contingut
     * @return Retorna el contingut del document amb titol = titol i autor = autor
     * @throws DocumentNoTrobat Si aquest document no està introduit al sistema, no existeix el seu path,
     *                        per tant es llança l'excepció DocumentNoTrobat
     */
    public String getContingut(String autor, String titol) throws DocumentNoTrobat {
        try {
            String path = getPath(autor, titol);

            String cont = "";
            if (esTXT(path)) {
                cont = getContTxt(path);
            }
            else if (esXML(path)) {
                cont = getContXml(path);
            }

            return cont;
        } catch (NullPointerException npe) {
            throw new DocumentNoTrobat(autor + "_" + titol);
        }
    }

    /**
     * @param path path del document del que es vol obtenir el contingut
     * @return Retorna el contingut del document amb titol = titol i autor = autor
     * @throws DocumentNoTrobat Si aquest document no està introduit al sistema, no existeix el seu path,
     *                        per tant es llança l'excepció DocumentNoTrobat
     * Aquesta funcio s usa nomes per a arxius xml
     */
    private String getContXml(String path) throws DocumentNoTrobat {
        try {
            FileReader lectorArxiu = new FileReader(path);
            BufferedReader entrada = new BufferedReader(lectorArxiu);

            String lectura = entrada.readLine();
            String contingut = "";
            int iteracio = 0;
            while (lectura != null) {
                if (iteracio > 2) contingut += (lectura + "\n");
                lectura = entrada.readLine();
                ++iteracio;
            }
            return contingut.substring(15,contingut.length()-20);
        } catch(IOException ioe) {
            throw new DocumentNoTrobat(path);
        }
    }

    /**
     * @param path path del document del que es vol obtenir el contingut
     * @return Retorna el contingut del document amb titol = titol i autor = autor
     * @throws DocumentNoTrobat Si aquest document no està introduit al sistema, no existeix el seu path,
     *                        per tant es llança l'excepció DocumentNoTrobat
     * Aquesta funcio s usa nomes per a arxius txt
     */
    private String getContTxt(String path) throws DocumentNoTrobat {
        try {
            FileReader lectorArxiu = new FileReader(path);
            BufferedReader entrada = new BufferedReader(lectorArxiu);

            String lectura = entrada.readLine();
            String contingut = "";
            int iteracio = 0;

            while (lectura != null) {
                if(iteracio>1)contingut += (lectura + "\n");
                lectura = entrada.readLine();
                ++iteracio;
            }
            return contingut;
        } catch(IOException ioe) {
            throw new DocumentNoTrobat(path);
        }
    }

    /**
     * @param path path del document
     * @return Retorna cert si el document és un arxiu txt
     */
    private boolean esTXT(String path) {
        return path.contains(".txt");
    }

    /**
     * @param path path del document
     * @return Retorna cert si el document és un arxiu xml
     */
    private boolean esXML(String path) {
        return path.contains(".xml");
    }

    /**
     * @return retorna un conjunt de documents amb el format autor_doc:titol:titol_doc:path:pat_doc:fiDocu:
     */
    public TreeSet<String> getDocuments() {
        TreeSet<String> docs = new TreeSet<>();
        if (CjtPaths.size() > 0) {
            for (String idDoc : CjtPaths.keySet()) {
                String[] autorTitol = idDoc.split("_");
                String doc = autorTitol[0] + ":titol:" + autorTitol[1] + ":path:" + CjtPaths.get(idDoc) + ":fiDocu:";
                docs.add(doc);
            }
        }
        return docs;
    }

    /**
     * @param infoDoc Informacio del document
     * @return Retorna el document en format autor_doc:titol:titol_doc:path:path_doc
     * @throws DocumentNoTrobat Salta si no es troba el document
     */
    public String getDocumentsPerPath(String infoDoc) throws DocumentNoTrobat {
        String informacioDoc;

        String[] autorSeparat = infoDoc.split(":titol:"); //autorSeparat[0] = autor
        String[] titolPath = autorSeparat[1].split(":path:"); //titolPath[0] = titol ; [1] = path
        String contingut = getContingut(autorSeparat[0], titolPath[0]);
        informacioDoc = autorSeparat[0] + ":titol:" + titolPath[0] + ":cont:" + contingut;

        return informacioDoc;
    }

    /**
     * @param path Path del document a importar
     * @return Document amb path = path en el format autor_doc:titol:titol_doc:cont:contingut_doc
     * @throws DocumentNoTrobat Salta si no troba el document
     * @throws InformacioBuida Salta si el paràmetre path està buit
     */
    public String importarDocument(String path) throws DocumentNoTrobat, InformacioBuida {
        String autor = getAutor(path);
        String titol = getTitol(path);
        try {
            CjtPaths.put(autor + "_" + titol, path);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DocumentNoTrobat(path);
        }
        return autor + ":titol:" + titol + ":cont:" + getContingut(autor, titol);
    }

    /**
     * @param path Path del document especificat
     * @return El titol del document especificat com a parametre
     * @throws InformacioBuida Salta si el path esta buit
     */
    private String getTitol(String path) throws InformacioBuida {
        String titol = "";
        try {
            FileReader lectorArxiu = new FileReader(path);
            BufferedReader entrada = new BufferedReader(lectorArxiu);
            entrada.readLine();
            titol = entrada.readLine();
            if(esXML(path)) {
                titol = entrada.readLine();
                titol = titol.substring(10,titol.length()-9);
            }

        } catch (IOException ioe) {

        } catch (NullPointerException npe) {
            throw new InformacioBuida(path);
        }
        System.out.println("titol document importat"+titol);
        return titol;
    }

    /**
     * @param path Path del document especificat
     * @return El autor del document especificat com a parametre
     * @throws InformacioBuida Salta si no s'introdueix la informacio
     */
    private String getAutor(String path) throws InformacioBuida {
        String autor = "";
        try {
            FileReader lectorArxiu = new FileReader(path);
            BufferedReader entrada = new BufferedReader(lectorArxiu);
            autor = entrada.readLine();
            if(esXML(path)) {
                autor = entrada.readLine();
                autor = autor.substring(10,autor.length()-9);
            }
        } catch (IOException ioe) {

        } catch (NullPointerException npe) {
            throw new InformacioBuida(path);
        }

        return autor;
    }

    /**
     * @param path Path del document a eliminar
     * @throws DocumentNoTrobat salta si el document no existeix
     */
    public void eliminarPath(String path) throws DocumentNoTrobat {
        try {
            CjtPaths.remove(path);
        } catch (NullPointerException npe) {
            throw new DocumentNoTrobat(path);
        }
    }

    /**
     * @param path Path del document especificat
     * @return La informacio indicant si s'ha esborrat el document especificat
     * @throws DocumentNoTrobat salta si el document no existeix
     * @throws InformacioBuida salta si no s'especifiquen parametres
     */
    public String deleteDocument(String path) throws DocumentNoTrobat, InformacioBuida {
        String info = getAutor(path) + "_" + getTitol(path);
        eliminarPath(info);
        info = info.replace("_", ":titol:");

        if(esborrarDocument(path)) System.out.println("El document s'ha esborrat");
        return info;
    }

    /**
     *
     * @param path Path del document a esborrar
     * @return Cert si s esborra el document amb èxit, fals si no
     */
    private boolean esborrarDocument(String path) {
        File doc = new File(path);
        return doc.delete();
    }

    /**
     *
     * @param path Path del document
     * @return Contingut del document emmagatzemat al path passat per parametre
     * @throws DocumentNoTrobat Salta si no es troba el document
     * @throws InformacioBuida Salta si el path es buit
     */
    public String getContingut(String path) throws DocumentNoTrobat, InformacioBuida {
        return getContingut(getAutor(path), getTitol(path));
    }

    /**
     * @param autor Autor del document a editar
     * @param titol Titol del document a editar
     * @param nouTitol Nou autor del document a editar
     * @throws DocumentNoTrobat Salta si no troba el document amb autor = autor i titol = titol
     */
    public void editarTitol(String autor, String titol, String nouTitol) throws DocumentNoTrobat {
        String path = CjtPaths.get(autor+"_"+titol);
        eliminarPath(autor + "_" + titol);
        afegirPath(autor+ ":titol:" + nouTitol + ":path:" + path);
    }

    /**
     * @param autor Autor del document a editar
     * @param titol Titol del document a editar
     * @param nouAutor Nou autor del document a editar
     * @throws DocumentNoTrobat Salta si no troba el document amb autor = autor i titol = titol
     */
    public void editarAutor(String autor, String titol, String nouAutor) throws DocumentNoTrobat{
        String path = CjtPaths.get(autor+"_"+titol);
        eliminarPath(autor + "_" + titol);
        afegirPath(autor+ ":titol:" + nouAutor + ":path:" + path);
    }
}
