package org.example.SensorRestApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name="measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double value;

    @Column(name = "israining")
    @NotNull
    private boolean isRaining;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @NotNull
    private Sensor sensor;

    @Column(name = "measurement_date_time")
    @NotNull
    private LocalDateTime measurementDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime dateTime) {
        this.measurementDateTime = dateTime;
    }
}
