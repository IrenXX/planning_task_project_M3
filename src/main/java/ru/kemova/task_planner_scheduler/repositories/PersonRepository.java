package ru.kemova.task_planner_scheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kemova.task_planner_scheduler.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
