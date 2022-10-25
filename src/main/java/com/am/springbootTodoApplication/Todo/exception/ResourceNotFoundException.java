package com.am.springbootTodoApplication.Todo.exception;

import java.util.List;
import java.util.Map;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String validation_error, List<Map<String, String>> errors) {
    }
}


