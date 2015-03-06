package cam.aim.service;


import cam.aim.domain.Aim;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
public class Menu {

    public static void main(String[] args) {
        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
//        Aim aim =new Aim();
//        aim.setAzimuth((long)300);
//        aim.setElevation((long)300);
//        aim.setInfo("testhome");
//        service.createAim(aim);

        List<Aim> list = service.getAllAim();
        for(Aim aim: list){
            System.out.println(aim.toString());
        }
//        System.out.println(list.toString());
//        Aim aim = service.getAim(2);
//        System.out.println(aim.toString());
    }
}
