import jdk.jfr.Percentage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class MonitoringFrame extends JFrame implements ActionListener {
    private JTextField jtf;
    private JLabel searchLbl;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane jsp;
    private static LowLevelMonitoringPanel lowLevelMonitoringPanel;

    public MonitoringFrame(JFrame owner, boolean modal) {
        setTitle("Monitoring");
        setSize(new Dimension(900,720));

        lowLevelMonitoringPanel = new LowLevelMonitoringPanel(this);
        add(lowLevelMonitoringPanel);

        // Zorgt ervoor dat het frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        GridBagConstraints layout = new GridBagConstraints();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
