package com.example.Planetary_hours.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;
import com.example.Planetary_hours.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // 查詢所有尚未刪除的 Activity
    public List<Activity> findAll() {
        return activityRepository.findByDeletedFalse();
    }

    // 查詢指定 Planet 的 Activity
    public List<Activity> findByPlanet(Planet planet) {
        return activityRepository.findByPlanetAndDeletedFalse(planet);
    }
    
    public Activity findById(Long id) {

        return activityRepository.findById(id)
                .orElse(null);
    }

    // 新增 Activity
    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }

    // 修改 Activity
    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }
    
    // 軟刪除 Activity
    public boolean delete(Long id) {

        return activityRepository.findById(id)
                .map(activity -> {
                    activity.setDeleted(true);
                    activityRepository.save(activity);
                    return true;
                })
                .orElse(false);
    }
}
