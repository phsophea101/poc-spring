package com.sample.spring.exception;

import com.sample.spring.conts.BizErrorCode;
import com.sample.spring.web.vo.response.ResponseErrorVo;
import com.sample.spring.web.vo.response.ResponseVO;
import com.sample.spring.web.vo.response.ResponseVOBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class RestExceptionHandlerAdvice {

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> responseException(Exception ex) {
        log.error("Exception {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseVOBuilder<>().fail().error(new ResponseErrorVo(BizErrorCode.E0001.getValue(), BizErrorCode.E0001.getDescription(), ex.getLocalizedMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ResponseVO> responseBizException(BizException ex) {
        log.error("BizException {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseVOBuilder<>().fail().error(new ResponseErrorVo(ex.getError().getValue(), ex.getError().getDescription())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
