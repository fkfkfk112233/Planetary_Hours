package com.example.Planetary_hours.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;

public interface ActivityRepository extends JpaRepository<Activity, Long>{

    // 查詢所有尚未被軟刪除的 Activity
    List<Activity> findByDeletedFalse();

    // 查詢指定 Planet 且尚未被軟刪除的 Activity
    List<Activity> findByPlanetAndDeletedFalse(Planet planet);
}
