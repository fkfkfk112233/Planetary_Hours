package com.example.Planetary_hours.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;
import com.example.Planetary_hours.service.ActivityService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // GET /api/activities
    // 查詢所有尚未被軟刪除的 Activity
    @GetMapping
    public ResponseEntity<List<Activity>> findAll() {
        return ResponseEntity.ok(
                activityService.findAll()
        );
    }

    // GET /api/activities/planet/{planet}
    // 查詢指定 Planet 的 Activity
    @GetMapping("/planet/{planet}")
    public ResponseEntity<List<Activity>> findByPlanet(
            @PathVariable Planet planet) {

        return ResponseEntity.ok(
                activityService.findByPlanet(planet)
        );
    }

    // POST /api/activities
    // 新增 Activity
    @PostMapping
    public ResponseEntity<Activity> create(
            @RequestBody Activity activity) {

        Activity saved =
                activityService.create(activity);

        return ResponseEntity.ok(saved);
    }

    // PUT /api/activities/{id}
    // 修改 Activity
    @PutMapping("/{id}")
    public ResponseEntity<Activity> update(
            @PathVariable Long id,
            @RequestBody Activity activity) {

        activity.setId(id);

        Activity updated =
                activityService.update(activity);

        return ResponseEntity.ok(updated);
    }

    // DELETE /api/activities/{id}
    // 軟刪除 Activity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        boolean deleted =
                activityService.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}