package com.example.doorlock;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api")  // all endpoints start with /api
public class HomeControl {

    private final AtomicBoolean ledOn = new AtomicBoolean(false);
    private final AtomicReference<Double> distance = new AtomicReference<>(0.0);
    private final AtomicBoolean motionDetected = new AtomicBoolean(false);

    // ESP32 sends ultrasonic + motion
    @PostMapping("/sensor")
    public String updateSensor(@RequestBody SensorData data) {
        distance.set(data.getDistance());
        motionDetected.set(data.isMotion());
        return "Sensor updated";
    }

    // GET distance
    @GetMapping("/distance")
    public double getDistance() {
        return distance.get();
    }

    // GET LED status
    @GetMapping("/led")
    public String getLedStatus() {
        return ledOn.get() ? "ON" : "OFF";
    }

    // Turn LED ON
    @PostMapping("/led/on")
    public String turnOn() {
        ledOn.set(true);
        return "LED ON";
    }

    // Turn LED OFF
    @PostMapping("/led/off")
    public String turnOff() {
        ledOn.set(false);
        return "LED OFF";
    }

    // Motion status
    @GetMapping("/motion")
    public boolean getMotion() {
        return motionDetected.get();
    }
}

// JSON mapping class
class SensorData {
    private double distance;
    private boolean motion;

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public boolean isMotion() { return motion; }
    public void setMotion(boolean motion) { this.motion = motion; }
}
