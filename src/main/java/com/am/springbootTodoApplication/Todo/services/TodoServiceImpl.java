package com.am.springbootTodoApplication.Todo.services;
import com.am.springbootTodoApplication.Todo.configs.Validator;
import com.am.springbootTodoApplication.Todo.exception.ResourceNotFoundException;
import com.am.springbootTodoApplication.Todo.models.enums.State;
import com.am.springbootTodoApplication.Todo.repositories.TodoRepository;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private Validator validator;
    @Override
    public Todo createNewTodo (Todo todo){
        todoRepository.save(todo);
        return todo;
    }

    @Override
    public List<Todo> findAllTodos (int limit, int offset){
        List<Todo> todos = null;
        if (validator.todoValidator(limit, offset)) {
            todos = todoRepository.getAllTodos(limit, offset);
        }
        return todos;
    }


    @Override
    public List<Todo> findeTodosByFilter(String filter) {
        State userState = State.valueOf(filter.toUpperCase());

        if (userState == State.UNFINISHED) {
            boolean isDone = false;
            return todoRepository.findAllByIsDone(isDone).stream().sorted(Comparator.comparing(Todo::getDueDate)).toList();
        } else if (userState == State.FINISHED) {
            boolean isDone = true;
            return todoRepository.findAllByIsDone(isDone).stream().sorted(Comparator.comparing(Todo::getDueDate)).collect(Collectors.toList());
        }
        return todoRepository.findAll().stream().sorted(Comparator.comparing(Todo::getCreate_at)).collect(Collectors.toList());
    }

    @Override
    public Todo findTodoById (int id){
        return todoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("TODO not found with id: " + id));
    }

    @Override
    public Todo updateTodo(Todo todo, int id) {
                Todo savedTodo = findTodoById(id);
                savedTodo.setTitle(todo.getTitle());
                savedTodo.setDescription(todo.getDescription());
                savedTodo.setDueDate(todo.getDueDate());
                savedTodo.setIsDone(todo.getIsDone());
                todoRepository.save(savedTodo);
                return todo;
    }
    @Override
    public void deleteTodoById(int id) {
        Todo savedTodo = findTodoById(id);
        todoRepository.deleteById(savedTodo.getId());
    }

}
