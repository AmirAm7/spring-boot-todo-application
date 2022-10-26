package com.am.springbootTodoApplication.Todo.configs;

import com.am.springbootTodoApplication.Todo.exception.ValidatorException;
import org.springframework.stereotype.Component;
@Component
public class Validator {

    public boolean todoValidator (int limit, int offset) {
        String valid1 = "";
        String valid2 = "";

        if (limit < 0) {
            valid1 = "limit must be greater than 0";
        } else if (limit > 10) {
            valid1 = "limit must be less or equal to 10";
        }

        if (offset < 0) {
            valid2 = "offset must be greater or equal to 0";
        } else if (offset > 100) {
            valid2 = "offset must be less or equal to 100";
        }
        if (!valid1.isEmpty() || !valid2.isEmpty()) {
            throw new ValidatorException(valid1 + " " + valid2);
        }
        return true;
    }
}


