import presentation.view.ViewMenuPrincipal;


/**
 * Aquest Driver serveix per poder executar l'applicacio i executar els metodes que conte
 */

public class DriverPresentacio {

    /**
     * Metode que permet editar l'aparença de l'aplicacio
     */
    public static void establecerLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws Exception {
        establecerLookAndFeel();
        ViewMenuPrincipal vmMenuP = new ViewMenuPrincipal();
    }
}
