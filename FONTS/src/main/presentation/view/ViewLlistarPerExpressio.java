
package presentation.view;

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
 * @author Elias El Mrabet Abous (elias.el.mrabet@estudiantat.upc.edu)
 */
public class ViewLlistarPerExpressio extends JFrame {

    private final String ERROR = "ERROR";
    private final String WARNING = "WARNING";

    private final JTextField textField1 = new JTextField();

    private final JLabel label1 = new JLabel("Nova Expressió: ");
    private final JLabel label2 = new JLabel("Ordre: ");
    private final JLabel label3 = new JLabel("Expressions ja creades: ");

    private final JComboBox comboBox1 = new JComboBox();

    private final JList list1 = new JList();
    private final JScrollPane verticalScrollBar = new JScrollPane(list1);

    private final JButton sortirButton = new JButton ("SORTIR");
    private final JButton llistarButton = new JButton ("LLISTAR");

    private final JPanel panel1 = new JPanel();
    private final JFrame frame = new JFrame("JFrame");


    private String elementoSeleccionado;


    public ViewLlistarPerExpressio(String[] expressions) {
        frame.setLocationRelativeTo(null);
        setSize(1200,625);
        setMinimumSize(new Dimension(1200,625));
        setResizable(false);
        setTitle("LLISTAR PER EXPRESSIÓ");

        setLayout(null);

        label1.setBounds(30,200,200,50);
        add(label1);

        textField1.setBounds(200,200,900,50);
        add(textField1);

        label2.setBounds(30,290,200,50);
        add(label2);

        comboBox1.setBounds(200,300,100,40);
        add(comboBox1);

        label3.setBounds(30,100,200,50);
        add(label3);

        list1.setListData(expressions);

        verticalScrollBar.setBounds(200,100,900,70);
        add(verticalScrollBar);

        Color rojo = new Color(255,105,97);
        sortirButton.setBackground(rojo);

        llistarButton.setBounds(300,400,600,90);
        add(llistarButton);

        sortirButton.setBounds(1050,475,120,80);
        add(sortirButton);

        add(panel1);

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
         * Gestiona el tractament del Boto Llistar
         */
        ActionListener botoLlistar = new ActionListener() {
            /**
             * Gestiona els parametres d'entrada per processar la informacio que cumpleix els requisits
             * @param e event de pitjar el boto Llistar
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if(elementoSeleccionado != null){
                    String ordre = comboBox1.getSelectedItem().toString();
                    if(ordre == "A-Z")ordre = "a";
                    else ordre = "d";
                    String[] documents = new String[0];
                    try {

                        documents = CtrlPresentacion.llistarDocumentsPerExpressio(elementoSeleccionado, ordre);
                        String missatge = "";
                        if (documents.length != 0)
                            missatge = "Els documents que compleixen l'expressió";
                        else
                            missatge = "No hi ha cap document que compleixi l'expressio";

                        ViewLlistar vL = new ViewLlistar(missatge,documents);
                        setVisible(false);

                    } catch (DocumentNoTrobat pne) {
                        mensajeError(WARNING,"Hi ha hagut un error");
                    }
                }

                else if(textField1.getText().isEmpty()) {
                    String errorMensaje = "Cal introduir els parametres necessaris";
                    mensajeError(WARNING, errorMensaje);
                }

                else {
                    String expressio = textField1.getText();
                    String ordre = comboBox1.getSelectedItem().toString();
                    if(ordre == "A-Z")ordre = "a";
                    else ordre = "d";
                    String[] documents = new String[0];
                    try {
                        CtrlPresentacion.crearExpressio(expressio);
                        documents = CtrlPresentacion.llistarDocumentsPerExpressio(expressio, ordre);
                        textField1.setText("");
                        String missatge = "";
                        if (documents.length != 0)
                            missatge = "Els documents que compleixen l'expressió introduida";
                        else missatge = "No hi ha cap document que compleixi  l'expressió introduida";
                        ViewLlistar vL = new ViewLlistar(missatge,documents);
                        setVisible(false);

                    } catch (DocumentNoTrobat pne) {
                        mensajeError(WARNING,"Hi ha hagut un error");
                    }
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
                    String errorMensaje = "Hi ha hagut un error inesperat";
                    mensajeError(WARNING,errorMensaje);                }
                setVisible(false);
            }
        };

        llistarButton.addActionListener(botoLlistar);
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
