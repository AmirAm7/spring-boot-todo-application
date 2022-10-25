package com.am.springbootTodoApplication.Todo.models.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "todo")
public class Todo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 30, message = "title must be between 1 and 30")
    private String title;
    @NotNull
    @Size(min = 2, max = 500, message = "description size must be between 10 and 500")
    private String description;
    @NotNull (message = "dueDate must not be null")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime create_at;
    @NotNull(message = "isDone must not be null")
    private boolean isDone;
    public boolean getIsDone() {
        return isDone;
    }
    public void setIsDone(boolean done) {
        isDone = done;
    }
}
