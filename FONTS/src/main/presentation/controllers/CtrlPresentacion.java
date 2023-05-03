package presentation.controllers;

import domain.classes.exceptions.*;
import domain.controllers.CtrlDomain;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;
import persistence.controllers.CtrlPersistence;
import presentation.view.*;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class CtrlPresentacion {

        private static CtrlDomain ctrlDomain;

        private static CtrlPresentacion instance = new CtrlPresentacion();

        /**
         * Constructora del controlador de presentació.
         *
         * @throws Exception si es produeix un error en crear gestor.
         */
        private CtrlPresentacion() {
                ctrlDomain = ctrlDomain.getInstance();
                try {
                    ctrlDomain.inicialitzarAplicacio();
                } catch (DocumentNoTrobat pne) {}
        }

        public static CtrlPresentacion getInstance() {
                return instance;
        }

        /**
         * Fa visible la vista principal.
         */
        //-------------- VISTES ---------------
        public static void OpenViewPrincipal() throws Exception {
                ViewMenuPrincipal vMP = new ViewMenuPrincipal();
        }

        public static void vistaCreacioDocument() {
                ViewCrearDoc vCD = new ViewCrearDoc();
        }

        public static void vistaObrirDocument(Integer k) {
                ViewSeleccionarDoc vOD = new ViewSeleccionarDoc(k);
        }

        public static void vistaEliminarDocument() {
                ViewEliminarDocument vED = new ViewEliminarDocument();
        }

        public static void vistaConvertirDocument() {
                ViewConvertirDocument vCD = new ViewConvertirDocument();
        }

        public static void vistaDocsemblants() {
                ViewDocSemblants vDS = new ViewDocSemblants();
        }

        public static void vistaAutoryTitol() {
                ViewAutoryTitol vAT = new ViewAutoryTitol();
        }

        public static void vistaTitols() {
                ViewTitolsdunAutor vT = new ViewTitolsdunAutor();
        }

        public static void vistaAutorPrefix() {
                ViewAutorPrefix vAP = new ViewAutorPrefix();
        }

        public static void vistaGestionarExpressio() {
                String[] expresiones = ctrlDomain.getExpressionsBooleanes();
                ViewGestionarExpressio vL = new ViewGestionarExpressio(expresiones);
        }

        public static void vistaImportarDocument() {
                ViewImportar vI = new ViewImportar();
        }

        public static void vistaLListarPerExpressio() {
                String[] expresiones = ctrlDomain.getExpressionsBooleanes();
                ViewLlistarPerExpressio vLlE = new ViewLlistarPerExpressio(expresiones);
        }

        public static void vistaLlistar() {
                String[] info = ctrlDomain.llistarTotsDocs("a");
                ViewLlistar vLlE = new ViewLlistar("Documents existents en el sistema:", info);
        }

        public static void vistaEditarDoc(String autor, String titol, String contingut) {
                ViewEditarDocument vED = new ViewEditarDocument(autor, titol, contingut);
        }

        public static void vistaMostrarDoc(String autor, String titol, String contingut) {
                ViewMostraDocument vMD = new ViewMostraDocument(autor,titol,contingut);
        }

        //-------------- METODES ----------------

        public static void crearDoc(String autor, String titol, String contingut, String format) throws
                JaExisteixException, NullException, InformacioErroneaException, DocumentNoTrobat {
                ctrlDomain.crearDoc(autor, titol, contingut, format);
        }

        public static void editarDoc(String autor, String nouautor, String titol, String noutitol,
                                     String contingut, String noucontingut) throws NoExisteixException,
                                        DocumentNoExisteix, JaExisteixException, NullException, DocumentNoTrobat {
                ctrlDomain.editarDoc(autor, nouautor, titol, noutitol, contingut, noucontingut);
        }

        public static void convertirDoc(String autor, String titol, String format) throws DocumentNoTrobat {
                ctrlDomain.exportarFormatDocument(autor, titol, format);
        }

        public static String llistarDocumentPerTitolAutor(String autor, String titol) throws DocumentNoTrobat, NullException {
                return ctrlDomain.llistarDocumentPerTitolAutor(autor, titol);
        }

        public static String[] llistarAutorPrefix(String prefix, String ordre) {
                String autors[] = ctrlDomain.llistarAutorPrefix(prefix, ordre);
                return autors;
        }

        public static String[] llistarTitolsAutor(String autor, String ordre) {
                String titols[] = ctrlDomain.llistarTitolsAutor(autor, ordre);
                return titols;
        }

        public static String[] llistarDocSemblants(String autor, String titol, Integer k, String ordre)
                throws DocumentNoTrobat, NullPointerException {
                String[] identificadorDocs = new String[0];
                identificadorDocs = ctrlDomain.llistarDocSemblants(autor, titol, k, ordre);
                return identificadorDocs;
        }

        public static String[] crearExpressio(String expressio) {
                return ctrlDomain.crearExpressio(expressio);
        }

        public static String[] eliminarExpressio(String expressio) {
                return ctrlDomain.eliminaExpressio(expressio);
        }

        public static String[] modificarExpressio(String expressio, String novaexpressio) {
                return ctrlDomain.modificaExpressio(expressio, novaexpressio);
        }

        public static void eliminarDocument(String autor, String titol) throws DocumentNoExisteix, DocumentNoTrobat {
                ctrlDomain.borrarDocument(autor, titol);
        }

        public static void importarDocument(String path) throws DocumentNoTrobat,
                NullException, InformacioErroneaException, JaExisteixException, InformacioBuida {
                ctrlDomain.importarDocumnet(path);
        }

        public static String[] llistarDocumentsPerExpressio(String expressio, String ordre) throws DocumentNoTrobat {
                String[] doc = ctrlDomain.llistarDocumentsPerExpressio(expressio, ordre);
                return doc;
        }

        public static void tancarAplicacio() throws ArrayIndexOutOfBoundsException {
                ctrlDomain.finalitzarAplicacio();
                System.exit(0);
        }

        public static void guardarInfo() {
                ctrlDomain.guardarInformacio();
        }

        public static void eliminarDocPath(String path) throws DocumentNoExisteix, DocumentNoTrobat, InformacioBuida {
                ctrlDomain.borrarDocument(path);
        }

        public static String[] llistarDocSemblantsPath(String path, Integer k, String ordre) throws DocumentNoTrobat,
                InformacioBuida {
                return ctrlDomain.llistarDocSemblants(path,k,ordre);
        }

        public static String seleccionarDocumentPath(String path) throws DocumentNoTrobat, InformacioBuida {
                return ctrlDomain.getTotaInfo(path);
        }

        public static void convertirDocPath(String path, String format) throws DocumentNoTrobat, InformacioBuida {
                ctrlDomain.exportarFormatDocument(path,format);
        }

        public static String[] getautors() {
                return ctrlDomain.getAutors();
        }

}
