import javax.swing.table.AbstractTableModel;

public class MonitoringTable extends AbstractTableModel {

    String[] columnNames = {"IconType", "Name", "Activity", "Uptime", "Cpu usage", "Disk usage"};
    Object[][] rowData = {
            {"Test", "Active" , 24 + "-" + 16.36, "", "", ""},
            {"abc", "Active" , 24 + "-" + 16.36, "", "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", "", ""}
    };

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 2){

        }
        return getValueAt(0,columnIndex).getClass();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }
}
