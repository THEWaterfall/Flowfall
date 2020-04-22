package waterfall.flowfall.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import waterfall.flowfall.model.dto.ErrorDto;
import waterfall.flowfall.model.dto.ErrorResponseDto;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorDto> errors = new ArrayList<>();

        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorDto(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        ErrorResponseDto errorResponse =
                new ErrorResponseDto(ex.getLocalizedMessage(), BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.value(), errors);

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorResponseDto errorResponse
                = new ErrorResponseDto(ex.getMessage(), FORBIDDEN.getReasonPhrase(), FORBIDDEN.value());

        return new ResponseEntity<>(errorResponse, FORBIDDEN);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponseDto errorResponse
                = new ErrorResponseDto(ex.getMessage(), INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}
