package com.example.Planetary_hours.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;

public interface ActivityRepository extends JpaRepository<Activity, Long>{

    // 查詢所有尚未被軟刪除的 Activity
    List<Activity> findByDeletedFalse();

    // 查詢指定 Planet 且尚未被軟刪除的 Activity
    List<Activity> findByPlanetAndDeletedFalse(Planet planet);
    
    // 用 Planet + Activity 名稱尋找資料
    // 用來避免初始化資料重複建立
    Optional<Activity> findByPlanetAndName(Planet planet, String name);
}
