package com.am.springbootTodoApplication.Todo.models.dto;
//import com.am.springbootTodoApplication.Todo.configs.DateFormatOkay;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {

    @Id
    private int id;
    @Size(min = 1, max = 30, message = "title must be between 1 and 30")
    @NotNull(message = "title must not null")
    private String title;
    @NotNull (message = "description  must not null")
    @Size(min = 2, max = 500, message = "description size must be between 10 and 500")
    private String description;
    @NotNull(message = "dueDate must not be null")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;
    @NotNull(message = "isDone must not be null")
    private boolean isDone;
    @Enumerated(EnumType.STRING)
    private String color;
}

