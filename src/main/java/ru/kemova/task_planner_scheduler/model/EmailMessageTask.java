package ru.kemova.task_planner_scheduler.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageTask {

    @Size(min = 2, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @NotBlank(message = "Заголовок сообщения не может быть пустыми")
    @Size(min = 1, max = 50, message = "Заголовок должен содержать от 5 до 255 символов")
    private String title;

    private String body;
}
