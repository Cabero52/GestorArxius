package presentation.view;

import domain.classes.exceptions.DocumentNoExisteix;
import domain.classes.exceptions.JaExisteixException;
import domain.classes.exceptions.NoExisteixException;
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

public class ViewEditarDocument extends JFrame {
    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JLabel label1 = new JLabel("Autor: ");
    private final JLabel label2 = new JLabel("Títol: ");
    private final JLabel label3 = new JLabel("Contigut: ");

    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    private final JTextArea textArea1 = new JTextArea();

    private final JScrollPane verticalScrollBar = new JScrollPane(textArea1);

    private final JButton editarButton = new JButton("EDITAR");
    private final JButton sortirButton = new JButton("SORTIR");

    private final JPanel panell = new JPanel();
    private final JFrame frame = new JFrame("JFrame");

    public ViewEditarDocument(String autor, String titol, String contingut){
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("EDITAR DOCUMENT");

        setLayout(null);

        textField1.setBounds(150,30,1000,50);
        add(textField1);

        label1.setBounds(30,30,100,50);
        add(label1);

        textField2.setBounds(150,115,1000,50);
        add(textField2);

        label2.setBounds(30,115,100,50);
        add(label2);

        textArea1.setLineWrap(true);

        label3.setBounds(30,200,100,50);
        add(label3);
        verticalScrollBar.setBounds(150,190,950,260);
        add(verticalScrollBar);


        editarButton.setBounds(300,470,600,80);
        add(editarButton);

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

        textField1.setText(autor);
        textField2.setText(titol);
        textArea1.setText(contingut);


        /**
         * Gestiona el tractament del Boto Editar
         */
        ActionListener botoEditar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea1.getText().isEmpty() || textField1.getText().isEmpty() || textField2.getText().isEmpty()) {
                    mensaje(WARNING, "Cal introduir els paràmetres necessaris",0);
                }
                else {
                    String nouautor = textField1.getText();
                    String noutitol = textField2.getText();
                    String noucontingut = textArea1.getText();

                    try {
                        CtrlPresentacion.editarDoc(autor,nouautor,titol,noutitol,contingut,noucontingut);
                        mensaje("Document editat", "S'ha editat el doc amb autor '"
                                +autor+"' i titol '"+titol+"'",1);




                    } catch (NoExisteixException | DocumentNoExisteix ex) {
                        mensaje(ERROR,"El doc amb autor '"+autor+"' i titol '"+titol+"' " +
                                "que es vol editar no existeix",0);
                    } catch (JaExisteixException ex) {
                        mensaje(ERROR,"Ja existeix un doc amb la informacio que desitjes guardar",0);
                    } catch (NullException ex) {
                        mensaje(ERROR, "L'autor o el títol està buit",0);
                    } catch (DocumentNoTrobat ex) {
                        mensaje(ERROR, "El path introduit es erroni",0);
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
                    mensaje(WARNING,"Hi ha hagut un error inesperat",0);
                }
                setVisible(false);
            }
        };

        editarButton.addActionListener(botoEditar);
        sortirButton.addActionListener(botoSortir);
    }

    /**
     * Metode que permet indicar un misstage pop-up
     * @param errorTitol parametre que identifica el titol del popup
     * @param errorMensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensaje(String errorTitol, String errorMensaje, int k) {
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
                if (k == 1) {
                    try {
                        setVisible(false);
                        CtrlPresentacion.OpenViewPrincipal();
                    } catch (Exception ex) {
                        mensaje(WARNING, "Hi ha hagut un error inesperat", 0);
                    }
                }
            }
        };
        bSortir.addActionListener(Sortir);
    }
}

