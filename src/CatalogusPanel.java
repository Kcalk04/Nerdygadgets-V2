import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CatalogusPanel extends Panel {

    public boolean isHighlighted = false;
    private ArrayList<Component> catalogusComponenten;

    public CatalogusPanel(ArrayList<Component> componenten) {
        super(componenten);

        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(0, 0));
        setLayout(new GridLayout(5, 2));

        catalogusComponenten = new ArrayList<>();

        // Het initialiseren van de verschillende componenten
        Component pfsense = new Component("pfSense", 4000, 90, ComponentType.PFSENSE);
        Component mySQL1 = new Component("HAL9001DB", 5100, 90, ComponentType.DATABASESERVER);
        Component mySQL2 = new Component("HAL9002DB", 7700, 95, ComponentType.DATABASESERVER);
        Component mySQL3 = new Component("HAL9003DB", 12200, 98, ComponentType.DATABASESERVER);
        Component apacheServer1 = new Component("HAL9001W", 2200, 80, ComponentType.WEBSERVER);
        Component apacheServer2 = new Component("HAL9002W", 3200, 90, ComponentType.WEBSERVER);
        Component apacheServer3 = new Component("HAL9002W", 5100, 95, ComponentType.WEBSERVER);


        // Het toevoegen van de componenten aan de catelogus
        catalogusComponenten.add(pfsense);
        catalogusComponenten.add(apacheServer1);
        catalogusComponenten.add(apacheServer2);
        catalogusComponenten.add(apacheServer3);
        catalogusComponenten.add(mySQL1);
        catalogusComponenten.add(mySQL2);
        catalogusComponenten.add(mySQL3);

        tekenCatelogus();
    }

    // Deze functie tekent alle componenten binnen de catelogus en zorgt ervoor dat rechtermuisknop menu werkt
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


            // MouseListener aan afbeeldingslabel koppelen
            jlAfbeelding.addMouseListener(new MouseAdapter() {
                // Als linkermuisknop geklikt wordt, maak popup menuutje aan met toevoegen optie
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        isHighlighted = true;
                        // Verander border color wanneer de label gehighlight is
                        jlAfbeelding.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                        jlAfbeelding.setOpaque(true);

                        // Maak de popup menu aan
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem toevoegen = new JMenuItem("Toevoegen");
                        popupMenu.add(toevoegen);

                        // Popup laten zien op de coordinaten van de cursor
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
                public void mouseExited(MouseEvent e) {
                    // Check of linkermuisknop geklikt is, dan niet de kleur aanpassen
                    if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
                        return;
                    }

                    // Als de linkermuisknop niet ingedrukt is, doe normale kleuren
                    jlAfbeelding.setBorder(BorderFactory.createEmptyBorder());
                    jlAfbeelding.setOpaque(false);
                }
            });
        }
    }
}
