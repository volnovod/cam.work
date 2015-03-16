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

    @Column(name = "LATITUDE", nullable = false)
    private double latitude;

    @Column(name = "LONGTITUDE", nullable = false)
    private double longtitude;

    @Column(name = "OTHERINFO", nullable = true)
    private String info;

    @Override
    public String toString() {
        return "Aim{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", info='" + info + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
