package org.example.SensorRestApp.util;

import org.example.SensorRestApp.models.Measurement;
import org.example.SensorRestApp.services.SensorsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (measurement.getSensor() == null) {
            return;
        }
        if (sensorsService.findByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "no sensor with this name");
        }
    }
}
