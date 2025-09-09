package com.example.doorlock;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api")  // All REST endpoints under /api
@CrossOrigin // allow frontend or ESP32 from other origins
public class HomeControl {

    private final AtomicBoolean ledOn = new AtomicBoolean(false);
    private final AtomicReference<Double> distance = new AtomicReference<>(0.0);
    private final AtomicBoolean motionDetected = new AtomicBoolean(false);

    // --- Ultrasonic Distance ---
    @PostMapping("/sensor")
    public String updateDistance(@RequestBody SensorData data) {
        distance.set(data.getDistance());
        return "Distance updated: " + data.getDistance();
    }

    @GetMapping("/distance")
    public double getDistance() {
        return distance.get();
    }

    // --- LED Lock Control ---
    @GetMapping("/led")
    public String getLedStatus() {
        return ledOn.get() ? "ON" : "OFF";
    }

    @PostMapping("/led/on")
    public String turnOn() {
        ledOn.set(true);
        return "LED is ON";
    }

    @PostMapping("/led/off")
    public String turnOff() {
        ledOn.set(false);
        return "LED is OFF";
    }

    // --- Motion Sensor ---
    @PostMapping("/motion")
    public String updateMotion(@RequestBody MotionData data) {
        motionDetected.set(data.isMotion());
        System.out.println("Motion updated: " + data.isMotion());
        return "Motion updated: " + data.isMotion();
    }

    @GetMapping("/motion")
    public boolean getMotion() {
        return motionDetected.get();
    }
}

// Helper class for Ultrasonic Sensor JSON
class SensorData {
    private double distance;
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
}

// Helper class for PIR Motion JSON
class MotionData {
    private boolean motion;
    public boolean isMotion() { return motion; }
    public void setMotion(boolean motion) { this.motion = motion; }
}
