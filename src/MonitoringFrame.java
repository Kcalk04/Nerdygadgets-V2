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
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane jsp;


    public MonitoringFrame(JFrame owner, boolean modal) {

        setTitle("Monitoring");
        jtf = new JTextField(15);
        searchLbl = new JLabel("Search");
        String search = jtf.getText();
        String[] columnNames = {"Name", "Activity", "Uptime", "Cpu usage", "Disk usage"};
        Object[][] rowData = {{"Raja", "Active" , 24 + "-" + 16.36},{"Vineet", "Down"},{"Archana", "Python"},{"Krishna", "Scala"},{"Adithya", "AWS"},{"Jai", ".Net"}};
        model = new DefaultTableModel(rowData, columnNames);
        model.setRowCount(10); // set the number of rows to 10
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        jsp = new JScrollPane(table);
        TableCellEditor doubleEditor = new DefaultCellEditor(new JFormattedTextField(NumberFormat.getNumberInstance()));
        TableCellEditor timestampEditor = new DefaultCellEditor(new JFormattedTextField(new SimpleDateFormat("dd-HH:mm:ss")));
        TableCellEditor percentageEditor = new DefaultCellEditor(new JFormattedTextField(NumberFormat.getPercentInstance()));
        table.getColumnModel().getColumn(3).setCellEditor(timestampEditor);
        jsp.setBorder(null);
        jsp.setPreferredSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search, 0));
        table.setRowSorter(sorter);
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
