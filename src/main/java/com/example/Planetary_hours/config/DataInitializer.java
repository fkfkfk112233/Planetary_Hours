package com.example.Planetary_hours.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;
import com.example.Planetary_hours.repository.ActivityRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ActivityRepository activityRepository;

    public DataInitializer(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) {

        // Saturn 土星
        addActivity(Planet.SATURN, "深度思考");
        addActivity(Planet.SATURN, "研究");
        addActivity(Planet.SATURN, "紀律訓練");
        addActivity(Planet.SATURN, "建立制度");

        // Jupiter 木星
        addActivity(Planet.JUPITER, "求職");
        addActivity(Planet.JUPITER, "考試");
        addActivity(Planet.JUPITER, "財運相關");
        addActivity(Planet.JUPITER, "學術研究");
        addActivity(Planet.JUPITER, "長期規劃");

        // Mars 火星
        addActivity(Planet.MARS, "運動");
        addActivity(Planet.MARS, "比賽");
        addActivity(Planet.MARS, "執行力工作");

        // Sun 太陽
        addActivity(Planet.SUN, "領導");
        addActivity(Planet.SUN, "求名聲");
        addActivity(Planet.SUN, "面試");
        addActivity(Planet.SUN, "公開演講");
        addActivity(Planet.SUN, "重要決策");

        // Venus 金星
        addActivity(Planet.VENUS, "藝術創作");
        addActivity(Planet.VENUS, "感情交流");
        addActivity(Planet.VENUS, "社交活動");
        addActivity(Planet.VENUS, "美感設計");

        // Mercury 水星
        addActivity(Planet.MERCURY, "學習");
        addActivity(Planet.MERCURY, "程式設計");
        addActivity(Planet.MERCURY, "寫作");
        addActivity(Planet.MERCURY, "商業談判");
        addActivity(Planet.MERCURY, "溝通");

        // Moon 月亮
        addActivity(Planet.MOON, "冥想");
        addActivity(Planet.MOON, "旅行");
        addActivity(Planet.MOON, "情感交流");
        addActivity(Planet.MOON, "潛意識工作");
    }

    private void addActivity(Planet planet, String name) {

        // 如果資料已經存在，就不要重複建立
        if (activityRepository.findByPlanetAndName(planet, name).isPresent()) {
            return;
        }

        Activity activity = new Activity();

        activity.setPlanet(planet);
        activity.setName(name);

        // 目前預設資料沒有另外提供 description，
        // 因此先使用空字串。
        activity.setDescription("");

        activity.setDeleted(false);

        activityRepository.save(activity);
    }
}