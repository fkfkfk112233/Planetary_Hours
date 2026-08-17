package com.example.Planetary_hours.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
    // 查詢指定 Planet 的 Activity
    // ========================================

    public List<Activity> findByPlanet(
            Planet planet) {

        return activityRepository
                .findByPlanetAndDeletedFalse(
                        planet
                );
    }


    // ========================================
    // 查詢指定 ID 的 Activity
    // 只能取得尚未刪除的資料
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
            Activity activity) {

        // Activity 新增時一定是啟用狀態
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
            Activity activity) {

        return activityRepository
                .findByIdAndDeletedFalse(id)
                .map(existing -> {

                    existing.setPlanet(
                            activity.getPlanet()
                    );

                    existing.setName(
                            activity.getName()
                    );

                    existing.setDescription(
                            activity.getDescription()
                    );

                    return activityRepository.save(
                            existing
                    );
                })
                .orElse(null);
    }


    // ========================================
    // 軟刪除 Activity
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