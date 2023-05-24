import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Panel;

public class LowLevelMonitoringPanel extends Panel {
    private JLabel searchLbl;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane jsp;
    public LowLevelMonitoringPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(900, 360));


        JTextField searchField = setupSearchbox();
        setupTable();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchField.getText());
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

    private void setupTable() {
        MonitoringTable model = new MonitoringTable();
        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        jsp = new JScrollPane(table);
        jsp.setBorder(null);
        jsp.setPreferredSize(table.getPreferredSize());
        var cScrollPane = new GridBagConstraints();
        cScrollPane.ipady = 50;
        cScrollPane.fill = GridBagConstraints.HORIZONTAL;
        cScrollPane.insets = new Insets(10, 20, 0, 20);
        cScrollPane.gridx = 0;
        cScrollPane.gridy = 1;
        cScrollPane.gridwidth = 3;
        add(jsp, cScrollPane);
        table.setFillsViewportHeight(true);
//        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search, 0));
        table.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Do nothing, disable selection
            }
        });
//        JPanel tablePanel = new JPanel(new BorderLayout());
//        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
//        frame.add(tablePanel, BorderLayout.CENTER); // or any other layout

    }

    private JTextField setupSearchbox() {
        searchLbl = new JLabel("Search");
        var cLabel = createConstraint(0);
        cLabel.insets = new Insets(0, 20, 0, 10);
        add(searchLbl, cLabel);

        JTextField searchField = new JTextField(15);
        String search = searchField.getText();
        var cSearchField = createConstraint(1);
        add(searchField, cSearchField);
        return searchField;
    }

    private static GridBagConstraints createConstraint(int gridX) {
        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = gridX;
        c.gridy = 0;
        return c;
    }
}
