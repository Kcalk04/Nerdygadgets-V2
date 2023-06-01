import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TabelOptimalisatie  extends JDialog implements ActionListener{
    public JTable optDisplay;
    public TabelOptimalisatie(JFrame parent){
        super(parent, "Nieuw component toevoegen", true);
        optDisplay = new JTable();

        // Create table data
        Object[][] data = {
                {"PFsense"},
                {"pfsense", 99.998 + "%" ,"€" + 4000.00},
                {},
                {"Web"},
                {"HAL9001W", 80.0 + "%" ,"€" + 2200.00},
                {"HAL9002W", 90.0 + "%" ,"€" + 3200.00},
                {"HAL9003W", 95.0 + "%" ,"€" + 5100.00},
                {},
                {"Databases"},
                {"HAL9001DB", 90 + "%" ,"€" + 5100.00},
                {"HAL9002DB", 95 + "%" ,"€" + 7700.00},
                {"HAL9003DB", 98 + "%" ,"€" + 12200.00},
        };

        // Create table column names
        String[] columnNames = {"Machine", "Beschikbaarheid", "Prijs", "Aantal"};

        // Create a DefaultTableModel with the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create the JTable with the DefaultTableModel
        JTable table = new JTable(model);

        // Create a JScrollPane to contain the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JPanel and add the JScrollPane to it
        JPanel panel = new JPanel();
        panel.add(scrollPane);

        // Add the panel to the frame
        add(panel);
        setSize(new Dimension(600,400));


        // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
