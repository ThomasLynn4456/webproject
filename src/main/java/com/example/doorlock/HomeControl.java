package com.example.doorlock;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api")  // All REST endpoints under /api
public class HomeControl {

    private final AtomicBoolean ledOn = new AtomicBoolean(false);
    private final AtomicReference<Double> distance = new AtomicReference<>(0.0);
    private final AtomicBoolean motionDetected = new AtomicBoolean(false); // NEW

    // ESP32 sends ultrasonic distance here
    @PostMapping("/sensor")
    public String updateDistance(@RequestBody SensorData data) {
        distance.set(data.getDistance());
        return "Distance updated: " + data.getDistance();
    }

    // Get latest ultrasonic distance
    @GetMapping("/distance")
    public double getDistance() {
        return distance.get();
    }

    // ESP32 or web GUI: GET LED status
    @GetMapping("/led")
    public String getLedStatus() {
        return ledOn.get() ? "ON" : "OFF";
    }

    // Turn LED ON
    @PostMapping("/led/on")
    public String turnOn() {
        ledOn.set(true);
        return "LED is ON";
    }

    // Turn LED OFF
    @PostMapping("/led/off")
    public String turnOff() {
        ledOn.set(false);
        return "LED is OFF";
    }

    // --- Motion API ---
    @PostMapping("/motion")
    public String updateMotion(@RequestBody MotionData data) {
        motionDetected.set(data.isMotion());
        return "Motion updated: " + data.isMotion();
    }

    @GetMapping("/motion")
    public boolean getMotion() {
        return motionDetected.get();
    }
}

// Helper class for distance
class SensorData {
    private double distance;
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
}

// Helper class for motion
class MotionData {
    private boolean motion;
    public boolean isMotion() { return motion; }
    public void setMotion(boolean motion) { this.motion = motion; }
}
