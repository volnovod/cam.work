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
 * сервіс для взаємодії користувача і бази даних
 */
@Service("service")
public class AimServiceImpl implements AimService {

    @Autowired
    private AimDao aimDao;

    public AimServiceImpl() {
    }

    /**
     * створення цілі в базі
     * @param aim
     */
    @Override
    public void createAim(Aim aim) {
        aimDao.create(aim);
    }

    /**
     * отримати ціль із бази по id
     * @param id
     * @return
     */
    @Override
    public Aim getAim(Integer id) {
        return aimDao.read(id);
    }

    /**
     * видаляє ціль із бази
     * @param aim
     * @return
     */
    @Override
    public boolean deleteAim(Aim aim) {
        return aimDao.delete(aim);
    }

    /**
     * оновлює в базі параметри цілі
     * @param aim
     * @return
     */
    @Override
    public boolean updateAim(Aim aim) {
        return aimDao.update(aim);
    }

    /**
     * отримує список всіх цілей із бази
     * @return
     */
    @Override
    public List<Aim> getAllAim() {
        return aimDao.findAll();
    }
}
