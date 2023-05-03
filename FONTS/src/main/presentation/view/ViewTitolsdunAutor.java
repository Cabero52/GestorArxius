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

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class ViewTitolsdunAutor extends JFrame{

    private final String WARNING = "WARNING";

    private final JLabel labelAutor = new JLabel("Autor: ");
    private final JLabel label2 = new JLabel("Ordre: ");

    private final JButton buscarButton = new JButton("BUSCAR");

    private final JButton sortirButton = new JButton("SORTIR");

    private final JList list1 = new JList();
    private final JScrollPane scrollBarautor = new JScrollPane(list1);
    private final JComboBox comboBox1 = new JComboBox();

    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();

    private String autorSeleccionat;


    public ViewTitolsdunAutor() {
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("LLISTAR TíTOLS D'UN AUTOR");

        String[] totsAutors  = CtrlPresentacion.getautors();
        list1.setListData(totsAutors);

        scrollBarautor.setBounds(150, 100, 800, 70);
        add(scrollBarautor);

        labelAutor.setBounds(30, 105, 100, 50);
        add(labelAutor);

        label2.setBounds(30,190,100,50);
        add(label2);

        comboBox1.setBounds(150,200,100,40);
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

        comboBox1.addItem("A-Z");
        comboBox1.addItem("Z-A");

        /**
         * Gestiona el tractament del Boto Buscar
         */
        ActionListener botoBuscar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament
             * @param e event de pitjar el boto Buscar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(autorSeleccionat == null) {
                    mensaje(WARNING,"Cal introduir els parametres necessaris");
                }

                else {
                    String ordre = comboBox1.getSelectedItem().toString();
                    if(ordre == "A-Z")ordre = "a";
                    else ordre = "d";
                    String titols[] = CtrlPresentacion.llistarTitolsAutor(autorSeleccionat,ordre);
                    if(titols.length == 0)  {
                        mensaje(WARNING, "L'autor '"+autorSeleccionat+"' no existeix");
                    }
                    else {
                        String missatge = "Els títols de l'autor '"+autorSeleccionat+"'";
                        ViewLlistar vL = new ViewLlistar(missatge, titols);
                        setVisible(false);
                    }
                }
            }
        };

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                autorSeleccionat = (String) list1.getSelectedValue();
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
                    String errorTitol = WARNING;
                    String errorMensaje = "Hi ha hagut un error inesperat";
                    mensaje(errorTitol,errorMensaje);
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
        txt.setBounds(100,30,400,50);
        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(350,200,100,50);

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
