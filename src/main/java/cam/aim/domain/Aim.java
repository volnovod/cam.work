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

    @Column(name = "LONGITUDE", nullable = false)
    private double longitude;

    @Column(name = "PATH", nullable = true)
    private String path;

    @Column ( name = "LATITUDECK42", nullable = false)
    private double latitudeck42;

    @Column ( name = "LONGITUDECK42", nullable = false)
    private double longitudeck42;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getLatitudeck42() {
        return latitudeck42;
    }

    public void setLatitudeck42(double latitudeck42) {
        this.latitudeck42 = latitudeck42;
    }

    public double getLongitudeck42() {
        return longitudeck42;
    }

    public void setLongitudeck42(double longitudeck42) {
        this.longitudeck42 = longitudeck42;
    }

    @Override
    public String toString() {
        return " Широта-" + latitude + "\n" + "Довгота-" + longitude;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }




}
