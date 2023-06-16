package com.sparta.spring_memo.service;

import com.sparta.spring_memo.dto.MemoRequestDto;
import com.sparta.spring_memo.dto.MemoResponseDto;
import com.sparta.spring_memo.dto.PasswordDto;
import com.sparta.spring_memo.entity.Memo;
import com.sparta.spring_memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.*;

@Service
public class MemoService {

    private final JdbcTemplate jdbcTemplate;

    public MemoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public MemoResponseDto createMemo(MemoRequestDto requestDto) {

        Memo memo = new Memo(requestDto);

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAll();
    }

    public MemoResponseDto getOneMemo(Long id) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findOne(id);

    }

    public MemoResponseDto updateMemo(Long id, MemoRequestDto memoRequestDto) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {

            if(memo.getPassword().equals(memoRequestDto.getPassword())){
                // memo 내용 수정
                memoRepository.update(id, memoRequestDto);
                return memoRepository.findOne(id);

            }else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMemo(Long id, PasswordDto passwordDto) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        Memo memo = memoRepository.findById(id);
        if (memo != null) {

            if(memo.getPassword().equals(memo.getPassword())){
                // memo 내용 삭제
                memoRepository.delete(id);
                return id;
            }else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }

    }

}
