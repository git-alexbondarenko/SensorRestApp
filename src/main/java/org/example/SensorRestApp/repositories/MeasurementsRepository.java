package org.example.SensorRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.example.SensorRestApp.models.Measurement;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    @Query("SELECT COUNT(DISTINCT DATE(m.measurementDateTime)) FROM Measurement m WHERE m.isRaining = true")
    Long countDistinctMeasurementDatesWhereIsRaining();
}
