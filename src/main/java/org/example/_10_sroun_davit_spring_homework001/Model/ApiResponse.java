package org.example._10_sroun_davit_spring_homework001.Model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse <T>{
    private boolean success;
    private String message;
    private HttpStatus status;

    private T pay;
    private LocalDateTime localDateTime;
}
