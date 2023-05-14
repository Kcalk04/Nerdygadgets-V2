import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 10 pixels of horizontal and vertical spacing between components

        JButton verwijderAlles = new JButton("Verwijder alles");
        add(verwijderAlles);

        JLabel prijs = new JLabel("Prijs:");
        add(prijs);

        JLabel beschikbaarheid = new JLabel("Beschikbaarheid:");
        add(beschikbaarheid);


    }
}
