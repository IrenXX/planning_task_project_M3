package ru.kemova.task_planner_scheduler.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
