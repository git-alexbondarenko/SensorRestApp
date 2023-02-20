package org.example.SensorRestApp.util;

import org.example.SensorRestApp.models.Sensor;
import org.example.SensorRestApp.services.SensorsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if (sensorsService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "sensor with this name already registered");
        }
    }
}
