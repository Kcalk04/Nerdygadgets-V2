import javax.swing.*;
import java.awt.*;

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

    public ImageIcon getAfbeelding() {
        if(type == ComponentType.PFSENSE) {
            return new ImageIcon("src/img/pfsense.jpg");
        } else if(type == ComponentType.WEBSERVER) {
            return new ImageIcon("src/img/webserver.jpg");
        } else if(type == ComponentType.DATABASESERVER) {
            return new ImageIcon("src/img/databaseserver.jpg");
        } else {
            return null;
        }
    }
}
