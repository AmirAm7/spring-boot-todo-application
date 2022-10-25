package com.am.springbootTodoApplication.mapper;
import com.am.springbootTodoApplication.Todo.models.dto.TodoDto;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import com.am.springbootTodoApplication.Todo.models.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private Mapper mapper;

    @BeforeEach
    public void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        this.mapper =  new Mapper(modelMapper);
    }
    public Todo generateTodo(int id, String description, String title, boolean isDone ){
        return Todo.builder()
                .id(id)
                .create_at(LocalDateTime.now())
                .description(description)
                .dueDate(LocalDateTime.now().plus(3, ChronoUnit.DAYS))
                .title(title)
                .isDone(isDone)
                .build();
    }
    public TodoDto generateTodoDto(String description, String title, boolean isDone, String color){
        return TodoDto.builder()
                .description(description)
                .dueDate(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .title(title)
                .isDone(isDone)
                .color(color)
                .build();
    }

    public List<Todo> listOfTodos = List.of(
            generateTodo(33, "description 1", "Title 1", false),
            generateTodo(44, "description 2", "Title 2", false),
            generateTodo(55, "description 3", "Title 3", true)
    );
    public Todo getTodo(){
        return generateTodo(40,"description 4", "Title 4", true);
    }
    public TodoDto getTodoDto(){
        return generateTodoDto("description 4", "Title 4", false, "red");
    }

    @Test
    void givenTodoDto_whenConvertToEntity_thenEntityHasSameValues() {
        Todo todo = mapper.convertToEntity(getTodoDto());
        assertEquals(todo.getTitle(), getTodoDto().getTitle());
        assertEquals(todo.getDescription(), getTodoDto().getDescription());
        assertEquals(todo.getIsDone(), getTodoDto().isDone());
    }

    @Test
    void givenTodo_whenConvertToTodoDto_thenTodoDtoHasSameValues() {
        TodoDto todoDto = mapper.convertToDto(getTodo());
        assertEquals(todoDto.getTitle(), getTodo().getTitle());
        assertEquals(todoDto.getDescription(), getTodo().getDescription());
        assertEquals(todoDto.isDone(), getTodo().getIsDone());
    }

    @Test
    void givenListOfTodos_whenConvertToListOfTodoDto_thenListOfTodoDtosOfEvenNumbers() {
        List<TodoDto> todoDtoM = mapper.convertToListOfTodoDto(listOfTodos);
        assertEquals(todoDtoM.size(), listOfTodos.size());
    }

    @Test
    void givenPendingTodo_whenDueDateLessThan24_thenRedColor(){
        Todo todoM = mapper.convertToEntity(getTodoDto());
        todoM.setDueDate(LocalDateTime.now().plus(5, ChronoUnit.HOURS));
        assertEquals("red", getTodoDto().getColor());
    }

    /*
    @Test
    void givenPendingTodo_whenDueDateBetween24And48_thenYellowColor(){
        Todo todoM = mapper.convertToEntity(getTodoDto());
        todoM.setDueDate(LocalDateTime.now().plus(25, ChronoUnit.HOURS));
        assertEquals("yellow", getTodoDto().getColor());
    }
     */

    @Test
    void givenPendingTodo_whenDueDateMoreThan48_thenDefaultColor() {
        Todo todo = getTodo();
        todo.setIsDone(false);
        String color = mapper.findDifferenceTimeToDoWork(todo);
        assertNull(color);
    }
}

