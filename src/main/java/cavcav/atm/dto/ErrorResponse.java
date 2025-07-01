package cavcav.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private int code;
    private String message;
    private LocalDateTime timestamp;
}