import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Panel;

public class LowLevelMonitoringPanel extends Panel {
    private JTextField jtf;
    private JLabel searchLbl;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane jsp;
    public LowLevelMonitoringPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 150));
        setPreferredSize(new Dimension(900, 360));
        jtf = new JTextField(15);
        searchLbl = new JLabel("Search");
        String search = jtf.getText();
        MonitoringTable model = new MonitoringTable();
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        add(searchLbl);
        add(jtf);
        jsp = new JScrollPane(table);
        jsp.setBorder(null);
        jsp.setPreferredSize(table.getPreferredSize());
        add(jsp);
        table.setFillsViewportHeight(true);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search, 0));
        table.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Do nothing, disable selection
            }
        });
//        JPanel tablePanel = new JPanel(new BorderLayout());
//        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
//        frame.add(tablePanel, BorderLayout.CENTER); // or any other layout
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
    }
}
