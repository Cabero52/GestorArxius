package presentation.view;

import presentation.controllers.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class ViewMostraDocument extends JFrame{
    private final String WARNING = "WARNING";

    private final JTextArea textArea1 = new JTextArea();
    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();

    private final JButton sortirButton = new JButton("SORTIR");

    private final JScrollPane verticalScrollBar = new JScrollPane(textArea1);

    private final JLabel label1 = new JLabel("Autor: ");
    private final JLabel label2 = new JLabel("Títol: ");
    private final JLabel label3 = new JLabel("Contingut: ");


    private final JFrame frame = new JFrame("JFrame");
    private final JPanel panell = new JPanel();


    public ViewMostraDocument(String autor, String titol, String contingut){
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("MOSTRAR DOCUMENT");

        textField1.setBounds(150,30,1000,50);
        add(textField1);

        label1.setBounds(30,30,100,50);
        add(label1);

        textField2.setBounds(150,115,1000,50);
        add(textField2);

        label2.setBounds(30,115,100,50);
        add(label2);

        textArea1.setLineWrap(true);

        verticalScrollBar.setBounds(150,190,1000,260);
        add(verticalScrollBar);

        label3.setBounds(30,200,100,50);
        add(label3);

        Color rojo = new Color(255,105,97);
        sortirButton.setBackground(rojo);

        sortirButton.setBounds(1050,485,120,80);
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

        textField1.setText(autor);
        textField1.setEditable(false);
        textField2.setText(titol);
        textField2.setEditable(false);
        textArea1.setText(contingut);
        textArea1.setEditable(false);

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
                    mensajeError(WARNING,"Hi ha hagut un error inesperat");
                }
                setVisible(false);
            }
        };

        sortirButton.addActionListener(botoSortir);
    }

    /**
     * Metode que permet indicar un misstage pop-up
     * @param errorTitol parametre que identifica el titol del popup
     * @param errorMensaje parametre que conte el missatje que es mostra en el pop-up
     */
    private void mensajeError(String errorTitol, String errorMensaje) {
        JDialog noDoc = new JDialog(frame, errorTitol);
        noDoc.setBounds(500,300,500,300);
        noDoc.setLayout(null);

        JLabel txtError = new JLabel(errorMensaje);
        txtError.setBounds(100,30,400,50);

        JButton bSortir = new JButton("Sortir");
        bSortir.setVisible(true);
        bSortir.setBounds(350,200,100,50);

        noDoc.add(txtError);
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
