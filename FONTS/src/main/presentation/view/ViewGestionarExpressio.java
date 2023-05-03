package presentation.view;

import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.util.List;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class ViewGestionarExpressio extends JFrame{
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JButton afegirButton = new JButton("AFEGIR");
    private final JButton eliminarButton = new JButton("ELIMINAR");
    private final JButton modificarButton = new JButton("MODIFICAR");
    private final JButton sortirButton = new JButton("SORTIR");

    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();

    private final JLabel afegir = new JLabel("Afegir nova expressió:");
    private final JLabel eliminar = new JLabel("Eliminar/Modificar expressió:");
    private final JLabel  modificar = new JLabel("Expressió modificada: ");

    private final JList list1 = new JList();
    private final JScrollPane verticalScrollBar = new JScrollPane(list1);

    private final JPanel panell = new JPanel();
    private final JFrame frame = new JFrame();

    private String elementoSeleccionado;

    public ViewGestionarExpressio(String[] expresiones){


        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("GESTIONAR EXPRESSIONS BOOLEANAS");

        afegir.setBounds(20,30,200,50);
        add(afegir);

        textField1.setBounds(230,30,800,50);
        add(textField1);

        afegirButton.setBounds(300,100,600,70);
        add(afegirButton);

        eliminar.setBounds(20,200,200,50);
        add(eliminar);

        list1.setListData(expresiones);

        verticalScrollBar.setBounds(230,180,800,110);
        add(verticalScrollBar);

        eliminarButton.setBounds(300,300,600,70);
        add(eliminarButton);

        modificar.setBounds(20,410,200,50);
        add(modificar);

        textField2.setBounds(230,410,800,50);
        add(textField2);

        modificarButton.setBounds(300,470,600,70);
        add(modificarButton);

        Color rojo = new Color(255,105,97);
        sortirButton.setBackground(rojo);
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
         * Gestiona el tractament del Boto Afegir Expressio
         */
        ActionListener botoAfegir = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per afegir una expressio booleana
             * @param e event de pitjar el boto Sortir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()) {
                    String errorMensaje = "Cal introduir els parametres necessaris";
                    mensaje(WARNING, errorMensaje);
                }

                else {
                    String expressio = textField1.getText();
                    String[] expresionesModificadas = CtrlPresentacion.crearExpressio(expressio);
                    list1.setListData(expresionesModificadas);
                    mensaje("Expressió Afegida", "S'ha afegit correctament l'expressió");
                    textField1.setText("");
                }
            }
        };

        /**
         * Gestiona el tractament del Boto Eliminar Expressio
         */
        ActionListener botoEliminar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per eliminar una expressio booleana
             * @param e event de pitjar el boto Sortir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(elementoSeleccionado == null) {
                    mensaje(WARNING, "Cal introduir els parametres necessaris");
                }

                else {
                    String[] expresionesModificadas = CtrlPresentacion.eliminarExpressio(elementoSeleccionado);
                    list1.setListData(expresionesModificadas);
                    mensaje("Expressió Eliminada", "S'ha eliminat correctament l'expressió");
                }
            }
        };

        /**
         * Gestiona el tractament del Boto Modificar Expressio
         */
        ActionListener botoModificar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament per modificar una expressio booleana
             * @param e event de pitjar el boto Sortir
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(elementoSeleccionado == null) {
                    String errorMensaje = "Cal introduir els parametres necessaris";
                    mensaje(WARNING, errorMensaje);
                }
                else if(textField2.getText().isEmpty()) {
                    String errorMensaje = "Cal introduir els parametres necessaris";
                    mensaje(WARNING, errorMensaje);
                }

                else {
                    String novaexpressio = textField2.getText();
                    String[] expresionesModificadas = CtrlPresentacion.modificarExpressio(elementoSeleccionado,novaexpressio);
                    list1.setListData(expresionesModificadas);
                    mensaje("Expressio Modificada", "S'ha modificat correctament l'expressio");
                    textField2.setText("");

                }
            }
        };

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                elementoSeleccionado = (String) list1.getSelectedValue();
            }
        });

        /**
         *Gestiona el tancament de la finestra de la funcionalitat
         * @param e event de pitjar el boto Sortir
         */
        ActionListener botoSortir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CtrlPresentacion.OpenViewPrincipal();
                } catch (Exception ex) {
                    mensaje(ERROR, "Hi ha hagut un error inesperat");
                }
                setVisible(false);
            }
        };

        afegirButton.addActionListener(botoAfegir);
        eliminarButton.addActionListener(botoEliminar);
        modificarButton.addActionListener(botoModificar);
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
