package com.example.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto { // 데이터 요청
    private String title;
    private String username;
    private String password;
    private String contents;

    public BoardRequestDto(String title, String username, String password, String contents) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.contents = contents;
    }
}
