package cam.aim.service;

import cam.aim.dao.AimDao;
import cam.aim.dao.AimDaoImpl;
import cam.aim.domain.Aim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
@Service("service")
public class AimServiceImpl implements AimService {

    @Autowired
    private AimDao aimDao;

    public AimServiceImpl() {
    }

    @Override
    public void createAim(Aim aim) {
        aimDao.create(aim);
    }

    @Override
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
    public List<Aim> getAllAim() {
        return aimDao.findAll();
    }
}
