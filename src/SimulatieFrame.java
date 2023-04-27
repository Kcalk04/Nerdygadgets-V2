import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulatieFrame extends JDialog {
    public ArrayList<Component> componenten;
    public SimulatieFrame(JFrame owner, boolean modal) {
        super(owner, modal);

        setTitle("Simulatie Modus");
        setSize(new Dimension(900,720));
        setLayout(new GridBagLayout());

        // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        componenten = new ArrayList<>();
        GridBagConstraints layout = new GridBagConstraints();

        // Zorgt ervoor dat het scherm gevuld word met de Panels (strecht)
        layout.fill = GridBagConstraints.BOTH;
        layout.gridy = 0;
        layout.weighty = 1;

        // De gridx is de x-positie in het grid (het totale grid bestaat uit (x: 6, y: 0)
        // grid (x: 0, y: 0)
        layout.gridx = 0;
        layout.weightx = 2;
        add(new CatalogusPanel(componenten), layout);

        // grid (x: 2, y: 0)
        layout.gridx = 1;
        layout.weightx = 3;
        add(new VisualisatiePanel(componenten), layout);

        // grid (x: 5, y: 0)
        layout.gridx = 3;
        layout.weightx = 2;
        add(new OverviewPanel(componenten), layout);

        setVisible(true);
    }
}
