package com.am.springbootTodoApplication.services;
import com.am.springbootTodoApplication.Todo.configs.Validator;
import com.am.springbootTodoApplication.Todo.exception.ResourceNotFoundException;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import com.am.springbootTodoApplication.Todo.models.mapper.Mapper;
import com.am.springbootTodoApplication.Todo.repositories.TodoRepository;
import com.am.springbootTodoApplication.Todo.services.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.TransactionSystemException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
public class TodoServiceTest {

    @InjectMocks
    private TodoServiceImpl todoService;

    @Mock
    private TodoRepository mockTodoRepository;

    @Mock
    private Validator validator;
    @BeforeEach
    public void setUp(){
        Validator validator = new Validator();
    }
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

    public Todo getTodo(){
        return generateTodo(40,"description 4", "Title 4", false);
    }

    public List<Todo> getAllTodos(){

        List<Todo> listOfTodos = new ArrayList<>();
        listOfTodos.add(generateTodo(33, "description 1", "Title 1", false));
        listOfTodos.add(generateTodo(44, "description 2", "Title 2", false));
        listOfTodos.add(generateTodo(55, "description 3", "Title 3", true));
        return listOfTodos;
    }

    @Test
    public void givenFilterLimit_whenGetAllTodos_thenSizeOfListOk(){
        when(mockTodoRepository.getAllTodos(5,2)).thenReturn(getAllTodos());
        when(validator.todoValidator(5,2)).thenReturn(true);
        assertEquals(3, todoService.findAllTodos(5,2).size());
    }

    @Test
    public void givenTodoId_whenReadSavedTodoById_thenFoundTodoCorrect() {
        when(mockTodoRepository.findById(40)).thenReturn(Optional.ofNullable(getTodo()));
        Todo todo = todoService.findTodoById(40);
        assertEquals(40, todo.getId());
    }

    @Test
    public void givenTodoId_whenIdNotFound_thenResourceNotFoundException() {
        when(mockTodoRepository.findById(500)).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class, () -> todoService.findTodoById(500));
    }

    @Test
    public void givenTodoBody_whenCreateTodo_thenTodoNotNull(){
        when(mockTodoRepository.save(any())).thenReturn(getTodo());
        Todo todo = todoService.createNewTodo(getTodo());
        assertNotNull(todo);
    }

    @Test
    public void givenTodoBody_whenTitleIsNull_thenTransactionSystemException(){
        when(mockTodoRepository.save(any())).thenThrow(TransactionSystemException.class);
        assertThrows(TransactionSystemException.class, ()-> todoService.createNewTodo(getTodo()));
    }

    @Test
    public void givenNewTodoTitle_whenUpdateTodo_thenTitleCorrectedCorrectly() {
        when(mockTodoRepository.findById(any())).thenReturn(Optional.ofNullable(getTodo()));
        Todo todo = generateTodo(40,"Des 0", "Title 0", false);
        when(mockTodoRepository.save(any())).thenReturn(todo);
        Todo updatedTodo = todoService.updateTodo(todo, 40);
        assertEquals(todo.getTitle(), updatedTodo.getTitle());
    }

    @Test
    public void givenNewTodoDescription_whenUpdateTodo_thenDescriptionCorrectedCorrectly(){
        when(mockTodoRepository.findById(any())).thenReturn(Optional.ofNullable(getTodo()));
        Todo todo = generateTodo(40,"New Description ", "Title 0", false);
        when(mockTodoRepository.save(any())).thenReturn(todo);
        Todo updatedTodo = todoService.updateTodo(todo, 40);
        assertEquals(todo.getDescription(), updatedTodo.getDescription());
    }

    @Test
    public void givenNewSwitching_whenUpdateTodoIsDone_Success_thenIsDoneCorrectedCorrectly(){
        when(mockTodoRepository.findById(any())).thenReturn(Optional.ofNullable(getTodo()));
        Todo todo = generateTodo(40,"Description ", "New Title", true);
        when(mockTodoRepository.save(any())).thenReturn(todo);
        Todo updatedTodo = todoService.updateTodo(todo,40);
        assertEquals(todo.getIsDone(), updatedTodo.getIsDone());
    }

    @Test
    public void givenTodoId_whenDeleteTodoById_IdFound_thenTodoDeletedCorrectly(){
        when(mockTodoRepository.findById(any())).thenReturn(Optional.ofNullable(getTodo()));
        Todo todo = generateTodo(40,"Description ", "New Title", true);
        todoService.deleteTodoById(40);
        assertFalse(mockTodoRepository.existsById(40));
    }

    @Test
    public void givenTodoId_whenDeleteTodoById_IdNotFound_thenResourceNotFoundException(){
        when(mockTodoRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class, () -> todoService.deleteTodoById(500));
    }
}




