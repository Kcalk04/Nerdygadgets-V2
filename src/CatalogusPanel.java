import java.awt.*;
import java.util.ArrayList;

public class CatalogusPanel extends Panel {

    public CatalogusPanel(ArrayList<Component> componenten) {
        super(componenten);

        setPreferredSize(new Dimension(200,720));
        setBackground(Color.lightGray);
    }
}
