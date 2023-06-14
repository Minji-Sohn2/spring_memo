package com.sparta.spring_memo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String title;
    private String username;
    private String contents;
}
