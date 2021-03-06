package pe.bvva.pruebatecnica.apitipocambio.config;

import lombok.extern.slf4j.Slf4j;
import pe.bvva.pruebatecnica.apitipocambio.exceptions.BadRequestException;
import pe.bvva.pruebatecnica.apitipocambio.exceptions.NoDataFoundException;
import java.util.HashMap;
import java.util.Map;
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

@ControllerAdvice
@Slf4j
public class ResponseExcepcionConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> all(Exception e, WebRequest request) {
        log.error("Error",e);
        return new ResponseEntity("Se presento un error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException .class)
    public ResponseEntity<?> accessDenied(Exception e, WebRequest request) {
        log.error("Error",e);
        return new ResponseEntity("Usted no tiene authorizacion", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> noDataFound(Exception e, WebRequest request) {
        return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(Exception e, WebRequest request) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);

    }
}
