import java.awt.*;
import java.util.ArrayList;

public class OverviewPanel extends Panel {
    public OverviewPanel(ArrayList<Component> componenten) {
        super(componenten);
        setBackground(Color.lightGray);
//        setPreferredSize(new Dimension(260, 720));
        setLayout(new GridLayout(5,5 ));
    }
}
