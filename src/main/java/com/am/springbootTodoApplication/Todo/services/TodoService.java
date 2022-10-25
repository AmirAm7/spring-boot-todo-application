package com.am.springbootTodoApplication.Todo.services;

import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import java.util.List;

public interface TodoService {

    Todo updateTodo(Todo todo, int id);
    Todo createNewTodo (Todo todo);
    void deleteTodoById(int id);
    Todo findTodoById(int id);
    List<Todo> findeTodosByFilter(String filter);
    List<Todo> findAllTodos (int limit, int offset);


}
