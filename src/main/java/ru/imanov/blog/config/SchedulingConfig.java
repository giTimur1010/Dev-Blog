package ru.imanov.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.imanov.blog.job.DeleteSmallArticlesJob;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig implements SchedulingConfigurer {

    private final DeleteSmallArticlesJob deleteSmallArticlesJob;

    @Value("${application.cron-task.delete-small-articles-cron}")
    private String deleteArticlesCron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(deleteSmallArticlesJob, deleteArticlesCron);
    }
}
