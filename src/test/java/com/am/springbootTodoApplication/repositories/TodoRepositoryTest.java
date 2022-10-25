package com.am.springbootTodoApplication.repositories;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import com.am.springbootTodoApplication.Todo.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    public Todo generateTodo(int id, String description, String title, boolean isDone ){
        return Todo.builder()
                .id(id)
                .create_at(LocalDateTime.now())
                .description(description)
                .dueDate(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
                .title(title)
                .isDone(isDone)
                .build();
    }

    public List<Todo> listOfTodos (){
        List<Todo> todos = new ArrayList<>();
        Todo todo1 = generateTodo(0, "DES 1", "TITLE 1", false);
        Todo todo2 = generateTodo(0, "DES 2", "TITLE 2", true);
        Todo todo3 =generateTodo(0, "DES 3", "TITLE 3", false);
        todos.add(todo1);
        todos.add(todo2);
        todos.add(todo3);
        return todos;
    }
    public Todo getTodo(){
        return generateTodo(40,"description 4", "Title 4", false);
    }

    @Test
    public void givenTodo_whenSave_thenGetOk() {
        Todo genericTodo = todoRepository
                .save(getTodo());
        assertNotEquals(40, genericTodo.getId());
    }

    @Test
    public void givenGetAllTodos_whenLimit3_thenGive3(){
        todoRepository.saveAll(listOfTodos());
        List<Todo> listOfTodos = todoRepository.getAllTodos(2,1);
        assertEquals(2, listOfTodos.size());
        assertNotEquals(3,listOfTodos.size());
    }

    @Test
    public void givenListOfTodos_whenIsDonTrueOrFalse_thenCorrectSize (){
        todoRepository.saveAll(listOfTodos());
        List<Todo> listOfTodosISDoneT = todoRepository.findAllByIsDone(true);
        List<Todo> listOfTodosISDoneF = todoRepository.findAllByIsDone(false);
        assertEquals(1, listOfTodosISDoneT.size());
        assertEquals(2, listOfTodosISDoneF.size());
    }
}
