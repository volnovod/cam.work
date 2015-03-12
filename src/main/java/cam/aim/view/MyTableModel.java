package cam.aim.view;

import cam.aim.domain.Aim;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Виктор on 12.03.2015.
 */
public class MyTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    public List<Aim> getBeans() {
        return beans;
    }

    public void setBeans(List<Aim> beans) {
        this.beans = beans;
    }

    private List<Aim> beans;

    @Override
    public int getRowCount() {
        return this.beans.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:
                return "Id";
            case 1:
                return "Elevation";
            case 2:
                return "Azimuth";
            case 3:
                return "Info";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return Integer.class;
            case 1:
                return Long.class;
            case 2:
                return Long.class;
            case 3:
                return String.class;
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aim aim = beans.get(rowIndex);
        switch (columnIndex){
            case 0:
                return aim.getId();
            case 1:
                return aim.getElevation();
            case 2:
                return aim.getAzimuth();
            case 3:
                return aim.getInfo();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        this.listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        this.listeners.remove(l);
    }
}
