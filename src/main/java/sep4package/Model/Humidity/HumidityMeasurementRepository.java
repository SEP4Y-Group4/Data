package sep4package.Model.Humidity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HumidityMeasurementRepository extends JpaRepository<HumidityMeasurement, java.lang.Long> {
}
