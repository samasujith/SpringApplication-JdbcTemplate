package com.sujith.project.exceptions;

import lombok.*;
import org.springframework.http.*;

import java.time.*;

@Data
@AllArgsConstructor
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
