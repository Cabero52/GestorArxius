package presentation.view;

import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author Jaume Alos Cuadrado(jaume.alos@estudiantat.upc.edu)
 */

public class ViewMenuPrincipal extends JFrame{
    private CtrlPresentacion cp = CtrlPresentacion.getInstance();

    private final String WARNING = "WARNING";

    private final JButton bCrearDoc = new JButton("Crear Document");
    private final JButton bEliminarDoc = new JButton("Eliminar Document");
    private final JButton bEditarDoc = new JButton("Editar Document");
    private final JButton bConvertirFormat = new JButton("Convertir Format");
    private final JButton bLlistarDocSemblants = new JButton("Llistar Documents Semblants");
    private final JButton bLlistarperAutorTitol = new JButton("Llistar per Autor i Titol");
    private final JButton bLlistarDocExpresio = new JButton("Llistar per Expressió Boleana");
    private final JButton bLlistarTitolsdunAutor = new JButton("Llistar Titols d'un Autor");
    private final JButton bLlistarAutorPrefix = new JButton("Llistar Autors per Prefix");
    private final JButton bGestionarExpressio = new JButton("Gestionar Expressió");
    private final JButton bImportarDocument = new JButton("Importar Document");
    private final JButton bListartodosDocumentos = new JButton("Llistar tots els Documents");
    private final JButton bObrir = new JButton("Obrir Document");
    private final JButton bmanualUsuari = new JButton("Manual d'Usuari");
    private final JButton bGuardar = new JButton("GUARDAR");
    private final JButton bSortir = new JButton("SORTIR ");

    private final JPanel panell = new JPanel();
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final JFrame frame = new JFrame();

    public ViewMenuPrincipal() throws Exception {
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        panell.setLayout(new GridLayout(4,6));

        setTitle("GESTOR D'ARXIUS");

        panell.add(bCrearDoc,constraints);

        panell.add(bEditarDoc,constraints);

        panell.add(bEliminarDoc,constraints);

        panell.add(bConvertirFormat,constraints);

        panell.add(bLlistarperAutorTitol,constraints);

        panell.add(bLlistarDocSemblants,constraints);

        panell.add(bLlistarDocExpresio,constraints);

        panell.add(bLlistarTitolsdunAutor,constraints);

        panell.add(bLlistarAutorPrefix,constraints);

        panell.add(bGestionarExpressio,constraints);

        panell.add(bImportarDocument,constraints);

        panell.add(bListartodosDocumentos,constraints);

        panell.add(bObrir,constraints);

        panell.add(bmanualUsuari,constraints);

        panell.add(bGuardar,constraints);

        Color red= new Color(255,105,97);
        bSortir.setBackground(red);
        panell.add(bSortir,constraints);

        add(panell);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame,
                        "Estas segur que vols tancar la app?",
                        "Confirmació de tancament",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    cp.guardarInfo();
                    System.exit(0);
                }
            }
        });

        /**
         * Gestiona el tractament del Boto CrearDocument
         */
        ActionListener crearDocument = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 cp.vistaCreacioDocument();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto editarDocument
         */
        ActionListener editarDocument = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaObrirDocument(1);
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto eliminarDocument
         */
        ActionListener eliminarDocument = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaEliminarDocument();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto convertirFormat
         */
        ActionListener convertirFormat = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaConvertirDocument();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto llistarDocSemblants
         */
        ActionListener llistarDocSemblants = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaDocsemblants();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto llistarPerAutorTitol
         */
        ActionListener llistarPerAutorTitol = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaAutoryTitol();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto llistarDocExpresio
         */
        ActionListener llistarDocExpresio = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaLListarPerExpressio();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto llistarTitolsdunAutor
         */
        ActionListener llistarTitolsdunAutor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 cp.vistaTitols();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto llistarAutorPrefix
         */
        ActionListener llistarAutorPrefix = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.vistaAutorPrefix();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto gestionarExpressio
         */
        ActionListener gestionarExpressio = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.vistaGestionarExpressio();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto importarDocument
         */
        ActionListener importarDocument = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.vistaImportarDocument();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto llistartotselsdocuments
         */
        ActionListener llistartotselsdocuments = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.vistaLlistar();
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto obrirDocument
         */
        ActionListener obrirDocument = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.vistaObrirDocument(2);
                setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto manualUsuari
         */
        ActionListener manualUsuari = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "../../DOCS/ManualUsuari.pdf";
                File file = new File(filePath).getAbsoluteFile();

                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }
            };

        /**
         * Gestiona el tractament del Boto Guardar
         */
        ActionListener guardar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cp.guardarInfo();
                    mensaje("Actualitzacio correcta", "S'ha actualitzat la informació correctament");

                }catch (ArrayIndexOutOfBoundsException aioobe) {
                    mensaje(WARNING, "La informació no s'ha pogut guardar adequadament.");

                }
            }
        };

        /**
         * Gestiona el tractament del Boto Sortir
         */
        ActionListener sortir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cp.tancarAplicacio();
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    mensaje(WARNING, "El sistema esta processant la teva peticio");
                }
            }
        };


        bCrearDoc.addActionListener(crearDocument);

        bEliminarDoc.addActionListener(eliminarDocument);

        bEditarDoc.addActionListener(editarDocument);

        bConvertirFormat.addActionListener(convertirFormat);

        bLlistarDocSemblants.addActionListener(llistarDocSemblants);

        bLlistarperAutorTitol.addActionListener(llistarPerAutorTitol);

        bLlistarDocExpresio.addActionListener(llistarDocExpresio);

        bLlistarTitolsdunAutor.addActionListener(llistarTitolsdunAutor);

        bLlistarAutorPrefix.addActionListener(llistarAutorPrefix);

        bGestionarExpressio.addActionListener(gestionarExpressio);

        bImportarDocument.addActionListener(importarDocument);

        bListartodosDocumentos.addActionListener(llistartotselsdocuments);

        bObrir.addActionListener(obrirDocument);

        bmanualUsuari.addActionListener(manualUsuari);

        bGuardar.addActionListener(guardar);

        bSortir.addActionListener(sortir);
    }

    /**
     * Metode que permet indicar un misstage pop-up
     * @param titol parametre que identifica el titol del popup
     * @param mensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensaje(String titol, String mensaje) {
        JDialog noDoc = new JDialog(frame, titol);
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(mensaje);
        txt.setBounds(100,30,400,50);

        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(200,100,100,50);

        noDoc.add(txt);
        noDoc.add(bSortir);
        noDoc.setVisible(true);

        ActionListener SortirError = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noDoc.setVisible(false);
            }
        };
        bSortir.addActionListener(SortirError);
    }
}
