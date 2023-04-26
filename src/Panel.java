import javax.swing.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    public ArrayList<Component> componenten;

    public Panel(ArrayList<Component> componenten) {
        this.componenten = componenten;
    }
}
