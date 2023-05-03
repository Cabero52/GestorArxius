package presentation.view;

import domain.classes.exceptions.DocumentNoExisteix;
import domain.classes.exceptions.InformacioErroneaException;
import domain.classes.exceptions.JaExisteixException;
import domain.classes.exceptions.NullException;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;
import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * @author Jaume Alos Cuadrado(jaume.alos@estudiantat.upc.edu)
 */

public class ViewEliminarDocument extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JList list1 = new JList();
    private final JScrollPane scrollBarautor = new JScrollPane(list1);

    private final JList list2 = new JList();
    private final JScrollPane scrollBartitol = new JScrollPane(list2);
    private final JTextField textField3 = new JTextField();
    
    private final JLabel labelAutor = new JLabel("Autor: ");
    private final JLabel labelTitol = new JLabel("Títol: ");
    private final JLabel path = new JLabel("Path: ");

    private final JLabel path_info = new JLabel("'Tria el fitxers que vols seleccionar en l'explorador d'arxius'");

    private final JButton eliminarButton = new JButton("ELIMINAR");
    private final JButton sortirButton = new JButton("SORTIR");
    private final JButton triarDirectoryButton = new JButton("Triar fitxers");


    private final JPanel panell = new JPanel();
    private final JFrame frame = new JFrame("JFrame");
    private String ROOT = "../../Documents";
    private final JFileChooser chooser = new JFileChooser(ROOT);
    private String autorSeleccionat;

    private String titolSeleccionat;

    private String[] titols;

    public ViewEliminarDocument() {
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("ELIMINAR DOCUMENT");

        setLayout(null);

        String[] totsAutors  = CtrlPresentacion.getautors();
        list1.setListData(totsAutors);

        scrollBarautor.setBounds(150, 100, 800, 70);
        add(scrollBarautor);

        labelAutor.setBounds(30, 105, 100, 50);
        add(labelAutor);

        scrollBartitol.setBounds(150, 200, 800, 70);
        add(scrollBartitol);

        labelTitol.setBounds(30,205,100,50);
        add(labelTitol);

        Color red = new Color(255,105,97);
        sortirButton.setBackground(red);

        eliminarButton.setBounds(300,450,600,70);
        add(eliminarButton);

        path.setBounds(30,300,100,50);
        add(path);
        path_info.setBounds(160,260,600,50);
        add(path_info);

        triarDirectoryButton.setBounds(1000,300,150,50);
        add(triarDirectoryButton);

        textField3.setBounds(150,300,800,50);
        textField3.setEditable(false);
        add(textField3);

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
         * Gestiona el tractament del Boto Eliminar
         */
        ActionListener botoEliminar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per eliminar un document
             * @param e event de pitjar el boto Eliminar
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textField3.getText().isEmpty()) {
                    String path = textField3.getText();
                    String[] cjtPaths = path.split("///");

                    int numImportados = 0;
                    for (String camino : cjtPaths) {
                        try {
                            CtrlPresentacion.eliminarDocPath(camino);
                            ++numImportados;

                            String[] totsAutors  = CtrlPresentacion.getautors();
                            list1.setListData(totsAutors);
                            titols  = CtrlPresentacion.llistarTitolsAutor(autorSeleccionat,"a");
                            list2.setListData(titols);

                            } catch(DocumentNoTrobat ex) {
                            mensaje(ERROR, "El path introduit es erroni");
                        } catch (InformacioBuida ne) {
                            mensaje(ERROR, "L'autor o el títol està buit");
                        } catch (DocumentNoExisteix ex) {
                            mensaje(ERROR,"El document especificat no existeix");

                    }
                    if(numImportados == cjtPaths.length){
                        if(cjtPaths.length > 1)
                            mensaje("Documents Importats", "S'han eliminat els documents especificats");
                        else
                            mensaje("Document Importat", "S'ha eliminat el document especificat");
                    }
                    textField3.setText("");

                    }
                }

                else if(autorSeleccionat == null | titolSeleccionat == null) {
                    mensaje(WARNING, "Cal introduir els parametres necessaris");
                }

                else {
                    try {
                        CtrlPresentacion.eliminarDocument(autorSeleccionat,titolSeleccionat);
                        mensaje("Document eliminat", "S'ha eliminat el doc " +
                                "amb autor '"+autorSeleccionat+"' i titol '"+titolSeleccionat+"'");

                        String[] totsAutors  = CtrlPresentacion.getautors();
                        list1.setListData(totsAutors);
                        titols  = CtrlPresentacion.llistarTitolsAutor(autorSeleccionat,"a");
                        list2.setListData(titols);

                    } catch (DocumentNoExisteix ex) {
                        mensaje(ERROR,"El doc amb autor '"+autorSeleccionat+"' i titol '"+
                                titolSeleccionat+"' no existeix");
                    } catch (DocumentNoTrobat ex) {
                        mensaje(ERROR,"El path no existeix");
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
         * Gestiona el tractament del Boto Triar
         */
        ActionListener botoTriar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada mostra un explorador d'arxius per seleccionar un document
             * @param e event de pitjar el boto Triar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setDialogTitle("Selecciona arxiu");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(true);
                FileNameExtensionFilter filter = new FileNameExtensionFilter
                        ("Txt i XML", "txt","xml");
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
                        CtrlPresentacion.vistaEliminarDocument();
                        setVisible(false);
                    } catch (Exception ex) {
                        mensaje(WARNING,"Hi ha hagut un error inesperat");
                }
                }
            }
        };

        /**
         * Gestiona el tractament del Boto Sortir
         */
        ActionListener botoSortir = new ActionListener() {
            /**
             * Gestiona el tancament de la finestra
             * @param e event de pitjar el boto Sortir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.OpenViewPrincipal();
                } catch (Exception ex) {
                    mensaje(WARNING,"Hi ha hagut un error inesperat");
                }
                setVisible(false);
            }
        };

        eliminarButton.addActionListener(botoEliminar);
        triarDirectoryButton.addActionListener(botoTriar);
        sortirButton.addActionListener(botoSortir);
    }

    /**
     * Metode que permet indicar un misstage pop-up
     * @param errorTitol parametre que identifica el titol del popup
     * @param errorMensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensaje(String errorTitol, String errorMensaje) {
        JDialog noDoc = new JDialog(frame, errorTitol);
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(errorMensaje);
        txt.setBounds(50,30,400,50);
        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(350,200,100,50);

        noDoc.add(txt);
        noDoc.add(bSortir);
        noDoc.setVisible(true);

        ActionListener Sortir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noDoc.setVisible(false);
            }
        };
        bSortir.addActionListener(Sortir);
    }

}
