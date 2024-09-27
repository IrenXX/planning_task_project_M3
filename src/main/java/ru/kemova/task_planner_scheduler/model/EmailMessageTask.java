package ru.kemova.task_planner_scheduler.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageTask {

    private String email;

    private String title;

    private String body;
}
