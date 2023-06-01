import javax.swing.table.AbstractTableModel;

public class MonitoringTable extends AbstractTableModel {



    String[] columnNames = {"Name", "Activity", "Uptime", "Cpu usage", "Disk usage"};
    Object[][] rowData = {
            {"Raja", "Active" , 24 + "-" + 16.36, "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", ""},
            {"Raja", "Active" , 24 + "-" + 16.36, "", ""}
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
        return 5;
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
