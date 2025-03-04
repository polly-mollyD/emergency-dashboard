package com.example.emergency_dashboard.controller;

import com.example.emergency_dashboard.models.Incident;
import com.example.emergency_dashboard.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;
    private final GeometryFactory geometryFactory = new GeometryFactory(); // For Point conversion

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody IncidentRequest request) {
        try {
            // Convert longitude/latitude to Point
            Point location = geometryFactory.createPoint(new Coordinate(request.getLongitude(), request.getLatitude()));

            // Create new Incident
            Incident newIncident = new Incident();
            newIncident.setIncidentType(request.getIncidentType());
            newIncident.setLocation(location);
            newIncident.setSeverityLevel(request.getSeverityLevel());

            Incident savedIncident = incidentService.createIncident(newIncident);
            return ResponseEntity.ok(savedIncident);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    static class IncidentRequest {
        private String incidentType;
        private double latitude;
        private double longitude;
        private String severityLevel;

        // Getters and Setters
        public String getIncidentType() {
            return incidentType;
        }

        public void setIncidentType(String incidentType) {
            this.incidentType = incidentType;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getSeverityLevel() {
            return severityLevel;
        }

        public void setSeverityLevel(String severityLevel) {
            this.severityLevel = severityLevel;
        }
    }

    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentService.getIncidentById(id);
        return incident.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.ok("Incident deleted successfully");
    }
}
