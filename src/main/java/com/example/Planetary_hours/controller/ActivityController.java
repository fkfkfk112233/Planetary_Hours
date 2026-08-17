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


    public ActivityController(
            ActivityService activityService) {

        this.activityService =
                activityService;
    }


    // ========================================
    // 查詢所有 Activity
    // ========================================

    @GetMapping
    public ResponseEntity<List<Activity>> findAll() {

        return ResponseEntity.ok(
                activityService.findAll()
        );
    }


    // ========================================
    // 查詢指定 Planet
    // ========================================

    @GetMapping("/planet/{planet}")
    public ResponseEntity<List<Activity>> findByPlanet(
            @PathVariable Planet planet) {

        return ResponseEntity.ok(
                activityService.findByPlanet(
                        planet
                )
        );
    }


    // ========================================
    // 查詢指定 ID
    // 只允許取得尚未刪除的資料
    // ========================================

    @GetMapping("/{id}")
    public ResponseEntity<Activity> findById(
            @PathVariable Long id) {

        Activity activity =
                activityService.findById(id);

        if (activity == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                activity
        );
    }


    // ========================================
    // 新增 Activity
    // ========================================

    @PostMapping
    public ResponseEntity<Activity> create(
            @RequestBody Activity activity) {

        Activity saved =
                activityService.create(
                        activity
                );

        return ResponseEntity.ok(
                saved
        );
    }


    // ========================================
    // 修改 Activity
    // ========================================

    @PutMapping("/{id}")
    public ResponseEntity<Activity> update(
            @PathVariable Long id,
            @RequestBody Activity activity) {

        Activity updated =
                activityService.update(
                        id,
                        activity
                );

        if (updated == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                updated
        );
    }


    // ========================================
    // 軟刪除 Activity
    // ========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        boolean deleted =
                activityService.delete(id);

        if (!deleted) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .noContent()
                .build();
    }
}