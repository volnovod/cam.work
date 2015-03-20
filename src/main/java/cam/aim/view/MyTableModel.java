package cam.aim.view;

import cam.aim.domain.Aim;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.*;

/**
 * Created by Виктор on 12.03.2015.
 */
public class MyTableModel extends AbstractTableModel{

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Aim> beans;

    public List<Aim> getBeans() {
        return beans;
    }

    public void setBeans(List<Aim> beans) {
        this.beans = beans;
        Collections.sort(this.beans, new Comparator<Aim>() {
            @Override
            public int compare(Aim o1, Aim o2) {
                if(o1.getId() > o2.getId()){
                    return 1;
                }
                if(o1.getId() < o2.getId()){
                    return -1;
                }
                return 0;
            }
        });
    }





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
                return "№";
            case 1:
                return "Широта";
            case 2:
                return "Довгота";
            case 3:
                return "Інфо";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return Integer.class;
            case 1:
                return Double.class;
            case 2:
                return Double.class;
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
                return aim.getLatitude();
            case 2:
                return aim.getLongtitude();
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
