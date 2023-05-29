import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Panel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LowLevelMonitoringPanel extends Panel {
    private JLabel searchLbl;
    private JTable table;
    private TableRowSorter sorter;
    private JScrollPane jsp;
    public LowLevelMonitoringPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(900, 720));
        setBackground(Color.GREEN);


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
            public void search(String searchValue) {
                if (searchValue.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    RowFilter<Object, Object> filter = RowFilter.regexFilter(searchValue);
                    var regex = Pattern.compile(searchValue, Pattern.CASE_INSENSITIVE);
                    var filter2 = new RegexFilter<Object, Object>(regex);
                    sorter.setRowFilter(filter2);
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
        table.setRowHeight(50);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jsp.setPreferredSize(table.getPreferredSize());
        var cScrollPane = new GridBagConstraints();
        cScrollPane.ipady = 22;
        cScrollPane.fill = GridBagConstraints.HORIZONTAL;
        cScrollPane.insets = new Insets(10, 20, 0, 20);
        cScrollPane.gridx = 0;
        cScrollPane.gridy = 1;
        cScrollPane.gridwidth = 5;
        add(jsp, cScrollPane);
        table.setFillsViewportHeight(true);
//        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search, 0));
        table.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Do nothing, disable selection
            }
        });
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
    private abstract static class GeneralFilter<M, I> extends RowFilter<M, I> {
        private int[] columns;

        private static void checkIndices(int[] columns) {
            for (int i = columns.length - 1; i >= 0; i--) {
                if (columns[i] < 0) {
                    throw new IllegalArgumentException("Index must be >= 0");
                }
            }
        }
        GeneralFilter(int[] columns) {
            checkIndices(columns);
            this.columns = columns;
        }

        @Override
        public boolean include(Entry<? extends M, ? extends I> value){
            int count = value.getValueCount();
            if (columns.length > 0) {
                for (int i = columns.length - 1; i >= 0; i--) {
                    int index = columns[i];
                    if (index < count) {
                        if (include(value, index)) {
                            return true;
                        }
                    }
                }
            } else {
                while (--count >= 0) {
                    if (include(value, count)) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected abstract boolean include(
                Entry<? extends M, ? extends I> value, int index);
    }
    private static class RegexFilter<M, I> extends GeneralFilter<M, I> {
        private Matcher matcher;

        RegexFilter(Pattern regex, int... columns) {
            super(columns);
            if (regex == null) {
                throw new IllegalArgumentException("Pattern must be non-null");
            }
            matcher = regex.matcher("");
        }

        @Override
        protected boolean include(
                Entry<? extends M, ? extends I> value, int index) {
            matcher.reset(value.getStringValue(index));
            return matcher.find();
        }
    }
}
