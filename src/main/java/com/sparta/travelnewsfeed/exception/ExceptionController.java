package com.sparta.travelnewsfeed.exception;

import com.sparta.travelnewsfeed.dto.response.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  // userconctrollerd의 IllegalArgumentException에 대한 전역 예외 처리기
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<CommonResponseDto> handleIllegalArgumentException(
      IllegalArgumentException exception) {

    CommonResponseDto response = new CommonResponseDto(exception.getMessage(),
        HttpStatus.BAD_REQUEST.value());
    return ResponseEntity
        .badRequest()
        .body(response);
  }

  @ExceptionHandler(ExistUserException.class)
  public ResponseEntity<CommonResponseDto> handleExistUserException(
      RuntimeException exception) {

    CommonResponseDto response = new CommonResponseDto(exception.getMessage(),
        HttpStatus.BAD_REQUEST.value());
    return ResponseEntity
        .badRequest()
        .body(response);
  }




}
