package com.example.emergency_dashboard.models;

import com.example.emergency_dashboard.serializers.PointDeserializer;
import com.example.emergency_dashboard.serializers.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point; // For PostGIS Geometry
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import java.time.LocalDateTime;


@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "incident_type", nullable = false)
    private String incidentType;

    @Column(name = "severity_level", nullable = false)
    private String severityLevel;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @JsonSerialize(using = PointSerializer.class)   // Custom Serializer
    @JsonDeserialize(using = PointDeserializer.class) // Custom Deserializer
    @Column(name = "location", columnDefinition = "GEOGRAPHY(Point, 4326)", nullable = false)
    private Point location;  // Must store (longitude, latitude)


    public Incident() {}

    // constractor that accepts lattitude and longitude
    public Incident(String incidentType, double latitude, double longitude, String severityLevel) {
        this.incidentType = incidentType;
        this.severityLevel = severityLevel;
        this.location = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }

    public LocalDateTime getTimestamp() { return timestamp; }
    @PrePersist
    public void setTimestamp() { this.timestamp = LocalDateTime.now(); }  // Auto set time before insert


    public Point getLocation() { return location; }
    public void setLocation(Point location) { this.location = location; }

    // alternative setter for the location
    public void setLocation(double latitude, double longitude) {
        this.location = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
    }
}
