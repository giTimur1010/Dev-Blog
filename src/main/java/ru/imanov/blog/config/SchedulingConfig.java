package ru.imanov.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.imanov.blog.job.DeleteSmallArticlesJob;
import ru.imanov.blog.properties.CronProperties;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig implements SchedulingConfigurer {

    private final DeleteSmallArticlesJob deleteSmallArticlesJob;
    private final CronProperties cronProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(deleteSmallArticlesJob, cronProperties.getDeleteSmallArticlesCron());
    }
}
