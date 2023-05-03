package presentation.view;

import domain.classes.exceptions.InformacioErroneaException;
import domain.classes.exceptions.JaExisteixException;
import domain.classes.exceptions.NullException;
import persistence.classes.exceptions.InformacioBuida;
import persistence.classes.exceptions.DocumentNoTrobat;
import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class ViewImportar extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JTextField textField1 = new JTextField();
    private final JButton  sortirButton = new JButton ("SORTIR");
    private final JButton importarButton = new JButton ("IMPORTAR");
    private final JButton triarDirectoryButton = new JButton("Triar fitxer");

    private final JLabel path_info = new JLabel("'Tria el fitxer que vols seleccionar en l'explorador d'arxius'");

    private final JLabel label1 = new JLabel("Path: ");
    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();

    private String ROOT = "../../Documents";
    private final JFileChooser chooser = new JFileChooser(ROOT);


    public ViewImportar(){

        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("IMPORTAR DOCUMENT");

        textField1.setBounds(150,150,800,50);
        textField1.setEditable(false);
        add(textField1);

        path_info.setBounds(150,190,600,50);
        add(path_info);

        label1.setBounds(30,150,100,50);
        add(label1);

        Color red = new Color(255,105,97);
        sortirButton.setBackground(red);

        triarDirectoryButton.setBounds(1000,150,150,50);
        add(triarDirectoryButton);

        importarButton.setBounds(300,400,600,90);
        add(importarButton);



        sortirButton.setBounds(1050,475,120,80);
        add( sortirButton);

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
         * Gestiona el tractament del Boto Importar
         */
        ActionListener botoImportar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada per importar el document especificat
             * @param e event de pitjar el boto Importar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()) {
                    mensaje(WARNING, "Cal introduir els parametres necessaris");
                }
                else {
                    String path = textField1.getText();
                    String[] cjtPaths = path.split("///");

                    int numImportados = 0;
                        for (String camino : cjtPaths) {
                            try {
                                CtrlPresentacion.importarDocument(camino);
                                ++numImportados;
                            }catch(DocumentNoTrobat ex) {
                                mensaje(ERROR, "El path introduit es erroni");
                            } catch (NullException | InformacioBuida ne) {
                                mensaje(ERROR, "L'autor o el títol està buit");
                            } catch (InformacioErroneaException iee) {
                                mensaje(ERROR, "El path introduit conte caracters incompatibles");
                            } catch (JaExisteixException ex) {
                                mensaje(ERROR,"Ja existeix el document que es vol importar");
                            }
                        }
                    if(numImportados == cjtPaths.length){
                        if(cjtPaths.length > 1)
                            mensaje("Documents Importats", "S'han importat els documents especificats");
                        else
                            mensaje("Document Importat", "S'ha importat el document especificat");
                    }
                    textField1.setText("");

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
                    textField1.setText(path);

                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    try {
                            CtrlPresentacion.vistaImportarDocument();
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

        importarButton.addActionListener(botoImportar);
        triarDirectoryButton.addActionListener(botoTriar);
        sortirButton.addActionListener(botoSortir);
    }

    /**
     * Metode que permet indicar un misstage pop-up
     * @param Titol parametre que identifica el titol del popup
     * @param Mensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensaje(String Titol, String Mensaje) {
        JDialog noDoc = new JDialog(frame, Titol);
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txt = new JLabel(Mensaje);
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
}
