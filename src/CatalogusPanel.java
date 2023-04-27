import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CatalogusPanel extends Panel {

    private ArrayList<Component> catalogusComponenten;

    public CatalogusPanel(ArrayList<Component> componenten) {
        super(componenten);

        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(0,0));
        setLayout(new GridLayout(5,2));

        catalogusComponenten = new ArrayList<>();

        // Het initialiseren van de verschillende componenten
        Component pfsense = new Component("Pfsense", 49.99, 90, ComponentType.PFSENSE);
        Component apacheServer1 = new Component("Apache", 19.99, 70, ComponentType.WEBSERVER);
        Component apacheServer2 = new Component("Apache", 29.99, 90, ComponentType.WEBSERVER);
        Component mySQL1 = new Component("MySQL", 19.99, 70, ComponentType.DATABASESERVER);
        Component mySQL2 = new Component("MySQL", 22.99, 80, ComponentType.DATABASESERVER);
        Component mySQL3 = new Component("MySQL", 29.99, 90, ComponentType.DATABASESERVER);

        // Het toevoegen van de componenten aan de catelogus
        catalogusComponenten.add(pfsense);
        catalogusComponenten.add(apacheServer1);
        catalogusComponenten.add(apacheServer2);
        catalogusComponenten.add(mySQL1);
        catalogusComponenten.add(mySQL2);
        catalogusComponenten.add(mySQL3);

        tekenCatelogus();
    }

    // Deze functie tekent alle componenten binnen de catelogus
    private void tekenCatelogus() {
        for(int i = 0; i < catalogusComponenten.size(); i++) {

            // Het aanmaken van een panel zodat alle gegevens van een component bij elkaar blijft
            JPanel component = new JPanel();
            component.setLayout(new GridBagLayout());
            component.setBackground(Color.lightGray);

            GridBagConstraints layout = new GridBagConstraints();

            // Zorgt ervoor dat de componenten in een vierkant blijven en niet strechten
            layout.fill = GridBagConstraints.NONE;

            // Zorgt ervoor dat elk label onder elkaar komt (gridy gaat steeds 1 omhoog)
            layout.gridx = 0;
            layout.gridy = 0;

            JLabel jlAfbeelding = new JLabel(catalogusComponenten.get(i).getAfbeelding());
            component.add(jlAfbeelding);

            layout.gridy = 1;
            JLabel jlNaam = new JLabel(catalogusComponenten.get(i).getNaam());
            component.add(jlNaam, layout);

            layout.gridy = 2;
            JLabel jlKosten = new JLabel("€" + catalogusComponenten.get(i).getKosten());
            component.add(jlKosten, layout);

            layout.gridy = 3;
            JLabel jlBeschikbaarheid = new JLabel(catalogusComponenten.get(i).getBeschikbaarheid() + "%");
            component.add(jlBeschikbaarheid, layout);

            // Het toevoegen van het component
            add(component);
        }
    }
}
