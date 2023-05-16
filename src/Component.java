import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Component {
    private String naam;
    private double kosten;
    private double beschikbaarheid;
    private ComponentType type;

    public Component(String naam, double kosten, double beschikbaarheid, ComponentType type) {
        this.naam = naam;
        this.kosten = kosten;
        this.beschikbaarheid = beschikbaarheid;
        this.type = type;
    }

    public String getNaam() {
        return naam;
    }

    public double getKosten() {
        return kosten;
    }

    public double getBeschikbaarheid() {
        return beschikbaarheid;
    }

    public ComponentType getType() {
        return type;
    }

    // Geeft de afbeelding terug op basis van het type component (PFSENSE, WEBSERVER, DATABASE)
    public ImageIcon getAfbeelding() {

        // Slaat het root pad van het project op
        String rootPath = new File("").getAbsolutePath();

        if(type == ComponentType.PFSENSE) {
            return new ImageIcon(rootPath + "/src/img/pfsense.jpg");
        } else if(type == ComponentType.WEBSERVER) {
            return new ImageIcon(rootPath + "/src/img/webserver.jpg");
        } else if(type == ComponentType.DATABASESERVER) {
            return new ImageIcon(rootPath + "/src/img/databaseserver.jpg");
        } else {
            return new ImageIcon(rootPath + "/src/img/placeholder.jpg");
        }
    }

    public static ArrayList<Component> sort(ArrayList<Component> componenten) {

        ArrayList<Component> c = new ArrayList<>();

        for (int i = 0; i < ComponentType.values().length; i++) {
            for (Component component : componenten) {
                if (!c.contains(component) && component.getType().ordinal() <= i) {
                    c.add(component);
                }
            }
        }

        return c;
    }
}
