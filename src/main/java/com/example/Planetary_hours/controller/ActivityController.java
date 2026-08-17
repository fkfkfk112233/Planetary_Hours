package com.example.Planetary_hours.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.service.ActivityService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // GET /api/activities
    @GetMapping
    public ResponseEntity<List<Activity>> findAll() {
        return ResponseEntity.ok(activityService.findAll());
    }

    // POST /api/activities
    @PostMapping
    public ResponseEntity<Activity> create(@RequestBody Activity activity) {
        Activity saved = activityService.create(activity);

        return ResponseEntity.ok(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Activity> update(
            @PathVariable Long id,
            @RequestBody Activity activity) {

        activity.setId(id);

        Activity updated = activityService.update(activity);

        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        boolean deleted = activityService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}