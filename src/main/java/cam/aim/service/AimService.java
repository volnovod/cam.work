package cam.aim.service;

import cam.aim.domain.Aim;

import java.util.List;

/**
 * Created by victor on 17.02.15.
 */
public interface AimService {

    Long createAim(Aim aim);
    Aim getAim(Integer id);
    boolean deleteAim(Aim aim);
    boolean updateAim(Aim aim);
    List<Aim> getAllAim();
}
