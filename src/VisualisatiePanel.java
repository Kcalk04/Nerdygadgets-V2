import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

    public class VisualisatiePanel extends Panel implements ActionListener, MouseListener {
        double totaleKosten = 0.0;
        private ArrayList<Component> componenten;
        public JMenuItem jmiVerwijderen;
        public int geselecteerdComponentID;
        double kostenPfsense = 0;
        double kostenDatabase = 0;
        double kostenWeb = 0;
        double totaalPercentage = 0;
        double beschikbaarheidPfsense = 0;
        double beschikbaarheidWeb = 0;
        double beschikbaarheidDatabase = 0;
        int aantalPfsense = 0;
        int aantalDatabase = 0;
        int aantalWeb = 0;
        int totaalAantal = 0;

        public VisualisatiePanel(ArrayList<Component> geselecteerdeComponenten) {
            super(geselecteerdeComponenten);
            this.componenten = geselecteerdeComponenten;
            setBackground(Color.white);

            setPreferredSize(new Dimension(0, 0));
            setLayout(new GridLayout(5, 2));
        }

        public void voegComponentToe(Component component) {
            // Toevoegen componenten
            if(componenten.size() < 12) {
                componenten.add(component);
                tekenVisualisatiePanel();

                // Aanroepen methodes om bij het toevoegen van elk component de gegevens te updaten
                berekenKosten();
                berekenBeschikbaarheid();
                berekenAantal();
                SimulatieFrame.overviewPanel.tekenOverviewPanel();
            } else {
                System.out.println("Maximaal aantal componenten van 12 bereikt");
            }
        }


        public void berekenBeschikbaarheid() {
            // Zet variabalen op 0 en 1
            totaalPercentage = 0;
            beschikbaarheidPfsense = 1;
            beschikbaarheidWeb = 1;
            beschikbaarheidDatabase = 1;

            // Loop door de componenten heen en bereken voor elk ComponentType apart de beschikbaarheid
            for (Component component: componenten) {
                if (component.getType() == ComponentType.PFSENSE) {
                    beschikbaarheidPfsense *= (1 - (component.getBeschikbaarheid() / 100));
                }
                if (component.getType() == ComponentType.WEBSERVER) {
                    beschikbaarheidWeb *= (1 - (component.getBeschikbaarheid() / 100));
                }
                if (component.getType() == ComponentType.DATABASESERVER) {
                    beschikbaarheidDatabase *= (1 - (component.getBeschikbaarheid() / 100));
                }
            }
            // Herschrijf de beschikbaarheid om het op te kunnen tellen
            beschikbaarheidPfsense = 1 - beschikbaarheidPfsense;
            beschikbaarheidWeb = 1 - beschikbaarheidWeb;
            beschikbaarheidDatabase = 1 - beschikbaarheidDatabase;

            // Berekenen totaalpercentage
            totaalPercentage = (beschikbaarheidPfsense * beschikbaarheidWeb * beschikbaarheidDatabase) * 100;
        }

        public void berekenKosten() {
            // Zet variabelen op 0
            kostenPfsense = 0;
            kostenDatabase = 0;
            kostenWeb = 0;
            totaleKosten = 0;

            // Loop door de componenten heen en bereken voor elk ComponentType apart de kosten
            for (Component component : componenten) {
                if (component.getType() == ComponentType.PFSENSE) {
                    kostenPfsense += component.getKosten();
                }
                if (component.getType() == ComponentType.DATABASESERVER) {
                    kostenDatabase += component.getKosten();
                }
                if (component.getType() == ComponentType.WEBSERVER) {
                    kostenWeb += component.getKosten();
                }
            }
            // Tel de kosten bij elkaar op
            totaleKosten = kostenPfsense + kostenDatabase + kostenWeb;
            }

        public void berekenAantal() {
            // Zet variabelen op 0
            aantalPfsense = 0;
            aantalDatabase = 0;
            aantalWeb = 0;
            totaalAantal = 0;

            for (Component component : componenten) {
                // Loop door de componenten heen en bereken voor elk ComponentType apart het aantal
                if (component.getType() == ComponentType.PFSENSE) {
                    aantalPfsense ++;
                }
                if (component.getType() == ComponentType.DATABASESERVER) {
                    aantalDatabase++;
                }
                if (component.getType() == ComponentType.WEBSERVER) {
                    aantalWeb++;
                }
            }
            // Bereken het totaal aantal
            totaalAantal = aantalPfsense + aantalDatabase + aantalWeb;
        }

        public void clearAlleWaardes() {
            // Zet alle waardes op 0
            kostenPfsense = 0.0;
            kostenDatabase = 0.0;
            kostenWeb = 0.0;
            totaleKosten = 0.0;

            beschikbaarheidPfsense = 0.0;
            beschikbaarheidDatabase = 0.0;
            beschikbaarheidWeb = 0.0;
            totaalPercentage = 0.0;

            aantalPfsense = 0;
            aantalDatabase = 0;
            aantalWeb = 0;
            totaalAantal = 0;
        }


            // Deze functie tekent alle componenten binnen de catalogus
            public void tekenVisualisatiePanel () {
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
                    layout.gridy = 2;

                    JLabel jlAfbeelding = new JLabel(componenten.get(i).getAfbeelding());
                    jlAfbeelding.setName(String.valueOf(i)); // Set a unique ID for the label
                    jlAfbeelding.addMouseListener(this);
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
                    jlAfbeelding.addMouseListener(this);
                    add(component);

                }
                revalidate(); // Herlaad het panel zodat de nieuwe componenten getoond worden


                // Knop om alle componenten te verwijderen
                JButton verwijderButton = new JButton("Verwijder alles");
                verwijderButton.setLocation(200, 200);
                verwijderButton.setFocusable(false);
                add(verwijderButton);
                verwijderButton.addActionListener(new ActionListener() {
                    // ActionListener voor confirmation en het clearen van de panel
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int input = JOptionPane.showConfirmDialog(null, "Weet je zeker dat je alles wilt verwijderen", "Waarschuwing", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (input == JOptionPane.YES_OPTION) {
                            removeAll();
                            repaint();

                            componenten.clear();
                            clearAlleWaardes();
                            removeAll();
                            SimulatieFrame.overviewPanel.tekenOverviewPanel();
                        }
                    }
                });
            }
            public double aftrekkenKosten (Component component){
            // Aftrekken kosten wanneer een component verwijderd wordt
                totaleKosten -= component.getKosten();
                System.out.println(totaleKosten);
                return totaleKosten;
            }

            @Override
            public void actionPerformed (ActionEvent e){
            // Als een individueel component verwijderd wordt worde alle bereken methodes opnieuw aangeroepen om de correcte data te berekenen
                if (e.getSource() == jmiVerwijderen) {
                    aftrekkenKosten(componenten.get(geselecteerdComponentID));
                    componenten.remove(geselecteerdComponentID);

                    SimulatieFrame.visualisatiePanel.berekenKosten();
                    SimulatieFrame.visualisatiePanel.berekenBeschikbaarheid();
                    SimulatieFrame.visualisatiePanel.berekenAantal();
                    SimulatieFrame.overviewPanel.tekenOverviewPanel();

                    tekenVisualisatiePanel();
                    repaint();
                }
            }

            public void mousePressed (MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)) {

                    // Maak de popup menu aan
                    JPopupMenu popupMenu = new JPopupMenu();
                    jmiVerwijderen = new JMenuItem("Verwijderen");
                    jmiVerwijderen.addActionListener(this);

                    JLabel clickedLabel = (JLabel) e.getComponent(); // Haal de geklikte label op
                    String imageId = clickedLabel.getName(); // Haal de bijbehorende ID bij het label op
                    int id = Integer.parseInt(imageId);

                    geselecteerdComponentID = id;

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

