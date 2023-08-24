package com.ehxhfl.springboottest.handler;

import com.ehxhfl.springboottest.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
//어디서든 예외 발생하면 이 함수로 들어오게끔 하는 어노테이션
@RestController
public class GlobalExceptionHandler {

    //예외 발생 시 하위 함수를 실행시킴
    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
