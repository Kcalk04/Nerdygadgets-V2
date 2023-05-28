import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OverviewPanel extends Panel {
    public ArrayList<Component> componentenOverzicht;
    private VisualisatiePanel visualisatiePanel;

    public OverviewPanel(ArrayList<Component> componenten, VisualisatiePanel visualisatiePanel) {
        super(componenten);
        this.visualisatiePanel = visualisatiePanel;

        // Toevoegen componenten voor het overzicht
        componentenOverzicht = new ArrayList<>();

        Component pfsense = new Component("PFSense", ComponentType.PFSENSE);
        Component database = new Component("Databaseserver",ComponentType.DATABASESERVER);
        Component web = new Component("Webserver", ComponentType.WEBSERVER);
        componentenOverzicht.add(pfsense);
        componentenOverzicht.add(database);
        componentenOverzicht.add(web);

        tekenOverviewPanel();
        setBackground(Color.lightGray);
        setLayout(new GridLayout(4, 1));
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

            // Voor elk verschillend componentType de aantallen in een label stoppen
            layout.gridy = 1;
            JLabel jlAantal = new JLabel();
            if (componentenOverzicht.get(i).getType().equals(ComponentType.PFSENSE)) {
                jlAantal = new JLabel("Aantal: " + SimulatieFrame.visualisatiePanel.aantalPfsense);
            } else if (componentenOverzicht.get(i).getType().equals(ComponentType.DATABASESERVER)) {
                jlAantal = new JLabel("Aantal: " + SimulatieFrame.visualisatiePanel.aantalDatabase);
            } else if (componentenOverzicht.get(i).getType().equals(ComponentType.WEBSERVER)) {
                jlAantal = new JLabel("Aantal: " + SimulatieFrame.visualisatiePanel.aantalWeb);
            }
            component.add(jlAantal, layout);

            // Voor elk verschillend componentType de kosten in een label stoppen
            layout.gridy = 2;
            JLabel jlKosten = new JLabel();
            if (componentenOverzicht.get(i).getType().equals(ComponentType.PFSENSE)) {
                jlKosten = new JLabel("Kosten: €" + SimulatieFrame.visualisatiePanel.kostenPfsense);
            } else if (componentenOverzicht.get(i).getType().equals(ComponentType.DATABASESERVER)) {
                jlKosten = new JLabel("Kosten: €" + SimulatieFrame.visualisatiePanel.kostenDatabase);
            } else if (componentenOverzicht.get(i).getType().equals(ComponentType.WEBSERVER)) {
                jlKosten = new JLabel("Kosten: €" + SimulatieFrame.visualisatiePanel.kostenWeb);
            }
            component.add(jlKosten, layout);

        // Voor elk verschillend componentType de beschikbaarheid in een label stoppen
            layout.gridy = 3;
            JLabel jlBeschikbaarheid = new JLabel();
            if (componentenOverzicht.get(i).getType().equals(ComponentType.PFSENSE)) {
                jlBeschikbaarheid = new JLabel((SimulatieFrame.visualisatiePanel.beschikbaarheidPfsense * 100)+ "%");
            } else if (componentenOverzicht.get(i).getType().equals(ComponentType.DATABASESERVER)) {
                jlBeschikbaarheid = new JLabel((SimulatieFrame.visualisatiePanel.beschikbaarheidDatabase * 100) + "%");
            } else if (componentenOverzicht.get(i).getType().equals(ComponentType.WEBSERVER)) {
                jlBeschikbaarheid = new JLabel((SimulatieFrame.visualisatiePanel.beschikbaarheidWeb * 100) + "%");
            }
            component.add(jlBeschikbaarheid, layout);
            // Het toevoegen van het component
            add(component);

        }
        // Maak nieuw panel aan voor 4e rij voor totalen
        JPanel totaalPanel = new JPanel();
        totaalPanel.setLayout(new GridBagLayout());
        totaalPanel.setBackground(Color.lightGray);
        Border border = new LineBorder(Color.BLACK, 2, false);
        totaalPanel.setBorder(border);

        // Grid bag constraints voor 4e rij
        GridBagConstraints layout = new GridBagConstraints();
        layout.fill = GridBagConstraints.NONE;
        layout.gridy = 0;

        // Rond het beschikbaarheidspercentage op 4 decimalen én naar beneden af
        double totaleBeschikbaarheidNietAfgerond = SimulatieFrame.visualisatiePanel.totaalPercentage;
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String totaleBeschikbaarheidAfgerond = decimalFormat.format(totaleBeschikbaarheidNietAfgerond);


        // Voeg labels toe
        JLabel totaalAantalLabel = new JLabel("Totaal aantal machines: " + SimulatieFrame.visualisatiePanel.totaalAantal);
        JLabel totaleKostenLabel = new JLabel("Totale kosten: €" + SimulatieFrame.visualisatiePanel.totaleKosten);

        // Check of beschikbaarheid 0.0 is, dan 0.0 aanhouden, anders afgeronde percentage laten zien
        JLabel totaleBeschikbaarheidLabel;
        JLabel waarschuwingLabel1;
        JLabel waarschuwingLabel2;
        JLabel witregelLabel = new JLabel(" ");
        if(SimulatieFrame.visualisatiePanel.totaalPercentage == 0.0) {
            totaleBeschikbaarheidLabel = new JLabel("Totale beschikbaarheid: 00,0000" + "%");
            waarschuwingLabel1 = new JLabel("Voeg minimaal één van elk type toe");
            waarschuwingLabel2 = new JLabel("om beschikbaarheid te zien");
        } else {
            totaleBeschikbaarheidLabel = new JLabel("Totale beschikbaarheid: " + totaleBeschikbaarheidAfgerond + "%");
            waarschuwingLabel1 = new JLabel("");
            waarschuwingLabel2 = new JLabel("");
        }

        // Toevoegen labels aan panel met gridy++ voor de spacing
        totaalPanel.add(totaalAantalLabel, layout);
        layout.gridy++;
        totaalPanel.add(totaleKostenLabel, layout);
        layout.gridy++;
        totaalPanel.add(totaleBeschikbaarheidLabel, layout);
        layout.gridy++;
        totaalPanel.add(witregelLabel, layout);
        layout.gridy++;
        waarschuwingLabel1.setForeground(Color.RED);
        waarschuwingLabel2.setForeground(Color.RED);
        totaalPanel.add(waarschuwingLabel1, layout);
        layout.gridy++;
        totaalPanel.add(waarschuwingLabel2, layout);

        // Toevoegen 4e rij panel aan main panel
        add(totaalPanel);

        revalidate(); // Herlaad het panel

    }
}
