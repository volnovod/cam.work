package cam.aim.view;

import cam.aim.domain.Aim;
import cam.aim.service.AimService;
import cam.aim.service.AimServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by victor on 18.02.15.
 */
public class InfoTable extends JPanel {
    private boolean DEBUG = false;

    public InfoTable() {
        super(new GridLayout(1, 0));

        String[] columnNames = {"№",
                "Місце", "Азимут", "Info"};


        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
        List<Aim> list = service.getAllAim();
        Object[][] data = new String[list.size()][columnNames.length];
        Aim aim = new Aim();
        for(int i = 0; i < list.size(); i++){
            aim = list.get(i);
            System.out.println(list.size());
            data[i][0]=aim.getId().toString();
            data[i][1]=aim.getElevation().toString();
            data[i][2]=aim.getAzimuth().toString();
            data[i][3]=aim.getInfo();

        }



        final JTable table = new JTable(data, columnNames);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos=(int)dimension.getWidth()/8;
        int ypos=(int)dimension.getHeight()/2;

        table.setPreferredScrollableViewportSize(new Dimension(xpos, ypos));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }



}