package com.sparta.spring_memo.controller;

import com.sparta.spring_memo.dto.MemoRequestDto;
import com.sparta.spring_memo.dto.MemoResponseDto;
import com.sparta.spring_memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {

    // DB 역할
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        Memo memo = new Memo(requestDto);

        // 현재 리스트가 비어있다면 (id = 1), 아니라면 (id = 마지막 id 값 + 1)
        Long maxID = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxID);

        // 받아온 시각 설정
        memo.setCreatedAt(timeNow());

        // id를 key 값으로 가지도록 리스트에 추가
        memoList.put(memo.getId(), memo);

        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }


    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map to List
        // stream() -> 앞의 values()를 for 문처럼 하나씩 돌려줌
        // stream()에 의해 하나씩 나오는 객체는 memo
        // 그 memo를 하나의 MemoResponseDto 로 만들어줌
        List<MemoResponseDto> responseDtoList = new ArrayList<>(memoList.values().stream().map(MemoResponseDto::new).toList());

        // 시간 기준으로 내림차순 정렬
        responseDtoList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        // 시간 순으로 id 새로 부여
//        long i = 0;
//        for(MemoResponseDto m : responseDtoList){
//            m.setId(++i);
//        }

        return responseDtoList;
    }


    @GetMapping("/memos/{id}")
    public MemoResponseDto getOneMemo(@PathVariable Long id) {

        MemoResponseDto memoResponseDto;

        if (memoList.containsKey(id)) {
            Memo memo = memoList.get(id);
            memoResponseDto = new MemoResponseDto(memo);

            return memoResponseDto;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }

    }


    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto, @RequestBody String password) {
        if (memoList.containsKey(id)) {

            Memo memo = memoList.get(id);

            if(password.equals(memo.getPassword())){
                memo.setModifiedAt(timeNow());
                memo.update(memoRequestDto);
                return memo.getId();
            }else{
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id, @RequestBody String password) {
        if (memoList.containsKey(id)) {

            Memo memo = memoList.get(id);

            if(password.equals(memo.getPassword())){
                memoList.remove(id);
                return id;
            }else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    // create, update 시각 설정
    private String timeNow() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd h:mm:ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

}
