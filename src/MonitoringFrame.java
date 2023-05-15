import jdk.jfr.Percentage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    public MonitoringFrame(JFrame owner, boolean modal) {

        setTitle("Monitoring");
        jtf = new JTextField(15);
        searchLbl = new JLabel("Search");
        String search = jtf.getText();
        MonitoringTable model = new MonitoringTable();
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        jsp = new JScrollPane(table);
        jsp.setBorder(null);
        jsp.setPreferredSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search, 0));
        table.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Do nothing, disable selection
            }
        });
        add(searchLbl);
        add(jtf);
        add(jsp);
        jtf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(jtf.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(jtf.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(jtf.getText());
            }
            public void search(String str) {
                if (str.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(str));
                }
            }
        });
            setSize(new Dimension(900,720));
            setLayout(new GridLayout(0,1));

            // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
            setResizable(false);

            GridBagConstraints layout = new GridBagConstraints();

            setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
