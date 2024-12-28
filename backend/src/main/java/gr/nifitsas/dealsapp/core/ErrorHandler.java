package gr.nifitsas.dealsapp.core;

import gr.nifitsas.dealsapp.core.exceptions.*;
import gr.nifitsas.dealsapp.dto.ResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
      String bodyOfResponse = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
      return new ResponseEntity<>(new ResponseDTO(ex.getStatusCode().toString(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }


  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler({AppObjectNotFoundException.class})
  public ResponseEntity<ResponseDTO> handleAppObjectNotFoundException(AppObjectNotFoundException ex) {
    return new ResponseEntity<>(new ResponseDTO(ex.getCode(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({AppGenericException.class,AppServerException.class})
  public ResponseEntity<ResponseDTO> handleAppGenericException(AppGenericException ex) {
    return new ResponseEntity<>(new ResponseDTO(ex.getCode(), ex.getMessage()),  HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({AppObjectAlreadyExistsException.class})
  public ResponseEntity<ResponseDTO> handleAppObjectAlreadyExists(AppObjectAlreadyExistsException ex) {
    return new ResponseEntity<>(new ResponseDTO(ex.getCode(), ex.getMessage()), HttpStatus.CONFLICT);
  }
  @ExceptionHandler({AppObjectInvalidArgumentException.class})
  public ResponseEntity<ResponseDTO> handleAppObjectInvalidArgumentException(AppObjectInvalidArgumentException ex) {
    return new ResponseEntity<>(new ResponseDTO(ex.getCode(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler({AppObjectNotAuthorizedException.class})
  public ResponseEntity<ResponseDTO> handleAppObjectNotAuthorizedException(AppObjectNotAuthorizedException ex) {
    return new ResponseEntity<>(new ResponseDTO(ex.getCode(), ex.getMessage()), HttpStatus.UNAUTHORIZED);
  }


}
