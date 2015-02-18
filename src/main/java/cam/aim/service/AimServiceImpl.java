package cam.aim.service;

import cam.aim.dao.AimDaoImpl;
import cam.aim.domain.Aim;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
@Repository
@Transactional
public class AimServiceImpl implements AimService {

//    @Autowired
    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    AimDaoImpl aimDao = (AimDaoImpl)context.getBean("aimdao");

    @Override
    public void createAim(Aim aim) {
        aimDao.create(aim);
    }

    @Override
    @Transactional(readOnly = true)
    public Aim getAim(Integer id) {
        return aimDao.read(id);
    }

    @Override
    public boolean deleteAim(Aim aim) {
        return aimDao.delete(aim);
    }

    @Override
    public boolean updateAim(Aim aim) {
        return aimDao.update(aim);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aim> getAllAim() {
        return aimDao.findAll();
    }
}
