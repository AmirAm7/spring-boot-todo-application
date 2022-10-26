package com.am.springbootTodoApplication.Todo.exception;

import java.util.List;
import java.util.Map;

public class ValidatorException extends RuntimeException {
    public ValidatorException() {
        super();
    }

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String validation_error, List<Map<String, String>> errors) {
    }
}
