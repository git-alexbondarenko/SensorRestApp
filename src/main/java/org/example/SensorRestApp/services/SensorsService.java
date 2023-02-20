package org.example.SensorRestApp.services;

import org.example.SensorRestApp.models.Sensor;
import org.example.SensorRestApp.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    @Transactional
    public void registerSensor(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
