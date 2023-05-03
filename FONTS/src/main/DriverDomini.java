import domain.classes.exceptions.*;
import domain.controllers.CtrlDomain;
import persistence.classes.exceptions.DocumentNoTrobat;

import java.util.Scanner;

/**
 * Aquest Driver serveix per poder provar el sistema en general utilitzant una interficie 
 * per poder executar els metodes proporcionats pel CtrlDomini <p>
 * Per poder executar tots els metodes es requereix la creacio del CtrlDomini e inclou la creacio
 * dels objectes necessaris.
 *
 * @author Jaume Alos Cuadrado (jaume.alos.cuadrado@estudiantat.upc.edu)
 */

public class DriverDomini {
    private CtrlDomain cd = null;
    private Scanner in;

    /**
     * Crea el CtrlDomain.
     */
    private void testConstructora() {
        try {
            cd = cd.getInstance();
            cd.inicialitzarAplicacio();
        } catch (DocumentNoTrobat pne) {}
    }
    /**
     * @return el titol que s ha introduit per a un document
     */
    private String introdueixTitol (){

        System.out.println("Introdueix el títol");
        Scanner entrada = new Scanner(System.in);
        String titol = entrada.nextLine();
        return titol;
    }

    /**
     * @return l autor que s ha introduit per a un document.
     */
    private String introdueixAutor (){

        System.out.println("Introdueix l'Autor");
        Scanner entrada = new Scanner(System.in);
        String autor = entrada.nextLine();
        return autor;
    }
    private String introdueixPath (){

        System.out.println("Introdueix el path");
        Scanner entrada = new Scanner(System.in);
        String path = entrada.nextLine();
        return path;
    }

    /**
     * @return el contingut que s ha introduit per a un document.
     */
    private String introdueixContingut (){

        System.out.println("Introdueix el contingut");
        System.out.println(" -Per finalitzar, prem la tecla Enter i a continuacio introdueix:':quit'");
        String cont = "";
        Scanner entrada = new Scanner(System.in);
        String frase = entrada.nextLine();
        while(!frase.contains(":quit")) {
            cont += frase + "\r\n";
            frase = entrada.nextLine();
        }
        return cont;
    }

    private String introdueixFormat() {
        System.out.println("Si el teu document sera un fitxer de text introdueix: txt");
        System.out.println("Si el teu document sera un fitxer xml introdueix: xml");
        Scanner entrada = new Scanner(System.in);
        String format = entrada.nextLine();
        return format;
    }

    /**
     * @return el prefix introduit
     */
    private String introdueixPrefix (){

        System.out.println("Introdueix el prefix");
        Scanner entrada = new Scanner(System.in);
        String prefix = entrada.nextLine();
        return prefix;
    }

    private String introdueixOrdre (){
        System.out.println("Ordre (a-z) introdueixi: a");
        System.out.println("Ordre (z-a) introdueixi: d");
        Scanner entrada = new Scanner(System.in);
        String ordre = entrada.nextLine();
        return ordre;
    }

    /**
     * @return el nombre de documents a llistar
     */
    private Integer introdueixNumeroDocuments (){

        System.out.println("Introdueix el número");
        Scanner entrada = new Scanner(System.in);
        Integer k = Integer.valueOf(entrada.nextLine());
        return k;
    }

    /**
     * @return l expressio booleana introduida
     */
    private String introdueixExpressioBoleana (){

        System.out.println("Introdueix l'expressió boleana");
        Scanner entrada = new Scanner(System.in);
        String expBool = entrada.nextLine();
        return expBool;
    }
    /**
     * @return l expressio booleana introduida
     */
    private String introdueixOpcio (){

        System.out.println("Introdueix Opció");
        Scanner entrada = new Scanner(System.in);
        String opcio = entrada.nextLine();
        return opcio;
    }
    /**
     * Crea el document que té com a clau el títol i l'autor i com a valor el contingut introduit.
     */
    private void testCrearDoc() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        String contingut = introdueixContingut();
        String format = introdueixFormat();

        try {
            cd.crearDoc(autor, titol, contingut, format);
        } catch (Exception e) {
            System.out.println("Ha saltat una excepció");
        }
    }


    /**
     * Esborra definitivament el document que té com a clau el títol i l'autor introduit.
     */
    private void testBorrarDocument() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        try {
            cd.borrarDocument(autor,titol);
        } catch (Exception e) {

        }
    }

    /**
     * Editem el contingut del document que té com a clau el títol i l'autor introduçit.
     */
    private void testEditarContenido() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        String contingut = introdueixContingut();
        try {
            cd.editarContingut(autor,titol,contingut);
        } catch (DocumentNoTrobat e) {
        }
    }
    /**
     * Editem l'autor del document que té com a clau el títol i l'autor introduit.
     */
    private void testEditarTitol() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        String nouTitol = introdueixTitol();
        try {
            cd.editarTitol(autor,titol,nouTitol);
        } catch (DocumentNoTrobat e) {
        } catch (JaExisteixException e) {
        } catch (NullException e) {
        } catch (DocumentNoExisteix e) {
        }
    }

    /**
     * Editem l'autor del document que té com a clau el títol i l'autor introduit.
     */
    private void testEditarAutor() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        String nouAutor = introdueixAutor();

        try {
            cd.editarAutor(autor,titol,nouAutor);
        } catch (DocumentNoTrobat e) {
        } catch (DocumentNoExisteix e) {
        } catch (NullException e) {
        }
    }

    /**
     * Convertim un document al format demanat per l'usuari
     */
    private void testExportarFormatDocument() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        String format = introdueixFormat();
        try {
            cd.exportarFormatDocument(autor, titol, format);
        } catch(DocumentNoTrobat pne) {}
    }

    /**
     * Creem l'expressio introduida.
     */
    private void testCrearExpressio() {
        String expressio = introdueixExpressioBoleana();
        cd.crearExpressio(expressio);
    }

    /**
     * Eliminem l'expressio introduida.
     */
    private void testEliminarExpressio() {
        String expressio = introdueixExpressioBoleana();
        cd.eliminaExpressio(expressio);
    }

    /**
     * Editem l'expressio per la nova expressio introduida.
     */
    private void testModificarExpressio() {
        String expressio = introdueixExpressioBoleana();
        String novaexpressio = introdueixExpressioBoleana();
        cd.modificaExpressio(expressio, novaexpressio);
    }

    private void testImportarDoc() {

        String path = introdueixPath();
        try {
            cd.importarDocumnet(path);
        } catch (Exception e) {
            System.out.println("Ha hagut algun error");
        }
    }

    /**
     * Llistem el document que té com a clau el títol i l'autor introduit.
     */
    private void testLlistarDocumentPerTitolAutor() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        try {
            cd.llistarDocumentPerTitolAutor(autor,titol);
        } catch (Exception e) {
            System.out.println("El document no existeix");
        }
    }

    /**
     * Llistem tots els titols de l'autor que s'introdueix.
     */
    private void testllistarTitolsAutor() {
        String autor = introdueixAutor();
        String ordre = introdueixOrdre();
        cd.llistarTitolsAutor(autor, ordre);
    }

    /**
     * Llistem tots els autors que comencin pel prefix introduit.
     */
    private void testLlistarAutorPrefix() {
        String prefix = introdueixPrefix();
        System.out.println("Ordre (a-z) introdueixi: a");
        System.out.println("Ordre (z-a) introdueixi: d");
        String opcio = introdueixOpcio();
        cd.llistarAutorPrefix(prefix, opcio);
    }

    /**
     * Llistem els documents més semblants al document introduit.
     */
    private void testLlistarDocSemblants() {
        String autor = introdueixAutor();
        String titol = introdueixTitol();
        int k = introdueixNumeroDocuments();
        System.out.println("Ordre (a-z) introdueixi: a");
        System.out.println("Ordre (z-a) introdueixi: d");
        String ordre = introdueixOpcio();
        if(k>0) {
            try {
                cd.llistarDocSemblants(autor,titol,k, ordre);
            } catch (DocumentNoTrobat e) {
                System.out.println("path no existeix");
            }
        }
    }

    /**
     * Llistem els documents que compleixen l expressio introduida.
     */

    private void testLlistarDocumentsPerExpressio() {
        String expBool = introdueixExpressioBoleana();
        System.out.println("Ordre (a-z) introdueixi: a");
        System.out.println("Ordre (z-a) introdueixi: d");
        String ordre = introdueixOpcio();
        try {
            cd.llistarDocumentsPerExpressio(expBool, ordre);
        } catch (DocumentNoTrobat e) {
            System.out.println("expressio mal escrita");
        }
    }

    private void testLlistarTotsDocuments() {
        System.out.println("Ordre (a-z) introdueixi: a");
        System.out.println("Ordre (z-a) introdueixi: d");
        String ordre = introdueixOpcio();
        cd.llistarTotsDocs(ordre);
    }

    private void testFinalitzarAplicacio() {
        try {
            cd.finalitzarAplicacio();
        } catch (ArrayIndexOutOfBoundsException aioobe) {}
    }
    private void testGuardarInformacio() {
        cd.guardarInformacio();
    }



    /**
     * Representa el panell d'ajuda.
     * */
    private static void mostra_metodes() {
        System.out.println("Funcionalitats:");
        System.out.println("-------------------------------------------------------");
        System.out.println("(1|crearDoc) - Crear Document");
        System.out.println("(2|borrarDocDefinitivament) - Esborrar document definitivament");
        System.out.println("(3|editarContingut) - Editar el contingut del Document");
        System.out.println("(4|editarTitol) - Editar el titol del Document");
        System.out.println("(5|editarAutor) - Editar l'autor del Document");
        System.out.println("(6|exportarFormatDocument) - Converteix un document al format demanat");
        System.out.println("(7|crearExpressio) - Crear Expressió booleana");
        System.out.println("(8|eliminaExpressio) - Elimina Expressió booleana");
        System.out.println("(9|modificaExpressio) - Modifica una Expressio booleana");
        System.out.println("(10|importarDoc) - Importa un Document");
        System.out.println("(11|llistarDocumentPerTitolAutor) - Llistar un document per titol i autor");
        System.out.println("(12|llistarTitolsAutor) - Llistar els Titols d'un Autor");
        System.out.println("(13|llistarAutorPrefix) - Llistar els Autors que començen per un prefix");
        System.out.println("(14|llistarDocSemblants) - Llistar documents semblants");
        System.out.println("(15|llistarDocumentsPerExpressio) - Llistar documents per expressió");
        System.out.println("(16|llistarTotsDocs) - Llistar tots els documents del sistema");
        System.out.println("(17|guardarInformacio) - Guarda la informació de l'aplicació per tal de poder-la recuperar");
        System.out.print("\n");
        System.out.println("(0|sortir) - Tancar Driver");
        System.out.println("-------------------------------------------------------");
    }

    /**
     * Representa el panell de menu per poder sortir de l'aplicació o tornar al menu principal.
     * */
    private void tornarMenu() {
        System.out.println("Prem ENTER per tornar al menu principal");
        in.nextLine();
    }

    /**
     * @throws Exception
     */
    public static void main (String [] args) throws Exception {
        DriverDomini dd = new DriverDomini();
        System.out.println("Driver Principal Domini (PROP Grup 42.1)");
        System.out.println("");
        mostra_metodes();
        dd.in = new Scanner(System.in);
        String input = dd.in.nextLine();
        dd.testConstructora();

        while (!input.equals("0") && !input.equals("sortir")) {
            switch (input) {

                case "1":
                case "crearDoc": {
                    dd.testCrearDoc();
                    break;
                }

                case "2":
                case "borrarDocDefinitivament": {
                    dd.testBorrarDocument();
                    break;
                }

                case "3":
                case "editarContenido": {
                    dd.testEditarContenido();
                    break;
                }

                case "4":
                case "editarTitol": {
                    dd.testEditarTitol();
                    break;
                }

                case "5":
                case "editarAutor": {
                    dd.testEditarAutor();
                    break;
                }

                case "6":
                case "exportarFormatDocument": {
                    dd.testExportarFormatDocument();
                    break;
                }

                case "7":
                case "crearExpressio": {
                    dd.testCrearExpressio();
                    break;
                }

                case "8":
                case "eliminaExpressio": {
                    dd.testEliminarExpressio();
                    break;
                }

                case "9":
                case "modificaExpressio": {
                    dd.testModificarExpressio();
                    break;
                }
                case "10":
                case "importarDoc": {
                    dd.testImportarDoc();
                    break;
                }

                case "11":
                case "listDocumentPerTitolAutor": {
                    dd.testLlistarDocumentPerTitolAutor();
                    break;
                }
                case "12":
                case "llistarTitolsAutor": {
                    dd.testllistarTitolsAutor();
                    break;
                }
                case "13":
                case "llistarAutorPrefix": {
                    dd.testLlistarAutorPrefix();
                    break;
                }
                case "14":
                case "llistarDocSemblants": {
                    dd.testLlistarDocSemblants();
                    break;
                }
                case "15":
                case "llistarDocumentsPerExpressio": {
                    dd.testLlistarDocumentsPerExpressio();
                    break;
                }
                case "16":
                case "llistarTotsDocuments": {
                    dd.testLlistarTotsDocuments();
                    break;
                }
                case "17":
                case "guardarInformacio": {
                    dd.testGuardarInformacio();
                    break;
                }
                default:
                    break;
            }

            System.out.println("\r\n");
            dd.tornarMenu();
            mostra_metodes();
            input = dd.in.nextLine();
        }
        dd.testFinalitzarAplicacio();
        dd.in.close();
    }


}