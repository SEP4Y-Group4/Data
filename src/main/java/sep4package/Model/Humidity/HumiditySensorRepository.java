package sep4package.Model.Humidity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HumiditySensorRepository extends JpaRepository<HumidityMeasurement, java.lang.Long> {
}
