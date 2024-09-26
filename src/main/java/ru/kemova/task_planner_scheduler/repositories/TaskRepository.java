package ru.kemova.task_planner_scheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kemova.task_planner_scheduler.model.Person;
import ru.kemova.task_planner_scheduler.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByPerson(Person person);
}