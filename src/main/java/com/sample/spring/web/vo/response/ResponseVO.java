package com.sample.spring.web.vo.response;

public class ResponseVO<T> {

    private String status;
    private String result;
    private ResponseErrorVo error;
    private T data;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public T getData() {
        return data;
    }

    public ResponseErrorVo getError() {
        return error;
    }

    public void setError(ResponseErrorVo error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

}
