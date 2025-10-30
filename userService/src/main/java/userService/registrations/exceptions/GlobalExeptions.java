package userService.registrations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import userService.registrations.exceptions.exceptionDto.ExceptionalResponseDto;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExeptions {
    @ExceptionHandler(UserExceptions.class)
    public ResponseEntity<ExceptionalResponseDto> userNotFound(UserExceptions e){
        LocalDateTime time=LocalDateTime.now();
        ExceptionalResponseDto dto=new ExceptionalResponseDto(
                e.getMessage(),
                404,
                time
        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
