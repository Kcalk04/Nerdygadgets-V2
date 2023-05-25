import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OverviewPanel extends Panel {
    public ArrayList<Component> componentenOverzicht;

    public OverviewPanel(ArrayList<Component> componenten) {
        super(componenten);
        Component pfsense = new Component("PFSense", ComponentType.PFSENSE);
        Component database = new Component("Databaseserver",ComponentType.DATABASESERVER);
        Component web = new Component("Webserver", ComponentType.WEBSERVER);

        componentenOverzicht = new ArrayList<>();

        componentenOverzicht.add(pfsense);
        componentenOverzicht.add(database);
        componentenOverzicht.add(web);

        tekenOverviewPanel();
        setBackground(Color.lightGray);
        setLayout(new GridLayout(3, 1));
    }





    public void tekenOverviewPanel () {
        removeAll(); // Verwijder alle componenten uit het panel voordat je ze opnieuw tekent


        for (int i = 0; i < componentenOverzicht.size(); i++) {
            // Het aanmaken van een panel zodat alle gegevens van een component bij elkaar blijft
            JPanel component = new JPanel();
            component.setLayout(new GridBagLayout());

            GridBagConstraints layout = new GridBagConstraints();

            // Zorgt ervoor dat de componenten in een vierkant blijven en niet strekken
            layout.fill = GridBagConstraints.NONE;

            // Zorgt ervoor dat elk label onder elkaar komt (gridy gaat steeds 1 omhoog)
            layout.gridx = 0;
            layout.gridy = 2;

            JLabel jlAfbeelding = new JLabel(componentenOverzicht.get(i).getAfbeelding());
            component.add(jlAfbeelding);

            VisualisatiePanel visualisatiePanel = new VisualisatiePanel(componenten);
            double[] kostenArray = visualisatiePanel.berekenKosten();
            double kostenPfsense = kostenArray[0];
            double kostenDatabase = kostenArray[1];
            double kostenWeb = kostenArray[2];
            double totaleKosten = kostenArray[3];


            layout.gridy = 1;
            JLabel jlNaam = new JLabel("Aantal " + componentenOverzicht.get(i).getNaam());
            component.add(jlNaam, layout);

            layout.gridy = 2;
            JLabel jlKosten = new JLabel("€");
            component.add(jlKosten, layout);

            layout.gridy = 3;
            JLabel jlBeschikbaarheid = new JLabel("%");
            component.add(jlBeschikbaarheid, layout);


            // Het toevoegen van het component
            add(component);

        }
        revalidate(); // Herlaad het panel zodat de nieuwe componenten getoond worden

    }
}
