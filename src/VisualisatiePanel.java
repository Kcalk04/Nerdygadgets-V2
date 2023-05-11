import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VisualisatiePanel extends Panel {
    public boolean isHighlighted = false;
    public void voegComponentToe(Component component) {
        componenten.add(component);
    }

    public VisualisatiePanel(ArrayList<Component> geselecteerdeComponenten) {
        super(geselecteerdeComponenten);
        setBackground(Color.white);

        setPreferredSize(new Dimension(0, 0));
        setLayout(new GridLayout(5, 2));
    }



    // Deze functie tekent alle componenten binnen de catelogus en zorgt ervoor dat rechtermuisknop menu werkt
    public void tekenVisualisatiePanel() {
        for(int i = 0; i < componenten.size(); i++) {

            // Het aanmaken van een panel zodat alle gegevens van een component bij elkaar blijft
            JPanel component = new JPanel();
            component.setLayout(new GridBagLayout());

            GridBagConstraints layout = new GridBagConstraints();

            // Zorgt ervoor dat de componenten in een vierkant blijven en niet strechten
            layout.fill = GridBagConstraints.NONE;

            // Zorgt ervoor dat elk label onder elkaar komt (gridy gaat steeds 1 omhoog)
            layout.gridx = 0;
            layout.gridy = 0;

            JLabel jlAfbeelding = new JLabel(componenten.get(i).getAfbeelding());
            jlAfbeelding.setName(String.valueOf(i)); // Set a unique ID for the label
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
            SimulatieFrame.visualisatiePanel.add(component);
            System.out.println(componenten.get(i).getNaam() + " toegevoegd");
            System.out.println();

//            // MouseListener aan afbeeldingslabel koppelen
//            jlAfbeelding.addMouseListener(new MouseAdapter() {
//                // Als linkermuisknop geklikt wordt, maak popup menuutje aan met toevoegen optie
//                public void mousePressed(MouseEvent e) {
//                    if (SwingUtilities.isLeftMouseButton(e)) {
//                        isHighlighted = true;
//                        // Verander border color wanneer de label gehighlight is
//                        jlAfbeelding.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
//                        jlAfbeelding.setOpaque(true);
//
//                        // Maak de popup menu aan
//                        JPopupMenu popupMenu = new JPopupMenu();
//                        JMenuItem toevoegen = new JMenuItem("Toevoegen");
//                        popupMenu.add(toevoegen);
//
//                        // Popup laten zien op de coordinaten van de cursor
//                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
//
//                        toevoegen.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent f) { // f moet nog voor andere dingen gebruikt worden
//                                JLabel clickedLabel = (JLabel) e.getComponent(); // Haal de geklikte label op
//                                String imageId = clickedLabel.getName(); // Haal de bijbehorende ID bij het label op
//                                int id = Integer.parseInt(imageId);
//
//                                Component voorbeeldComponent = componenten.get(id);
//                                Component component = new Component(voorbeeldComponent.getNaam(), voorbeeldComponent.getKosten(), voorbeeldComponent.getBeschikbaarheid(), voorbeeldComponent.getType());
//                                System.out.println(component.getNaam());
//
//                                VisualisatiePanel visualisatiePanel = new VisualisatiePanel(componenten);
//                                visualisatiePanel.voegComponentToe(component);
//                            }
//                        });
//                    }
//                }
//                public void mouseExited(MouseEvent g) { // CLEAR BORDER NA TOEVOEGEN EN DESELECT
//                    // Check of linkermuisknop geklikt is, dan niet de kleur aanpassen
//                    if ((g.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
//                        return;
//                    }
//
//                    // Als de linkermuisknop niet ingedrukt is, doe normale kleuren
//                    jlAfbeelding.setBorder(BorderFactory.createEmptyBorder());
//                    jlAfbeelding.setOpaque(false);
//                }
//            });
        }
    }

}