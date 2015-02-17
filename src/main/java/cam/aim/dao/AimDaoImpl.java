package cam.aim.dao;

import cam.aim.domain.Aim;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
@Repository("AimDao")
@Transactional
public class AimDaoImpl implements AimDao {

    @Autowired
    SessionFactory factory;

    @Override
    public Integer create(Aim aim) {
        return (Integer)factory.getCurrentSession().save(aim);
    }

    @Override
    @Transactional(readOnly = true)
    public Aim read(Integer id) {
        return (Aim)factory.getCurrentSession().get(Aim.class, id);
    }

    @Override
    public boolean update(Aim aim) {
        factory.getCurrentSession().update("AIM", aim);
        return true;
    }

    @Override
    public boolean delete(Aim aim) {
        factory.getCurrentSession().delete("AIM", aim);
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aim> findAll() {
        List<Aim> aimList= new ArrayList<>();
        return factory.getCurrentSession().createCriteria("AIM").list();
    }
}
