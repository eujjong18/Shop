package com.devcourse.shop.common;

import lombok.Getter;

@Getter
public class ResponseEntity<T> {
    private final int status;
    private final T data;
    private final long count;

    public ResponseEntity(int status, T all, long count){
        this.status = status;
        this.data = all;
        this.count = count;
    }
}
