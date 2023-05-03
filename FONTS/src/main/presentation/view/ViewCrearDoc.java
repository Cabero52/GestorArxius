package presentation.view;

import domain.classes.exceptions.InformacioErroneaException;
import domain.classes.exceptions.JaExisteixException;
import domain.classes.exceptions.NullException;
import persistence.classes.exceptions.DocumentNoTrobat;
import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Jaume Alos Cuadrado(jaume.alos@estudiantat.upc.edu)
 */

public class ViewCrearDoc extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    private final JTextArea textArea1 = new JTextArea();
    private final JScrollPane verticalScrollBar = new JScrollPane(textArea1);

    private final JLabel label1 = new JLabel("Autor: ");
    private final JLabel label2 = new JLabel("Títol: ");
    private final JLabel label3 = new JLabel("Contingut: ");
    private final JLabel label4 = new JLabel("Format: ");

    private final JComboBox comboBox1 = new JComboBox();

    private final JButton crearButton = new JButton("CREAR");
    private final JButton sortirButton = new JButton("SORTIR");

    private final JPanel panell = new JPanel();
    private final JFrame frame = new JFrame("JFrame");


    public ViewCrearDoc(){
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("CREAR DOCUMENT");

        label1.setBounds(30,30,100,50);
        add(label1);
        textField1.setBounds(150,30,1000,50);
        add(textField1);

        label2.setBounds(30,110,100,50);
        add(label2);
        textField2.setBounds(150,110,1000,50);
        add(textField2);

        textArea1.setLineWrap(true);
        label3.setBounds(30,200,100,50);
        add(label3);
        verticalScrollBar.setBounds(150,190,950,260);
        add(verticalScrollBar);


        label4.setBounds(30,480,100,50);
        add(label4);
        comboBox1.setBounds(150,490,100,30);
        add(comboBox1);

        Color red = new Color(255,105,97);
        sortirButton.setBackground(red);

        crearButton.setBounds(300,470,600,80);
        add(crearButton);
        sortirButton.setBounds(1050,485,120,80);
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

        comboBox1.addItem("txt");
        comboBox1.addItem("xml");


        /**
         * Gestiona el tractament del Boto Crear
         */
        ActionListener botoCrear = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per crear un Document
             * @param e e event de pitjar el boto Cercar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea1.getText().isEmpty() || textField1.getText().isEmpty() || textField2.getText().isEmpty()) {
                    mensaje(WARNING, "Cal introduir els paràmetres necessaris");
                }

                else {
                    String autor = textField1.getText();
                    String titol = textField2.getText();
                    String contingut = textArea1.getText();
                    String format = comboBox1.getSelectedItem().toString();

                    try {
                        CtrlPresentacion.crearDoc(autor, titol, contingut, format);
                        mensaje("Document Creat", "S'ha creat el doc amb autor '"+
                                autor+"' i titol '"+titol+"'");
                        textField1.setText("");
                        textField2.setText("");
                        textArea1.setText("");

                    } catch(JaExisteixException ex) {
                        mensaje(ERROR,"El doc introduit ja existeix");
                    } catch (NullException ne) {
                        mensaje(ERROR, "Les dades introduides son nul·les");
                    } catch (InformacioErroneaException | DocumentNoTrobat iee ) {
                        mensaje(ERROR, "Has usat caràcters incompatibles.");
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
                    mensaje("WARNING!","Ha hagut un error inesperat");
                }
                setVisible(false);
            }
        };

        crearButton.addActionListener(botoCrear);
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

        JButton sortirButton = new JButton("Sortir");
        sortirButton.setVisible(true);
        sortirButton.setBounds(350,200,100,50);

        noDoc.add(txt);
        noDoc.add(sortirButton);
        noDoc.setVisible(true);

        ActionListener Sortir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noDoc.setVisible(false);
            }
        };
        sortirButton.addActionListener(Sortir);
    }

}
