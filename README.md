# spring_memo
## usecase diagram
![memo_uscase](https://github.com/Minji-Sohn2/spring_memo/assets/130354169/c59bcba2-f468-4eac-85c2-043184eb14de)

## api
![api명세 pdf - Adobe Acrobat Reader (64-bit) 2023-06-15 오후 9_35_53](https://github.com/Minji-Sohn2/spring_memo/assets/130354169/f1057244-a44a-4447-89ac-6cac89566195)


## why?
- 수정, 삭제의 API request 글을 가져오는 id는 path value로, requestDto는 body로 가져왔습니다.

- api 명세서 예시를 참고해 명세서를 작성하고 설계했지만 제대로 실행되지 않아 확인할 수 없었다.

- 강의 자료를 참고해 JdbcTemplate를 사용했고 Controller, Service, Repository를 구분했다.
- 비밀번호를 확인하는 것은 db와 관련된 일이라고 생각하기 때문에 MemoRepository에서 확인하도록 수정하고 싶다.