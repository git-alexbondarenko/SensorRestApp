package org.example.SensorRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.SensorRestApp.models.Measurement;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
