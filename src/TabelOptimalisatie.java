import jdk.jfr.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class TabelOptimalisatie  extends JDialog implements ActionListener{
    public JTable optDisplay;
    public TabelOptimalisatie(JFrame parent, String beschikbaarheidString){
        super(parent, "Nieuw component toevoegen", true);
        optDisplay = new JTable();

//        double beschikbaarheid = Double.parseDouble(beschikbaarheidString);

//        ServerInfrastructure serverInfrastructure = new ServerInfrastructure(beschikbaarheid, 100000);
//        System.out.println(serverInfrastructure.findCheapestInfrastructure(0, 0, 0, 0).size());

        ArrayList<Component> selectedServers = SimulatieFrame.catalogusPanel.catalogusComponenten;

        // Create a DefaultTableModel with the data and column names
        BackTrackingAlg backTrackingAlg = new BackTrackingAlg();
        int[][] aantallen = backTrackingAlg.getAantal(selectedServers);

        // Create table column names
        String[] columnNames = {"Machine", "Beschikbaarheid", "Prijs", "Aantal"};

        // Create table data
        Object[][] data = new Object[selectedServers.size()][columnNames.length];
        System.out.println(selectedServers.size());
        System.out.println(aantallen.length);

        for (int i = 0; i < SimulatieFrame.catalogusPanel.catalogusComponenten.size()-1; i++) {
            data[i][0] = selectedServers.get(i).getNaam();
            data[i][1] = selectedServers.get(i).getBeschikbaarheid();
            data[i][2] = selectedServers.get(i).getKosten() * aantallen[i][0];
            data[i][3] = aantallen[i][0];
        }

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
