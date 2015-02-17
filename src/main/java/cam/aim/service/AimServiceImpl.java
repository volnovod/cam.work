package cam.aim.service;

import cam.aim.dao.AimDao;
import cam.aim.dao.AimDaoImpl;
import cam.aim.domain.Aim;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
public class AimServiceImpl implements AimService {

    @Autowired
    AimDaoImpl aimDao;
    @Override
    public Long createAim(Aim aim) {
        return null;
    }

    @Override
    public Aim getAim(Integer id) {
        return null;
    }

    @Override
    public boolean deleteAim(Aim aim) {
        return false;
    }

    @Override
    public boolean updateAim(Aim aim) {
        return false;
    }

    @Override
    public List<Aim> getAllAim() {
        return null;
    }
}
