package org.example.SensorRestApp.services;

import org.example.SensorRestApp.models.Measurement;
import org.example.SensorRestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> getAllMeasurements() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurement.setDateTime(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }
}
