package com.example.board.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String msg) {
        super(msg);
    }
}
