import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulatieFrame extends JDialog {
    public ArrayList<Component> componenten;
    public SimulatieFrame(JFrame owner, boolean modal) {
        super(owner, modal);

        setTitle("Simulatie Modus");
        setSize(new Dimension(900,720));
        setLayout(new FlowLayout());

        // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        // Initialisatie van attributen
        componenten = new ArrayList<>();

        add(new CatalogusPanel(componenten));

        // Component PFSense
        Component pfsense = new Component("Pfsense", 49.99, 90, ComponentType.DATABASESERVER);

        setVisible(true);
    }
}
