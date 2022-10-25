package com.am.springbootTodoApplication.Todo.controllers;
import com.am.springbootTodoApplication.Todo.models.dto.TodoDto;
import com.am.springbootTodoApplication.Todo.models.mapper.Mapper;
import com.am.springbootTodoApplication.Todo.services.TodoServiceImpl;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoServiceImpl todoService;
    private final Mapper mapper;


    public TodoController(TodoServiceImpl todoService, Mapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody TodoDto todoDto) {
            Todo newTodo = todoService.createNewTodo(mapper.convertToEntity(todoDto));
            return new ResponseEntity<>(mapper.convertToDto(newTodo), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteTodo(@RequestParam int id) {
            todoService.deleteTodoById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getTodo/{todo-id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable(value = "todo-id") int id) {
            Todo foundTodo = todoService.findTodoById(id);
            return new ResponseEntity<>(mapper.convertToDto(foundTodo), HttpStatus.OK);
    }

    @GetMapping("/allTodos")
    public ResponseEntity<List<TodoDto>> getAllTodos(@RequestParam int limit, @RequestParam int offset){
        List<Todo> allTodos = todoService.findAllTodos(limit, offset);
        return new ResponseEntity<>(mapper.convertToListOfTodoDto(allTodos), HttpStatus.FOUND);
    }

    @GetMapping("/getTodosByFilter")
    public ResponseEntity<List<TodoDto>> getTodosByFilter (@RequestParam String filter){
            List<Todo> todos = todoService.findeTodosByFilter(filter);
            return  new ResponseEntity<>(mapper.convertToListOfTodoDto(todos), HttpStatus.FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<TodoDto> updateTodo(@Valid @RequestBody TodoDto todoDto, @RequestParam int id) {
            Todo updateTodo = todoService.updateTodo(mapper.convertToEntity(todoDto), id);
            return new ResponseEntity<>(mapper.convertToDto(updateTodo), HttpStatus.CREATED);
    }
}
