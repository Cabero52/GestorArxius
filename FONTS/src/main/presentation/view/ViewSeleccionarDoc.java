package presentation.view;

import domain.classes.exceptions.InformacioErroneaException;
import domain.classes.exceptions.JaExisteixException;
import domain.classes.exceptions.NullException;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;
import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class ViewSeleccionarDoc extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JList list1 = new JList();
    private final JScrollPane scrollBarautor = new JScrollPane(list1);

    private final JList list2 = new JList();
    private final JScrollPane scrollBartitol = new JScrollPane(list2);

    private final JTextField textField3 = new JTextField();

    private final JLabel path = new JLabel("Path: ");
    private final JLabel path_info = new JLabel("'Tria el fitxers que vols seleccionar en l'explorador d'arxius'");
    private final JLabel labelAutor = new JLabel("Autor: ");
    private final JLabel labelTitol = new JLabel("Títol: ");

    private final JButton buscarButton = new JButton("BUSCAR");
    private final JButton triarDirectoryButton = new JButton("Triar fitxers");
    private final JButton sortirButton = new JButton("SORTIR");

    private String ROOT = "../../Documents";
    private final JFileChooser chooser = new JFileChooser(ROOT);
    private final JPanel panell = new JPanel();
    private final JFrame frame = new JFrame("JFrame");

    private String autorSeleccionat;

    private String titolSeleccionat;

    private String[] titols;

    public ViewSeleccionarDoc(Integer k) {
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("SELECCIONAR DOCUMENT");

        setLayout(null);

        String[] totsAutors  = CtrlPresentacion.getautors();
        list1.setListData(totsAutors);

        scrollBarautor.setBounds(150, 100, 800, 70);
        add(scrollBarautor);

        labelAutor.setBounds(30, 105, 100, 50);
        add(labelAutor);

        scrollBartitol.setBounds(150, 200, 800, 70);
        add(scrollBartitol);

        labelTitol.setBounds(30, 205, 100, 50);
        add(labelTitol);

        textField3.setBounds(150, 330, 800, 50);
        textField3.setEditable(false);
        add(textField3);

        path.setBounds(30, 330, 100, 50);
        add(path);
        path_info.setBounds(160,290,600,50);
        add(path_info);

        triarDirectoryButton.setBounds(1000, 330, 150, 50);
        add(triarDirectoryButton);

        Color red = new Color(255, 105, 97);
        sortirButton.setBackground(red);

        buscarButton.setBounds(300, 450, 600, 70);
        add(buscarButton);

        sortirButton.setBounds(1050,475,120,80);
        add(sortirButton);
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
                    CtrlPresentacion.guardarInfo();
                    System.exit(0);
                }
            }
        });

        /**
         * Gestiona el tractament del Boto Buscar
         */
        ActionListener botoBuscar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per cercar els documents
             * @param e event de pitjar el boto Buscar
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textField3.getText().isEmpty()) {
                    String path = textField3.getText();
                    String[] cjtPaths = path.split("///");

                    int numImportados = 0;
                    boolean jaHeSaltat = false;
                    for (String camino : cjtPaths) {
                        try {

                            String[] autorDoc = CtrlPresentacion.seleccionarDocumentPath(camino).split(":titol:");
                            String[] titolCont = autorDoc[1].split(":cont:"); // [0] titulo // [1] contenido
                            ++numImportados;

                            if (k == 1) {
                                CtrlPresentacion.vistaEditarDoc(autorDoc[0], titolCont[0], titolCont[1]);
                            } else {
                                CtrlPresentacion.vistaMostrarDoc(autorDoc[0], titolCont[0], titolCont[1]);
                            }
                            setVisible(false);

                        } catch (DocumentNoTrobat ex) {
                            if (!jaHeSaltat) {
                                importarDocMensaje(ERROR, "No tens aquest document introduït al sistema." + "\n" +
                                        "Vols introduir-lo?", path, k);
                                jaHeSaltat = true;
                            }
                        } catch (InformacioBuida ex) {
                            mensaje(ERROR, "El path introduit es erroni");

                        }
                        if (numImportados == cjtPaths.length) {
                            if (cjtPaths.length > 1)
                                mensaje("Documents Importats", "S'han importat els documents especificat");
                            else
                                mensaje("Document Importats", "Has importat el document especificat");
                        }
                        textField3.setText("");
                    }
                } else if (autorSeleccionat == null | titolSeleccionat == null) {
                    mensaje(WARNING, "Cal introduir els parametres necessaris");
                } else {

                    try {
                        String contingut = CtrlPresentacion.llistarDocumentPerTitolAutor(autorSeleccionat, titolSeleccionat);
                        if (k == 1) {
                            ViewEditarDocument vED = new ViewEditarDocument(autorSeleccionat, titolSeleccionat, contingut);
                        } else {
                            ViewMostraDocument vMD = new ViewMostraDocument(autorSeleccionat, titolSeleccionat, contingut);
                        }
                        setVisible(false);


                    } catch (DocumentNoTrobat ex) {
                        mensaje(ERROR, "El document no Existeix");
                    } catch (NullException ne) {
                        mensaje(ERROR, "El document no té títol i/o autor");
                    }
                }
            }
        };

        /**
         * Gestiona el tractament del Boto Triar
         */

        ActionListener botoTriar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament
             * @param e event de pitjar el boto Triar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setDialogTitle("Selecciona arxiu");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(true);
                FileNameExtensionFilter filter = new FileNameExtensionFilter
                        ("Txt i XML", "txt", "xml");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Documents/"));
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] arxiu = chooser.getSelectedFiles();
                    String path = new String();
                    for (File file : arxiu) {
                        path += file.getPath() + "///";
                    }
                    textField3.setText(path);

                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    try {
                        CtrlPresentacion.vistaObrirDocument(k);
                        setVisible(false);
                    } catch (Exception ex) {
                        String errorMensaje = "Hi ha hagut un error inesperat";
                        mensaje(WARNING, errorMensaje);
                    }
                }
            }
        };

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                autorSeleccionat = (String) list1.getSelectedValue();
                titols  = CtrlPresentacion.llistarTitolsAutor(autorSeleccionat,"a");
                list2.setListData(titols);

            }
        });
        list2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                titolSeleccionat = (String) list2.getSelectedValue();
            }
        });

        /**
         * Gestiona el tractament del Boto Sortir
         */
        ActionListener botoSortir = new ActionListener() {
            /**
             *Gestiona el tancament de la finestra de la funcionalitat
             * @param e event de pitjar el boto Sortir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.OpenViewPrincipal();
                } catch (Exception ex) {
                    String errorTitol = "WARNING!";
                    String errorMensaje = "Hi ha hagut un error inesperat";
                    mensaje(errorTitol, errorMensaje);
                }
                setVisible(false);
            }
        };
        buscarButton.addActionListener(botoBuscar);
        triarDirectoryButton.addActionListener(botoTriar);
        sortirButton.addActionListener(botoSortir);
    }

    /**
     * Metode que permet indicar un misstage pop-up
     * @param titol parametre que identifica el titol del popup
     * @param mensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensaje(String titol, String mensaje) {
        JDialog noDoc = new JDialog(frame, titol);
        noDoc.setBounds(500, 300, 500, 300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(mensaje);
        txt.setBounds(100, 30, 400, 50);
        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(350, 200, 100, 50);

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

    /**
     * Metode que permet mostrar un pop-up al no diposar del document en el sistema i permetre la seva importacio
     * @param titol parametre que identifica el titol del pop-up
     * @param mensaje parametre que identifica el missatje del pop.-up
     * @param path parametre que identifica el path del
     * @param k identifica el nombre de documents que es volen llistar
     */
    private void importarDocMensaje(String titol, String mensaje, String path, Integer k) {
        JDialog importDoc = new JDialog(frame, titol);
        importDoc.setBounds(500, 300, 500, 300);
        importDoc.setLayout(null);

        JLabel txt = new JLabel(mensaje);
        txt.setBounds(100, 30, 400, 50);
        JButton bAfegir = new JButton("Afegir");
        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(250, 100, 100, 50);
        bAfegir.setVisible(true);
        bAfegir.setBounds(150, 100, 100, 50);

        importDoc.add(bAfegir);
        importDoc.add(txt);
        importDoc.add(bSortir);
        importDoc.setVisible(true);

        ActionListener Sortir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importDoc.setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto Afegir
         */
        ActionListener Afegir = new ActionListener() {
            /**
             *Gestiona els parametres d'entrada i l'addicio dels documents al sistema
             * @param e event de pitjar el boto Afegir
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] cjtPaths = path.split("///");

                int numImportados = 0;
                for (String camino : cjtPaths) {
                    try {
                        CtrlPresentacion.importarDocument(camino);
                        String[] autor = CtrlPresentacion.seleccionarDocumentPath(camino).split(":titol:");
                        String[] titolCont = autor[1].split(":cont:"); // [0]<-titulo // [1]<-contenido
                        ++numImportados;

                        if (k == 1) {
                            ViewEditarDocument vED = new ViewEditarDocument(autor[0], titolCont[0], titolCont[1]);
                        } else {
                            ViewMostraDocument vMD = new ViewMostraDocument(autor[0], titolCont[0], titolCont[1]);
                        }
                        setVisible(false);

                    } catch (DocumentNoTrobat ex) {
                            importarDocMensaje(ERROR, "No tens aquest document introduït al sistema." + "\n" +
                                    "Vols introduir-lo?", path, k);
                    } catch (InformacioBuida ex) {
                        mensaje(ERROR, "El path introduit es erroni");
                    } catch (NullException ne) {
                        mensaje(ERROR, "L'autor o el títol està buit");
                    } catch (InformacioErroneaException iee) {
                        mensaje(ERROR, "El path introduit conte caracters incompatibles");
                    } catch (JaExisteixException ex) {
                        mensaje(ERROR,"Ja existeix el document que es vol importar");
                    }
                    if (numImportados == cjtPaths.length) {
                        if (cjtPaths.length > 1)
                            mensaje("Documents Importats", "S'han importat els documents especificats");
                        else
                            mensaje("Document Importat", "Has importat el document especificat");
                    }
                    textField3.setText("");
                }
                importDoc.setVisible(false);
            }
        };
        bAfegir.addActionListener(Afegir);
        bSortir.addActionListener(Sortir);

    }
}