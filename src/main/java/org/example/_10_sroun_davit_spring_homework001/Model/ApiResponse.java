package org.example._10_sroun_davit_spring_homework001.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private HttpStatus status;
    private T payload;
    private LocalDateTime localDateTime;



    public ApiResponse(boolean success, String message, HttpStatus status, LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.status = status;
        this.message = message;
        this.success = success;
    }

}
