package com.example.Planetary_hours.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.dto.ActivityRequest;
import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;
import com.example.Planetary_hours.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;


    public ActivityService(
            ActivityRepository activityRepository) {

        this.activityRepository =
                activityRepository;
    }


    // ========================================
    // 查詢所有尚未刪除的 Activity
    // ========================================

    public List<Activity> findAll() {

        return activityRepository
                .findByDeletedFalse();
    }


    // ========================================
    // 查詢指定 Planet
    // ========================================

    public List<Activity> findByPlanet(
            Planet planet) {

        return activityRepository
                .findByPlanetAndDeletedFalse(
                        planet
                );
    }


    // ========================================
    // 查詢指定 ID
    // ========================================

    public Activity findById(Long id) {

        return activityRepository
                .findByIdAndDeletedFalse(id)
                .orElse(null);
    }


    // ========================================
    // 新增 Activity
    // ========================================

    public Activity create(
            ActivityRequest request) {

        Activity activity =
                new Activity();

        activity.setPlanet(
                request.getPlanet()
        );

        activity.setName(
                request.getName()
        );

        activity.setDescription(
                request.getDescription()
        );

        // deleted 永遠由後端控制
        activity.setDeleted(false);

        return activityRepository.save(
                activity
        );
    }


    // ========================================
    // 修改 Activity
    // ========================================

    public Activity update(
            Long id,
            ActivityRequest request) {

        return activityRepository
                .findByIdAndDeletedFalse(id)
                .map(existing -> {

                    existing.setPlanet(
                            request.getPlanet()
                    );

                    existing.setName(
                            request.getName()
                    );

                    existing.setDescription(
                            request.getDescription()
                    );

                    return activityRepository.save(
                            existing
                    );
                })
                .orElse(null);
    }


    // ========================================
    // Soft Delete
    // ========================================

    public boolean delete(Long id) {

        return activityRepository
                .findByIdAndDeletedFalse(id)
                .map(activity -> {

                    activity.setDeleted(true);

                    activityRepository.save(
                            activity
                    );

                    return true;
                })
                .orElse(false);
    }
}