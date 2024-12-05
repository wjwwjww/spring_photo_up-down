package com.example.demo.common;

import lombok.Data;

@Data
public class CommonDTO<T> {
    private boolean success=true;
    private String msg;
    private T content;
}
