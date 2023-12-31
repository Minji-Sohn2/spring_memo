package com.sparta.spring_memo.dto;

import com.sparta.spring_memo.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private String createdAt;
    private String modifiedAt;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }

    public MemoResponseDto(Long id, String username, String contents, String title, String createdAt, String modifiedAt) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
