package ru.kemova.task_planner_scheduler.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kemova.task_planner_scheduler.kafka.EmailTaskProducer;
import ru.kemova.task_planner_scheduler.model.EmailMessageTask;
import ru.kemova.task_planner_scheduler.model.Person;
import ru.kemova.task_planner_scheduler.model.Task;
import ru.kemova.task_planner_scheduler.model.TaskStatus;
import ru.kemova.task_planner_scheduler.repositories.PersonRepository;
import ru.kemova.task_planner_scheduler.repositories.TaskRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepostService {
    private static final int MAX_TASKS_TO_DISPLAY = 5;
    private final EmailTaskProducer emailTaskProducer;
    private final PersonRepository userRepository;
    private final TaskRepository taskRepository;
    private Instant startOfDay;
    private Instant endOfDay;

    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 */5 * * * *")
    public void generateAndSendReports() {
        startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS).minus(1, ChronoUnit.DAYS);
        endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);

        List<Person> people = userRepository.findAll();
        people.forEach(this::processUserReports);
    }

    private void processUserReports(Person person) {
        List<Task> tasks = taskRepository.findAllByPerson(person);
        List<Task> incompleteTasks = filterTasksByStatus(tasks);
        List<Task> completedTodayTasks = filterTasksCompletedToday(tasks);

        EmailMessageTask emailTask = createEmailTask(person, incompleteTasks, completedTodayTasks);
        if (emailTask != null) {
            emailTaskProducer.sendEmailTask(emailTask);
        }
    }

    private List<Task> filterTasksByStatus(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.NEW)
                .collect(Collectors.toList());
    }

    private List<Task> filterTasksCompletedToday(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE
                        && task.getUpdateAt() != null
                        && task.getUpdateAt().isAfter(startOfDay)
                        && task.getUpdateAt().isBefore(endOfDay))
                .collect(Collectors.toList());
    }

    private EmailMessageTask createEmailTask(Person person, List<Task> incompleteTasks, List<Task> completedTasks) {
        String subject = null;
        String body = null;

        if (!incompleteTasks.isEmpty() && !completedTasks.isEmpty()) {
            subject = "У вас осталось выполнить" + incompleteTasks.size() + " задач. Вы выполнили "
                    + completedTasks.size() + " задач";
            body = "Выполненные задачи:\n" + formatTasks(completedTasks) + "\n\nНевыполненные задачи:\n"
                    + formatTasks(incompleteTasks);
        } else if (!incompleteTasks.isEmpty()) {
            subject = "У вас осталось выполнить " + incompleteTasks.size() + " задач";
            body = "Невыполненные задачи:\n" + formatTasks(incompleteTasks);
        } else if (!completedTasks.isEmpty()) {
            subject = "За сегодня вы выполнили " + completedTasks.size() + " задач";
            body = "Выполненные задачи:\n" + formatTasks(completedTasks);
        } else {
            return null;
        }
        return new EmailMessageTask(person.getEmail(), subject, body);
    }

    private String formatTasks(List<Task> tasks) {
        return tasks.stream()
                .limit(MAX_TASKS_TO_DISPLAY)
                .map(Task::getTitle)
                .collect(Collectors.joining("\n"));
    }
}