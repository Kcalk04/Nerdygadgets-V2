import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CatalogusPanel extends Panel implements ActionListener, MouseListener {
    public int geselecteerdComponentID;
    public String componentNaam;
    public ArrayList<Component> catalogusComponenten;
    public JMenuItem jmiToevoegen;
    public JMenuItem jmiVerwijderen;

    public CatalogusPanel(ArrayList<Component> componenten) {
        super(componenten);

        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(0, 0));
        setLayout(new GridLayout(5, 2));

        catalogusComponenten = new ArrayList<>();
        // Het initialiseren van de verschillende componenten
        Component pfsense = new Component("pfSense", 4000, 99.998, ComponentType.PFSENSE);
        Component mySQL1 = new Component("HAL9001DB", 5100, 90, ComponentType.DATABASESERVER);
        Component mySQL2 = new Component("HAL9002DB", 7700, 95, ComponentType.DATABASESERVER);
        Component mySQL3 = new Component("HAL9003DB", 12200, 98, ComponentType.DATABASESERVER);
        Component apacheServer1 = new Component("HAL9001W", 2200, 80, ComponentType.WEBSERVER);
        Component apacheServer2 = new Component("HAL9002W", 3200, 90, ComponentType.WEBSERVER);
        Component apacheServer3 = new Component("HAL9003W", 5100, 95, ComponentType.WEBSERVER);


        // Het toevoegen van de componenten aan de catelogus
        catalogusComponenten.add(pfsense);
        catalogusComponenten.add(apacheServer1);
        catalogusComponenten.add(apacheServer2);
        catalogusComponenten.add(apacheServer3);
        catalogusComponenten.add(mySQL1);
        catalogusComponenten.add(mySQL2);
        catalogusComponenten.add(mySQL3);

        Database.haalComponentenOp(catalogusComponenten);
        tekenCatelogus();
    }

    // Deze functie tekent alle componenten binnen de catelogus en zorgt ervoor dat rechtermuisknop menu werkt
    public void tekenCatelogus() {
        removeAll();
        for (int i = 0; i < catalogusComponenten.size(); i++) {

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
            jlAfbeelding.setName(String.valueOf(i)); // Set a unique ID for the label
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
            jlAfbeelding.addMouseListener(this);
            add(component);
        }
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jmiToevoegen) {
            Component voorbeeldComponent = catalogusComponenten.get(geselecteerdComponentID);
            Component component = new Component(voorbeeldComponent.getNaam(), voorbeeldComponent.getKosten(), voorbeeldComponent.getBeschikbaarheid(), voorbeeldComponent.getType());

            SimulatieFrame.visualisatiePanel.voegComponentToe(component);
            repaint();
        } else if (e.getSource() == jmiVerwijderen) {
            Database.verwijderComponent(componentNaam);
            SimulatieFrame.catalogusPanel.catalogusComponenten.remove(componentNaam);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            // Maak de popup menu aan
            JPopupMenu popupMenu = new JPopupMenu();
            jmiToevoegen = new JMenuItem("Toevoegen");
            jmiToevoegen.addActionListener(SimulatieFrame.catalogusPanel);

            jmiVerwijderen = new JMenuItem("Verwijderen");
            jmiVerwijderen.addActionListener(SimulatieFrame.catalogusPanel);

            JLabel clickedLabel = (JLabel) e.getComponent(); // Haal de geklikte label op
            String imageId = clickedLabel.getName(); // Haal de bijbehorende ID bij het label op
            int id = Integer.parseInt(imageId);
            geselecteerdComponentID = id;

            Component geselecteerdComponent = catalogusComponenten.get(geselecteerdComponentID);
            componentNaam = geselecteerdComponent.getNaam();

            popupMenu.add(jmiToevoegen);
            popupMenu.add(jmiVerwijderen);

            // Popup laten zien op de coordinaten van de cursor
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

//    public void mouseExited(MouseEvent g) { // CLEAR BORDER NA TOEVOEGEN EN DESELECT
//        // Check of linkermuisknop geklikt is, dan niet de kleur aanpassen
////                    if ((g.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
////                        return;
////                    }
//
//        // Als de linkermuisknop niet ingedrukt is, doe normale kleuren
////                    jlAfbeelding.setBorder(BorderFactory.createEmptyBorder());
////                    jlAfbeelding.setOpaque(false);
//    }
//});
//
//jlAfbeelding.addMouseListener(new MouseAdapter() {
        // Als linkermuisknop geklikt wordt, maak popup menuutje aan met toevoegen optie

//            @Override
//            public void actionPerformed(ActionEvent f) { // f moet nog voor andere dingen gebruikt worden
//                JLabel clickedLabel = (JLabel) e.getComponent(); // Haal de geklikte label op
//                String imageId = clickedLabel.getName(); // Haal de bijbehorende ID bij het label op
//                int id = Integer.parseInt(imageId);
//
//                Component voorbeeldComponent = catalogusComponenten.get(id);
//                System.out.println(id);
//                Component component = new Component(voorbeeldComponent.getNaam(), voorbeeldComponent.getKosten(), voorbeeldComponent.getBeschikbaarheid(), voorbeeldComponent.getType());
//
//                SimulatieFrame.visualisatiePanel.voegComponentToe(component);
//                SimulatieFrame.visualisatiePanel.tekenVisualisatiePanel();
////                System.out.println("Component toegevoegd");
////            }
//        }
