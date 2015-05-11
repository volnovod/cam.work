package cam.aim.view;

import cam.aim.domain.Aim;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
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
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex == 0){
            return "№";
        }
        if (columnIndex == 1 ){
            return "Ш";
        }
        if (columnIndex == 2){
            return "Д";
        }
        if( columnIndex == 3){
            return "Ш-42";
        }
        if (columnIndex == 4){
            return "Д-42";
        }
        if( columnIndex == 5){
            return "Path";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0){
            return Integer.class;
        }
        if (columnIndex == 1 ){
            return Double.class;
        }
        if (columnIndex == 2){
            return Double.class;
        }
        if( columnIndex == 3){
            return Double.class;
        }
        if (columnIndex == 4){
            return Double.class;
        }
        if( columnIndex == 5){
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
        if(columnIndex == 0){
            return aim.getId();
        }
        if (columnIndex == 1 ){
            return aim.getLatitude();
        }
        if (columnIndex == 2){
            return aim.getLongitude();
        }
        if( columnIndex == 3){
            return aim.getLatitudeck42();
        }
        if (columnIndex == 4){
            return aim.getLongitudeck42();
        }
        if( columnIndex == 5){
            return aim.getPath();
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
