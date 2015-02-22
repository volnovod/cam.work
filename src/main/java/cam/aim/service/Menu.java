package cam.aim.service;


import cam.aim.domain.Aim;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by victor on 17.02.15.
 */
public class Menu {

    public static void main(String[] args) {
        AimService service = new ClassPathXmlApplicationContext("transactionalContext.xml").getBean("service", AimServiceImpl.class);
        Aim aim =new Aim();
        aim.setAzimuth((long)300);
        aim.setElevation((long)300);
        aim.setInfo("testhome");
        service.createAim(aim);


    }
}
