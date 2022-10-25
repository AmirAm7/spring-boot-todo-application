package com.am.springbootTodoApplication.Todo.configs;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validator {
    public List<Map<String, String>> todoValidator (int limit, int offset) {
        List<Map<String, String>> body = new ArrayList<>();

        if (limit < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "LIMIT_MIN");
            error.put("message", "limit must be greater or equal to 0");
            body.add(error);
        }
        if (limit > 10) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "LIMIT_MAX");
            error.put("message", "limit must be less or equal to 10");
            body.add(error);
        }
        if (offset < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "OFFSET_MIN");
            error.put("message", "offset must be greater or equal to 0");
            body.add(error);
        }
        if (offset > 100) {
            Map<String, String> error = new HashMap<>();
            error.put("code", "OFFSET_MAX");
            error.put("message", "offset must be less or equal to 100");
            body.add(error);
        }
        return body;
    }
}
