package presentation.view;

import domain.classes.exceptions.NullException;
import persistence.classes.exceptions.DocumentNoTrobat;
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
 * @author Jaume Alos Cuadrado(jaume.alos@estudiantat.upc.edu)
 */

public class ViewAutoryTitol extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JList list1 = new JList();
    private final JScrollPane scrollBarautor = new JScrollPane(list1);

    private final JList list2 = new JList();
    private final JScrollPane scrollBartitol = new JScrollPane(list2);

    private final JLabel labelAutor = new JLabel("Autor: ");
    private final JLabel labelTitol = new JLabel("Títol: ");

    private final JButton sortirButton = new JButton("SORTIR");
    private final JButton buscarButton = new JButton("BUSCAR");

    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();

    private String autorSeleccionat;
    private String titolSeleccionat;
    private String[] titols;

    public ViewAutoryTitol(){
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("LLISTAR DOCUMENT PER AUTOR I TITOL");

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

        buscarButton.setBounds(300,400,600,70);
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

        /**
         * Gestiona el tractament del Boto Cercar
         */
        ActionListener botoBuscar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada i el seu tractament
             * @param e event de pitjar el boto Cercar
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(autorSeleccionat == null | titolSeleccionat == null) {
                    mensaje(WARNING, "Cal introduir els paràmetres necessaris");
                }

                else {
                    try {
                        String contingut = CtrlPresentacion.llistarDocumentPerTitolAutor(autorSeleccionat,titolSeleccionat);

                        ViewMostraDocument vMD = new ViewMostraDocument(autorSeleccionat,titolSeleccionat,contingut);
                        setVisible(false);

                    } catch (DocumentNoTrobat ex) {
                        mensaje(ERROR,"El doc amb autor '"+autorSeleccionat+"' i titol '"+titolSeleccionat +
                                "' no existeix");
                    } catch (NullException et) {
                        mensaje(ERROR,"El document no conté titol o autor");
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
