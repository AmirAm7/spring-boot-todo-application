package com.am.springbootTodoApplication.Todo.exception;
import com.am.springbootTodoApplication.Todo.models.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse message = new ErrorResponse("404",
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String,String> ee = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap((x-> x.getCodes()[1]),
                        FieldError::getDefaultMessage));

        List<ErrorResponse> errorResponses = new ArrayList<>();
        for (Map.Entry<String,String> map : ee.entrySet()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode(map.getKey());
            errorResponse.setMessage(map.getValue());
            errorResponses.add(errorResponse);
        }
            return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }

}
