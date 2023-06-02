import jdk.jfr.Category;

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


        // Create a DefaultTableModel with the data and column names
        BackTrackingAlg backTrackingAlg = new BackTrackingAlg();
        int[][] aantallen = backTrackingAlg.getAantal();

        // Create table column names
        String[] columnNames = {"Machine", "Beschikbaarheid", "Prijs", "Aantal"};

        // Create table data
        Object[][] data = new Object[SimulatieFrame.catalogusPanel.catalogusComponenten.size()][columnNames.length];

        for (int i = 0; i < aantallen.length-1; i++) {
            data[i][0] = SimulatieFrame.catalogusPanel.catalogusComponenten.get(i).getNaam();
            data[i][1] = SimulatieFrame.catalogusPanel.catalogusComponenten.get(i).getBeschikbaarheid();
            data[i][2] = SimulatieFrame.catalogusPanel.catalogusComponenten.get(i).getKosten() * aantallen[i][0];
            data[i][3] = aantallen[i][0];
        }

//        for (int i = 0; i < data.length; i++) {
//            data[i][5] = backTrackingAlg[i]
//        }

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
