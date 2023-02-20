package org.example.SensorRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.SensorRestApp.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
