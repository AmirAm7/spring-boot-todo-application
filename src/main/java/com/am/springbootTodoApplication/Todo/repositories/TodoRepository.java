package com.am.springbootTodoApplication.Todo.repositories;
import com.am.springbootTodoApplication.Todo.models.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findAllByIsDone (boolean isDone);

    @Query(nativeQuery = true,
            value = "select * from todo ORDER by id limit ?1 offset ?2  ")
    List<Todo> getAllTodos(int limit, int offset);


}




