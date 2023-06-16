package com.sparta.spring_memo.controller;

import com.sparta.spring_memo.dto.MemoRequestDto;
import com.sparta.spring_memo.dto.MemoResponseDto;
import com.sparta.spring_memo.dto.PasswordDto;
import com.sparta.spring_memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final JdbcTemplate jdbcTemplate;

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.createMemo(requestDto);

    }


    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getMemos();

    }


    @GetMapping("/memos/{id}")
    public MemoResponseDto getOneMemo(@PathVariable Long id) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getOneMemo(id);

    }


    @PutMapping("/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.updateMemo(id, memoRequestDto);

    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody PasswordDto passwordDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.deleteMemo(id, passwordDto);

    }

}
