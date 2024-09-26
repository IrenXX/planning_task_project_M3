package ru.kemova.task_planner_scheduler.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.kemova.task_planner_scheduler.model.EmailMessageTask;

@Component
@RequiredArgsConstructor
public class EmailTaskProducer {

    public final KafkaTemplate<String, EmailMessageTask> kafkaTemplate;

    public void sendEmailTask(EmailMessageTask emailTask) {
        kafkaTemplate.send("EMAIL_SENDING_TASKS", emailTask);
    }
}
