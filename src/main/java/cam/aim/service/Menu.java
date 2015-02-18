package cam.aim.service;

import cam.aim.dao.AimDao;
import cam.aim.dao.AimDaoImpl;
import cam.aim.domain.Aim;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by victor on 17.02.15.
 */
public class Menu {

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Aim aim =new Aim();
        aim.setId(1);
        aim.setAzimuth((long)250);
        aim.setElevation((long)200);
        aim.setInfo("test");
        AimService service = new AimServiceImpl();
        service.createAim(aim);
        System.out.println(aim.toString());

    }
}
