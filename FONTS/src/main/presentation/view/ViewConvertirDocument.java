package presentation.view;

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

public class ViewConvertirDocument extends JFrame{

    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JComboBox comboBox1 = new JComboBox();

    private final JButton convertirButton = new JButton("CONVERTIR");
    private final JButton  sortirButton = new JButton("SORTIR");
    private final JButton triarDirectoryButton = new JButton("Triar fitxer");

    private final JList list1 = new JList();
    private final JScrollPane scrollBarautor = new JScrollPane(list1);

    private final JList list2 = new JList();
    private final JScrollPane scrollBartitol = new JScrollPane(list2);
    private final JTextField textField3 = new JTextField();

    private final JLabel labelAutor = new JLabel("Autor: ");
    private final JLabel labelTitol = new JLabel("Títol: ");
    private final JLabel label3 = new JLabel("Format: ");
    private final JLabel path = new JLabel("Path: ");
    private final JLabel path_info = new JLabel("'Tria el fitxer que vols seleccionar en l'explorador d'arxius'");

    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();
    private String ROOT = "../../Documents";
    private final JFileChooser chooser = new JFileChooser(ROOT);
    private String autorSeleccionat;
    private String titolSeleccionat;
    private String[] titols;

    public ViewConvertirDocument(){
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("CONVERTIR DOCUMENT");

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

        label3.setBounds(30,390,100,50);
        add(label3);

        comboBox1.setBounds(150,400,100,30);
        add(comboBox1);

        path.setBounds(30,290,100,50);
        add(path);

        path_info.setBounds(160,265,600,50);
        add(path_info);

        triarDirectoryButton.setBounds(1000,300,150,50);
        add(triarDirectoryButton);

        textField3.setBounds(150,300,800,50);
        textField3.setEditable(false);
        add(textField3);

        Color red = new Color(255,105,97);
        sortirButton.setBackground(red);

        convertirButton.setBounds(300,450,600,70);
        add(convertirButton);

        sortirButton.setBounds(1050,475,120,80);
        add( sortirButton);

        add(panell);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame,
                        "Estàs segur que vols tancar la app?",
                        "Confirmació de tancament",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    CtrlPresentacion.guardarInfo();
                    System.exit(0);
                }
            }
        });

        comboBox1.addItem("txt");
        comboBox1.addItem("xml");

        /**
         * Gestiona el tractament del Boto Cercar
         */
        ActionListener botoCrear = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textField3.getText().isEmpty()) {
                    String path = textField3.getText();
                    String format = comboBox1.getSelectedItem().toString();
                    try {
                        CtrlPresentacion.convertirDocPath(path,format);
                        mensaje("Document Convertit","S'ha convertit el document al format '"+format+"'");
                        textField3.setText("");

                    } catch (DocumentNoTrobat ex) {
                        importarDocMensaje(ERROR,"No tens aquest document introduït al sistema." + "\n" +
                                "Vols introduir-lo?", path,format);
                    } catch (InformacioBuida ex) {
                        mensaje(ERROR, "El path introduit es erroni");

                    }
                }

                else if(autorSeleccionat == null | titolSeleccionat == null) {
                    mensaje(WARNING, "Cal introduir els parametres necessaris");
                }

                else  {

                    String format = comboBox1.getSelectedItem().toString();

                    try {
                        CtrlPresentacion.convertirDoc(autorSeleccionat,titolSeleccionat,format);
                        mensaje("Document Convertit","S'ha convertit el document al format '"+format+"'");

                    }catch (DocumentNoTrobat ex) {
                        mensaje(ERROR,"El document amb autor '"+autorSeleccionat+"' i titol '"+titols+"' no existeix");
                    }
                }
            }
        };

        /**
         * Gestiona el tractament del Boto Triar
         */
        ActionListener botoTriar = new ActionListener() {
            /**
             * Metode que permet escollir un document a través d'un explorador d'arxius on es llisten els diferents
             * documents i altres fitxers existents
             * @param e event de pitjar el boto Triar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setDialogTitle("Selecciona arxiu");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filter = new FileNameExtensionFilter
                        ("Txt i XML", "txt","xml");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);

                chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Documents/"));
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File arxiu = chooser.getSelectedFile();
                    textField3.setText(arxiu.getPath());

                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    try {
                        CtrlPresentacion.vistaConvertirDocument();
                        setVisible(false);
                    } catch (Exception ex) {
                        mensaje(WARNING, "Hi ha hagut un error inesperat");
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
                    mensaje(WARNING,"Hi ha hagut un error inesperat");
                }
                setVisible(false);
            }
        };

        convertirButton.addActionListener(botoCrear);
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
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(mensaje);
        txt.setBounds(100,30,400,50);
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

    /**
     * Metode que permet mostrar un pop-up al no diposar del document en el sistema i permetre la seva importacio
     * @param titol parametre que identifica el titol del pop-up
     * @param mensaje parametre que identifica el missatje del pop.-up
     * @param path parametre que identifica el path del
     * @param format parametre que identifica el format del document
     */
    private void importarDocMensaje(String titol, String mensaje,String path,String format) {
        JDialog importDoc = new JDialog(frame, titol);
        importDoc.setBounds(500,300,500,300);
        importDoc.setLayout(null);

        JLabel txt = new JLabel(mensaje);
        txt.setBounds(100,30,400,50);
        JButton bAfegir = new JButton("Afegir");
        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(250,100,100,50);
        bAfegir.setVisible(true);
        bAfegir.setBounds(150,100,100,50);

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
             * Gestiona els parametres d'entrada i el seu tractament per convertir un document
             * @param e event de pitjar el boto Afegir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.importarDocument(path);
                    CtrlPresentacion.convertirDocPath(path,format);
                    mensaje("Document Convertit","S'ha convertit el document al format '"+format+"'");

                } catch (DocumentNoTrobat | InformacioBuida ex) {
                    mensaje(ERROR, "El path '"+path+"' es erroni");
                } catch (NullException ne) {
                    mensaje(ERROR, "L'autor o el títol està buit");
                } catch (InformacioErroneaException iee) {
                    mensaje(ERROR, "El path '"+path+"'conte caracters incompatibles");
                } catch (JaExisteixException ex) {
                    mensaje(ERROR,"El document amb path '"+path+"' que es vol importar ja existeix");
                }
                textField3.setText("");
                importDoc.setVisible(false);
            }
        };
        bAfegir.addActionListener(Afegir);
        bSortir.addActionListener(Sortir);
    }
}
