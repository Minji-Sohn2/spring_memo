package com.sparta.spring_memo.service;

import com.sparta.spring_memo.dto.MemoRequestDto;
import com.sparta.spring_memo.dto.MemoResponseDto;
import com.sparta.spring_memo.entity.Memo;
import com.sparta.spring_memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.*;

public class MemoService {

    private final JdbcTemplate jdbcTemplate;

    private final Map<Long, Memo> memoList = new HashMap<>();

    public MemoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public MemoResponseDto createMemo(MemoRequestDto requestDto) {

        Memo memo = new Memo(requestDto);

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

//        // 현재 리스트가 비어있다면 (id = 1), 아니라면 (id = 마지막 id 값 + 1)
//        Long maxID = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
//        memo.setId(maxID);
//
//        // 받아온 시각 설정
//        memo.setCreatedAt(timeNow());
//
//        // id를 key 값으로 가지도록 리스트에 추가
//        memoList.put(memo.getId(), memo);

        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAll();
//        // Map to List
//        // stream() -> 앞의 values()를 for 문처럼 하나씩 돌려줌
//        // stream()에 의해 하나씩 나오는 객체는 memo
//        // 그 memo를 하나의 MemoResponseDto 로 만들어줌
//        List<MemoResponseDto> responseDtoList = new ArrayList<>(memoList.values().stream().map(MemoResponseDto::new).toList());
//
//        // 시간 기준으로 내림차순 정렬
//        responseDtoList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
//
//        return responseDtoList;
    }

    public MemoResponseDto getOneMemo(Long id) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findOne(id);

    }

    public MemoResponseDto updateMemo(Long id, MemoRequestDto memoRequestDto, String password) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {

            if(password.equals(memo.getPassword())){
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

    public Long deleteMemo(Long id, String password) {

        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        Memo memo = memoRepository.findById(id);
        if (memo != null) {

            if(password.equals(memo.getPassword())){
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
