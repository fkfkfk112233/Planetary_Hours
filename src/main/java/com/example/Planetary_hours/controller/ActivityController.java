package com.example.Planetary_hours.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Planetary_hours.dto.ActivityRequest;
import com.example.Planetary_hours.dto.ActivityResponse;
import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;
import com.example.Planetary_hours.service.ActivityService;

import jakarta.validation.Valid;


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
    public ResponseEntity<List<ActivityResponse>>
            findAll() {

        List<ActivityResponse> response =
                activityService
                        .findAll()
                        .stream()
                        .map(ActivityResponse::from)
                        .toList();

        return ResponseEntity.ok(
                response
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
    public ResponseEntity<ActivityResponse>
            findById(
                    @PathVariable Long id) {

        Activity activity =
                activityService.findById(id);

        if (activity == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                ActivityResponse.from(activity)
        );
    }


    // ========================================
    // 新增 Activity
    // ========================================

    @PostMapping
    public ResponseEntity<ActivityResponse>
            create(
            		@Valid
            		@RequestBody ActivityRequest request) {

        Activity saved =
                activityService.create(
                        request
                );

        return ResponseEntity.ok(
                ActivityResponse.from(saved)
        );
    }


    // ========================================
    // 修改 Activity
    // ========================================

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> update(
            @PathVariable Long id,

            @Valid
            @RequestBody ActivityRequest request) {

        Activity updated =
                activityService.update(
                        id,
                        request
                );

        if (updated == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                ActivityResponse.from(updated)
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