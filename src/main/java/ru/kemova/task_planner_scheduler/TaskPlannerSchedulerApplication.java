package ru.kemova.task_planner_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskPlannerSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskPlannerSchedulerApplication.class, args);
    }

}
