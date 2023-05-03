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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


/**
 * @author Jaume Alos Cuadrado(jaume.alos@estudiantat.upc.edu)
 */

public class ViewDocSemblants extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JButton sortirButton = new JButton("SORTIR");
    private final JButton buscarButton = new JButton("BUSCAR");
    private final JButton triarDirectoryButton = new JButton("Triar fitxer");

    private final JTextField textField3 = new JTextField();

    private final JComboBox comboBox1 = new JComboBox();

    SpinnerModel value = new SpinnerNumberModel(1, 1,9999, 1);
    private final JSpinner spinner1 = new JSpinner(value);

    private final JList list1 = new JList();
    private final JScrollPane scrollBarautor = new JScrollPane(list1);

    private final JList list2 = new JList();
    private final JScrollPane scrollBartitol = new JScrollPane(list2);

    private final JLabel labelAutor = new JLabel("Autor: ");
    private final JLabel labelTitol = new JLabel("Títol: ");
    private final JLabel nombre = new JLabel("Nombre: ");
    private final JLabel path = new JLabel("Path: ");
    private final JLabel path_info = new JLabel("'Tria el fitxer que vols seleccionar en l'explorador d'arxius'");

    private final JLabel ordre = new JLabel("Ordre: ");

    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();

    private String ROOT = "../../Documents";
    private final JFileChooser chooser = new JFileChooser(ROOT);
    private String autorSeleccionat;

    private String titolSeleccionat;

    private String[] titols;

    public ViewDocSemblants(){

        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("LLISTAR DOCUMENTS SEMBLANTS");

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

        path.setBounds(30,280,100,50);
        add(path);

        path_info.setBounds(160,310,600,50);
        add(path_info);

        triarDirectoryButton.setBounds(1000,280,150,50);
        add(triarDirectoryButton);

        textField3.setBounds(150,280,800,50);
        textField3.setEditable(false);
        add(textField3);

        spinner1.setBounds(600,380,200,50);
        add(spinner1);

        nombre.setBounds(540,380,100,50);
        add(nombre);

        ordre.setBounds(270,380,100,50);
        add(ordre);

        comboBox1.setBounds(350,385,100,40);
        add(comboBox1);

        Color red = new Color(255,105,97);
        sortirButton.setBackground(red);

        buscarButton.setBounds(300,500,600,70);
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

        comboBox1.addItem("A-Z");
        comboBox1.addItem("Z-A");

        /**
         * Gestiona el tractament del Boto Buscar
         */
        ActionListener botoBuscar = new ActionListener() {

            /**
             *
             * @param e event de pitjar el boto Buscar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer k = (Integer) spinner1.getValue();
                String ordre = comboBox1.getSelectedItem().toString();

                if(ordre == "A-Z")ordre = "a";
                else ordre = "d";

                if (!textField3.getText().isEmpty()) {
                    String path = textField3.getText();

                    try {
                        String[] documents = CtrlPresentacion.llistarDocSemblantsPath(path, k, ordre);
                        if (documents.length == 0) {
                            mensaje(ERROR, "No hi ha cap doc semblant");
                        } else {
                            String missatge = "Els docs semblants son:";
                            ViewLlistar vL = new ViewLlistar(missatge, documents);
                            setVisible(false);
                        }

                    } catch (DocumentNoTrobat ex) {
                        importarDocMensaje(ERROR, "No tens aquest document introduït al sistema." + "\n" +
                                "Vols introduir-lo?", path, k, ordre);
                    }catch(NullPointerException npe){
                        mensaje(ERROR,"Els parametres especificats estan buits");
                    } catch (InformacioBuida ex) {
                        mensaje(ERROR, "El path '"+path+"' es erroni");
                    }
                }else if((autorSeleccionat == null | titolSeleccionat == null)) {
                        mensaje(WARNING, "Cal introduir els paràmetres necessaris");
                }
                else {

                    try{
                        String[] documents =
                                CtrlPresentacion.llistarDocSemblants(autorSeleccionat,titolSeleccionat,k,ordre);
                        if (documents.length == 0) {
                            mensaje(ERROR, "No hi ha cap doc semblant");
                        } else {
                            String missatge = "Els documents semblants son:";
                            ViewLlistar vL = new ViewLlistar(missatge, documents);
                            setVisible(false);
                        }

                    }catch(DocumentNoTrobat | NullPointerException ex) {
                        mensaje(ERROR,"El doc amb autor '"+autorSeleccionat
                                +"' i titol '"+titolSeleccionat+"' no existeix");
                    }
                }
            }
        };

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
                FileNameExtensionFilter filter = new FileNameExtensionFilter
                        ("TXT i XML","txt","xml");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Documents/"));

                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File arxiu = chooser.getSelectedFile();
                    textField3.setText(arxiu.getPath());

                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    try {
                        CtrlPresentacion.vistaDocsemblants();
                        setVisible(false);
                    } catch (Exception ex) {
                        mensaje(WARNING,"Hi ha hagut un error inesperat");
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
                    mensaje(WARNING,"Ha hagut un error inesperat");
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
     * @param errorTitol parametre que identifica el titol del popup
     * @param errorMensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensaje(String errorTitol, String errorMensaje) {
        JDialog noDoc = new JDialog(frame, errorTitol);
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(errorMensaje);
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
     * @param k identifica el nombre de documents que es volen llistar
     * @param ordre identifica el ordre amb el que es desitja llistar la informacio
     */
    private void importarDocMensaje(String titol, String mensaje,String path, Integer k, String ordre) {
        JDialog noDoc = new JDialog(frame, titol);
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(mensaje);
        txt.setBounds(100,30,400,50);
        JButton bAfegir = new JButton("Afegir");
        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(250,100,100,50);
        bAfegir.setVisible(true);
        bAfegir.setBounds(150,100,100,50);

        noDoc.add(bAfegir);
        noDoc.add(txt);
        noDoc.add(bSortir);
        noDoc.setVisible(true);

        /**
         * Gestiona el tractament del Boto Sortir
         */
        ActionListener Sortir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noDoc.setVisible(false);
            }
        };

        /**
         * Gestiona el tractament del Boto Afegir
         */
        ActionListener Afegir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.importarDocument(path);
                    String[] documents = CtrlPresentacion.llistarDocSemblantsPath(path, k, ordre);
                    String missatge = "Els documents semblants son:";
                    ViewLlistar vL = new ViewLlistar(missatge, documents);
                    setVisible(false);

                } catch (DocumentNoTrobat | InformacioBuida ex) {
                    mensaje(ERROR, "El path introduit es erroni");
                } catch (NullException ne) {
                    mensaje(ERROR, "El path introduit es null");
                } catch (InformacioErroneaException iee) {
                    mensaje(ERROR, "El path introduit conte caracters incompatibles");
                } catch (JaExisteixException ex) {
                    mensaje(ERROR,"Ja existeix el document que es vol el importar");
                }
            }
        };
        bAfegir.addActionListener(Afegir);
        bSortir.addActionListener(Sortir);
    }
}
