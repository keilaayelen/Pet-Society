package Pet.Society.models.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerException {

    //CLASE CREADA PARA QUE AL SALIR ESTAS EXCEPCIONES, APAREZCA LA PETICIÓN CORRESPONDIENTE.

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> HandlerUserExistsException(UserExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The user already exists");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> HandlerUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user does not exist");
    }

    @ExceptionHandler(UnsubscribedUserException.class)
    public ResponseEntity<String> HandlerUnsubscribedUserException(UnsubscribedUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The user is unsubscribed");
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<String> HandlerPetNotFoundException(PetNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The pet does not exist");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> HandlerException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error processing the request. " + ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleUniqueConstraintViolation(DataIntegrityViolationException ex) {
        String message = "That data already exists";
        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("duplicate")) {
            if(ex.getMessage().toLowerCase().contains("email")){
                message = "The email already exists";
            }else if(ex.getMessage().toLowerCase().contains("dni")){
                message = "The DNI already exists";
            }
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(UserAttributeException.class)
    public ResponseEntity<String> handlerUserAttributeException(UserAttributeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The data entered is not correct" + ex.getMessage());
    }

    //Mejor forma de colocar los errores. Mas información
    @ExceptionHandler(LoginErrorException.class)
    public ResponseEntity<Map<String, String>> handlerLoginError(LoginErrorException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("An error occurred during login", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(DiagnosesNotFoundException.class)
    public ResponseEntity<String> handlerDiagnosesNotFoundException(DiagnosesNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The diagnoses does not exist " + ex.getMessage());
    }

    @ExceptionHandler(DuplicatedAppointmentException.class)
    public ResponseEntity<String> handlerDuplicatedAppointmentException(DuplicatedAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UnavailableAppointmentException.class)
    public ResponseEntity<String> handlerDuplicatedAppointmentException(UnavailableAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
