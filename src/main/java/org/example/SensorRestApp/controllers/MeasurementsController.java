package org.example.SensorRestApp.controllers;

import jakarta.validation.Valid;
import org.example.SensorRestApp.dto.MeasurementDTO;
import org.example.SensorRestApp.models.Measurement;
import org.example.SensorRestApp.services.MeasurementsService;
import org.example.SensorRestApp.util.MeasurementErrorResponse;
import org.example.SensorRestApp.util.MeasurementException;
import org.example.SensorRestApp.util.MeasurementValidator;
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
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper mapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService,
                                  MeasurementValidator measurementValidator, ModelMapper mapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.getAllMeasurements().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement newMeasurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(newMeasurement, bindingResult);
        if (bindingResult.hasErrors()) {
            errorMessage(bindingResult);
        }
        measurementsService.addMeasurement(newMeasurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementsService.getAllMeasurements().stream().filter(Measurement::isRaining).count();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return mapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return mapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
