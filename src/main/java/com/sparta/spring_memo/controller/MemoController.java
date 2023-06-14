package com.sparta.spring_memo.controller;

import com.sparta.spring_memo.dto.MemoRequestDto;
import com.sparta.spring_memo.dto.MemoResponseDto;
import com.sparta.spring_memo.entity.Memo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    // DB 역할
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto){
        Memo memo = new Memo(requestDto);
        // 현재 리스트가 비어있다면 (id = 1), 아니라면 (id = 마지막 id 값 + 1)
        Long maxID = memoList.size()>0? Collections.max(memoList.keySet())+1 : 1;
        memo.setId(maxID);

        // 받아온 시각 설정
        memo.setDate(memo.timeNow());
        // id를 key 값으로 가지도록 리스트에 추가
        memoList.put(memo.getId(), memo);

        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }
}
