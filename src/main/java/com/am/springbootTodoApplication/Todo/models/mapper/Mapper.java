package com.am.springbootTodoApplication.Todo.models.mapper;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import com.am.springbootTodoApplication.Todo.models.dto.TodoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Component
public class Mapper {

    private final ModelMapper modelMapper;
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TodoDto convertToDto(Todo todo) {
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        todoDto.setColor(findDifferenceTimeToDoWork(todo));
        return todoDto;
    }

    public List<TodoDto> convertToListOfTodoDto (List<Todo> todos){
        List<TodoDto> listOfTodoDtos = new ArrayList<>();
        for (Todo todo: todos) {
            TodoDto newTodoDto =  convertToDto(todo);
            listOfTodoDtos.add(newTodoDto);
        }
        return listOfTodoDtos;
    }

    public Todo convertToEntity(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setCreate_at(LocalDateTime.now());
        return todo;
    }

    public String findDifferenceTimeToDoWork (Todo todo){
        // LocalDateTime now = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(todo.getCreate_at(), todo.getDueDate());

        if (todo.getIsDone()){
            return "Green";
        } else if (hours > 48) {
            return null;
        } else if (hours > 24 && hours < 48) {
            return "Yellow";
        } else if (hours > 0 && hours < 24) {
            return "Red";
        }
        return null;
    }
}
