package cam.aim.service;

import cam.aim.dao.AimDao;
import cam.aim.dao.AimDaoImpl;
import cam.aim.domain.Aim;
import cam.aim.view.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by victor on 17.02.15.
 */
public class Menu {

    public static void main(String[] args) {
//        AimService service = new AimServiceImpl();
        AimDao aimDao = new AimDaoImpl();
        Aim aim =new Aim();
//        aim.setId(1);
        aim.setAzimuth((long)250);
        aim.setElevation((long)260);
        aim.setInfo("test2");
        aimDao.create(aim);
//        service.createAim(aim);
        System.out.println(aim.toString());
//        SessionFactory factory = HibernateUtil.getSessionFactory();
//        Session session = factory.openSession();
//        try{
//            session.beginTransaction();
//            session.save(aim);
//            session.getTransaction().commit();
//        } catch (HibernateException e){
//            e.printStackTrace();
//            session.getTransaction().rollback();
//        } finally {
//            session.close();
//            factory.close();
//        }

    }
}
