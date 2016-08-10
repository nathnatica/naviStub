package com.test.stub;

public class Response {

    ResponseEntity response = new ResponseEntity();

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }
}
