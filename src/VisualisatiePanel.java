import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

    public class VisualisatiePanel extends Panel implements MouseListener {
        double totaleKosten = 0.0;
        double totaleBeschikbaarheid = 1.0;
        private ArrayList<Component> componenten;

        public VisualisatiePanel(ArrayList<Component> geselecteerdeComponenten) {
            super(geselecteerdeComponenten);
            this.componenten = geselecteerdeComponenten;
            setBackground(Color.white);

            setPreferredSize(new Dimension(0, 0));
            setLayout(new GridLayout(5, 2));


            // Maak menu panel aan voor verwijder knop en kosten/beschikbaarheid overzicht
            MenuPanel menuPanel = new MenuPanel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.PAGE_END;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
//            add(menuPanel, gbc);
        }

        public void voegComponentToe(Component component) {
            // Toevoegen componenten
            componenten.add(component);
            tekenVisualisatiePanel();

            berekenKosten(component);
            berekenBeschikbaarheid(component);
        }

        public double berekenBeschikbaarheid(Component component) {
            double beschikbaarheidPfsense = 1;
            double beschikbaarheidDatabase = 1;
            double beschikbaarheidWeb = 1;

            // Check welk component er is aangelikt en bereken de beschikbaarheid ervan
            if (component.getType() == ComponentType.PFSENSE) {
                beschikbaarheidPfsense = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
            }
            if (component.getType() == ComponentType.DATABASESERVER) {
                beschikbaarheidDatabase = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
            }
            if (component.getType() == ComponentType.WEBSERVER) {
                beschikbaarheidWeb = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
            }

            totaleBeschikbaarheid = (beschikbaarheidPfsense * beschikbaarheidDatabase * beschikbaarheidWeb);
            System.out.println(totaleBeschikbaarheid);
            return totaleBeschikbaarheid;
        }

        public double berekenKosten(Component component) {
            // Berekenen totale kosten
            totaleKosten += component.getKosten();
            System.out.println(totaleKosten);
            return totaleKosten;
        }




        // Deze functie tekent alle componenten binnen de catalogus en zorgt ervoor dat het rechtermuisknopmenu werkt
        public void tekenVisualisatiePanel() {
            removeAll(); // Verwijder alle componenten uit het panel voordat je ze opnieuw tekent

            for (int i = 0; i < componenten.size(); i++) {
                // Het aanmaken van een panel zodat alle gegevens van een component bij elkaar blijft
                JPanel component = new JPanel();
                component.setLayout(new GridBagLayout());

                GridBagConstraints layout = new GridBagConstraints();

                // Zorgt ervoor dat de componenten in een vierkant blijven en niet strekken
                layout.fill = GridBagConstraints.NONE;

                // Zorgt ervoor dat elk label onder elkaar komt (gridy gaat steeds 1 omhoog)
                layout.gridx = 0;
                layout.gridy = 0;

                JLabel jlAfbeelding = new JLabel(componenten.get(i).getAfbeelding());
                jlAfbeelding.setName(String.valueOf(i)); // Set a unique ID for the label
                jlAfbeelding.addMouseListener(this); // DIT IS KAPOT OFZO JOCHEM KIJK HIER FF NAAR
                component.add(jlAfbeelding);

                layout.gridy = 1;
                JLabel jlNaam = new JLabel(componenten.get(i).getNaam());
                component.add(jlNaam, layout);

                layout.gridy = 2;
                JLabel jlKosten = new JLabel("€" + componenten.get(i).getKosten());
                component.add(jlKosten, layout);

                layout.gridy = 3;
                JLabel jlBeschikbaarheid = new JLabel(componenten.get(i).getBeschikbaarheid() + "%");
                component.add(jlBeschikbaarheid, layout);

                // Het toevoegen van het component
                add(component);

            }
            revalidate(); // Herlaad het panel zodat de nieuwe componenten getoond worden


            JButton verwijderButton = new JButton("Verwijder alles");
            verwijderButton.setLocation(200, 200);
            add(verwijderButton);
            verwijderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeAll();
                    repaint();
                    componenten.clear();
                    totaleKosten = 0.0;
                }
            });


        }



        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

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
