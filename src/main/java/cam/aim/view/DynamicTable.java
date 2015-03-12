package cam.aim.view;

import cam.aim.domain.Aim;
import cam.aim.service.AimService;
import cam.aim.service.AimServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Виктор on 12.03.2015.
 */
public class DynamicTable extends JFrame {

    public DynamicTable() throws HeadlessException {
        super("Tect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
        List<Aim> list = service.getAllAim();

        MyTableModel model = new MyTableModel();
        model.setBeans(list);
        JTable table = new JTable(model);
        getContentPane().add(new JScrollPane(table));

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DynamicTable();
            }
        });
    }
}
