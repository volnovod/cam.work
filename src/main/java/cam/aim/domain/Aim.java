package cam.aim.domain;

import javax.persistence.*;

/**
 * Created by victor on 17.02.15.
 */
@Entity
@Table(name="AIM")
public class Aim {

    @Id
    @SequenceGenerator(name="sequence", sequenceName = "AIM_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Integer id;

    @Column(name = "ELEVATION", nullable = false)
    private Long elevation;

    @Column(name = "AZIMUTH", nullable = false)
    private Long azimuth;

    @Column(name = "OTHERINFO", nullable = true)
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getElevation() {
        return elevation;
    }

    public void setElevation(Long elevation) {
        this.elevation = elevation;
    }

    public Long getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Long azimuth) {
        this.azimuth = azimuth;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Aim{" +
                "id=" + id +
                ", elevation=" + elevation +
                ", azimuth=" + azimuth +
                ", info='" + info + '\'' +
                '}';
    }
}
