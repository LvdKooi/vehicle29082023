package nl.kooi.vehicle.api;

import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.exception.VehicleException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(VehicleException.class)
    public ProblemDetail handleVehicleException(VehicleException exception) {
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, exception.getMessage());
    }
}
