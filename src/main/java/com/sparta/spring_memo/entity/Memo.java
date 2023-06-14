package com.sparta.spring_memo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private String date;


}
