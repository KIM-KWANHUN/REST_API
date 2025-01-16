package com.ohgiraffers.restapi.section02.responseEntity;

import lombok.Getter;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entity")
public class ResponseEntityController {

    /* comment.
    *   ResponseEntity 란?
    *   결과 데이터와 HTTP 상태 코드 를 직접 제어할 수 있는
    *   클래스이다.
    *   내부에 HttpStatus, HttpHeader, HttpBody 를 포함한다. */

    private List<UserDTO> users;

    /* 임시 DB 에서 조회한 값 설정 */
    public ResponseEntityController() {
        users = new ArrayList<>();

        users.add(new UserDTO(1, "usesr01", "pass01", "너구리", LocalDateTime.now()));
        users.add(new UserDTO(2, "usesr02", "pass02", "푸바오", LocalDateTime.now()));
        users.add(new UserDTO(3, "usesr03", "pass03", "러바오", LocalDateTime.now()));
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseMessage> findAllUser() {

        HttpHeaders headers = new HttpHeaders();
        // 응답 할 데이터의 양식 지정
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("users", users);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/user/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        HttpHeaders headers = new HttpHeaders();
        // 응답 할 데이터의 양식 지정
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        UserDTO foundUser =
                    users.stream()
                         .filter(user -> user.getNo() == userNo)
                         .collect(Collectors.toList())
                         .get(0);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(new ResponseMessage(200, "조회성공", responseMap));

    }
    /* comment.
    *   form 태그로 데이터 전달 받는 것과
    *   javaScript 로 데이터 전달 받는 것이 다르다. */
    @PostMapping("/user/regist")
    public ResponseEntity<?> regist(@RequestBody UserDTO newUser) {

        System.out.println("Json 데이터 @RequestBody 로 들어오나 확인용 = " + newUser);

        // List 에 들어있는 마지막 no 가져오기
        int lastNo = users.get(users.size() - 1).getNo();
        newUser.setNo(lastNo + 1);

        return ResponseEntity
                // 201 상태 코드 -> 등록 관련(자원 생성 관련) 상태코드
                .created(URI.create("entity/users" + users.get(users.size() -1 ).getNo())).build();
    }

    /* 수정 */
    @PutMapping("/user/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody UserDTO userModify) {
        // 회원정보 수정을 위한 유저 특정하기
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);

        foundUser.setId(userModify.getId());
        foundUser.setPwd(userModify.getPwd());
        foundUser.setName(userModify.getName());

        return ResponseEntity.created(URI.create("/entity/users" + userNo)).build();
    }

    /* 삭제 */
    @DeleteMapping("/user/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo) {

        // userNo 1명 특정
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);

        // 특정한 유저 객체 배열에서 삭제
        users.remove(foundUser);

        // 자원 삭제 관련 noContent
        return ResponseEntity.noContent().build();
    }
}
