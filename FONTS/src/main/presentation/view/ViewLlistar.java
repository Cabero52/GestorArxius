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
public class ViewLlistar extends JFrame {
    private final String WARNING = "WARNING";

    private final JTextArea textArea1 = new JTextArea();
    private final JLabel titol = new JLabel();
    private final JButton sortirButton = new JButton("SORTIR");

    private final JScrollPane verticalScrollBar = new JScrollPane(textArea1);
    private final JPanel panell = new JPanel();
    private final JFrame frame = new JFrame();

    public ViewLlistar(String missatge, String[] informacio) {
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("LLISTAR DOCUMENTS");

        textArea1.setLineWrap(true);

        verticalScrollBar.setBounds(150,100,900,375);
        add(verticalScrollBar);

        titol.setFont(titol.getFont().deriveFont(Font.BOLD, 25f));
        titol.setBounds(150,20,1000,60);
        add(titol);

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

        /**
         * Permet el tractament de la informacio que rep per a mostrar-ho correctament
         */
        String contenido = "";
        for (int i = 0; i < informacio.length; ++i) {
            contenido += informacio[i] + "\n";
        }
        textArea1.setEditable(false);
        textArea1.setText(contenido);
        textArea1.setFont(titol.getFont().deriveFont(Font.PLAIN, 18f));

        titol.setText(missatge);
        titol.setText(titol.getText().toUpperCase());

        /**
         * Gestiona el tractament del Boto Sortir
         */
        ActionListener bSortir = new ActionListener() {
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

        sortirButton.addActionListener(bSortir);
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

        ActionListener SortirError = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noDoc.setVisible(false);
            }
        };
        sortirButton.addActionListener(SortirError);
    }


}

