package org.example.SensorRestApp.controllers;

import jakarta.validation.Valid;
import org.example.SensorRestApp.dto.SensorDTO;
import org.example.SensorRestApp.models.Sensor;
import org.example.SensorRestApp.services.SensorsService;
import org.example.SensorRestApp.util.MeasurementErrorResponse;
import org.example.SensorRestApp.util.MeasurementException;
import org.example.SensorRestApp.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.SensorRestApp.util.ErrorMessage.errorMessage;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper mapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper mapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.mapper = mapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> index() {
        return sensorsService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor newSensor = convertToSensor(sensorDTO);
        sensorValidator.validate(newSensor, bindingResult);
        if (bindingResult.hasErrors()) {
            errorMessage(bindingResult);
        }
        sensorsService.registerSensor(newSensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return mapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return mapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
