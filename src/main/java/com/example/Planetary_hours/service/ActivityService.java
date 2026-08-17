package com.example.Planetary_hours.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.repository.ActivityRepository;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // 查詢所有 Activity
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    // 新增 Activity
    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }

    // 修改 Activity
    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }
}
