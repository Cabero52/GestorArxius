package presentation.view;

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
public class ViewAutorPrefix extends JFrame{
    private final String WARNING = "WARNING";

    private final JTextField textField1 = new JTextField();
    private final JButton sortirButton = new JButton("SORTIR");
    private final JButton buscarButton = new JButton("BUSCAR");

    private final JComboBox comboBox1 = new JComboBox<>();
    private final JLabel prefix = new JLabel("Prefix: ");
    private final JLabel ordre = new JLabel("Ordre: ");

    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();

    public ViewAutorPrefix(){
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("LLISTAR AUTOR PER PREFIX");

        prefix.setBounds(30,100,100,50);
        add(prefix);

        textField1.setBounds(100,100,1000,50);
        add(textField1);

        ordre.setBounds(30,200,100,50);
        add(ordre);

        comboBox1.setBounds(100,200,100,40);
        add(comboBox1);

        buscarButton.setBounds(300,400,600,70);
        add(buscarButton);

        Color red = new Color(255,105,97);
        sortirButton.setBackground(red);
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
         * Gestiona el tractament del Boto Cercar
         */
        ActionListener botoBuscar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per llistar els autors donat un prefix
             * @param e event de pitjar el boto Cercar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()) {
                    String errorMensaje = "Cal introduir els paràmetres necessaris";
                    mensaje(WARNING, errorMensaje);
                }

                else {
                    String prefix = textField1.getText();
                    String ordre = comboBox1.getSelectedItem().toString();
                    if(ordre == "A-Z")ordre = "a";
                    else ordre = "d";
                    String autors[] = CtrlPresentacion.llistarAutorPrefix(prefix,ordre);
                    textField1.setText("");

                    if (autors.length == 0) {
                        mensaje(WARNING,"No hi ha cap autor per llistar amb el prefix '" +prefix + "'");
                    }
                    else {
                        String missatge = "Els autors amb prefix : " + prefix;
                        ViewLlistar vL = new ViewLlistar(missatge,autors);
                        setVisible(false);
                    }
                }
            }
        };

        /**
         * Gestiona el tractament del Boto Sortir
         */
        ActionListener botoSortir = new ActionListener() {
            /**
             * Gestiona el tancament de la finestra de la funcionalitat
             * @param e event de pitjar el boto Sortir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.OpenViewPrincipal();
                } catch (Exception ex) {
                    mensaje(WARNING, "Hi ha hagut un error inesperat");
                }
                setVisible(false);
            }
        };

        buscarButton.addActionListener(botoBuscar);
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
        txt.setBounds(30,30,400,50);

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
