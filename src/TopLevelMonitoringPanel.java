import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TopLevelMonitoringPanel extends Panel {
    public TopLevelMonitoringPanel() {
    setPreferredSize(new Dimension(900, 360));
//        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
//        table.setRowSorter(sorter);
        RowFilter<TableModel, Object> filter = new RowFilter<TableModel, Object>() {
            public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                return true;
            }
        };
        JCheckBox filterCheckBox = new JCheckBox("Filter");
//        filterCheckBox.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    sorter.setRowFilter(filter);
//                } else {
//                    sorter.setRowFilter(null);
//                }
//            }
//        });

    }
}
