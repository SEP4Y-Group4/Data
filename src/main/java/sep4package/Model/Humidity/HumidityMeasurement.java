package sep4package.Model.Humidity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import sep4package.Model.Sensors;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Entity(name = "Humidity")
@Table(name = "humidity")
public class HumidityMeasurement
{
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
        name = "sequence-generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "humidity_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
        }
    )
    private java.lang.Long HumidityId;
    @Column
    private double Humidity;

    @Column
    private Timestamp Time;

    @OneToOne(mappedBy = "humidity")
    private Sensors sensors;


    public HumidityMeasurement(double Humidity,Timestamp timestamp)
    {
        this.Humidity = Humidity;
        this.Time = timestamp;
    }

    public HumidityMeasurement(Long HumidityId,double Humidity)
    {
        this.HumidityId = HumidityId;
        this.Humidity = Humidity;
    }

    public HumidityMeasurement() {
    }

    public java.lang.Long getHumidityId()
    {
        return HumidityId;
    }

    public void setHumidityId(java.lang.Long humidityId)
    {
        HumidityId = humidityId;
    }

    public double getHumidity()
    {
        return Humidity;
    }

    public void setHumidity(double humidity)
    {
        Humidity = humidity;
    }

    public Timestamp getTime()
    {
        return Time;
    }

    public void setTime(Timestamp time)
    {
        Time = time;
    }

}
