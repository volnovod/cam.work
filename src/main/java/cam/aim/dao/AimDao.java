package cam.aim.dao;

import cam.aim.domain.Aim;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
@Repository
@Transactional
public interface AimDao {

    Integer create(Aim aim);
    Aim read(Integer id);
    boolean update(Aim aim);
    boolean delete(Aim aim);
    List<Aim> findAll();
}
