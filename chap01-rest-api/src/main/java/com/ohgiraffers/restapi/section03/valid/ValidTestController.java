package com.ohgiraffers.restapi.section03.valid;

import com.ohgiraffers.restapi.section02.responseEntity.ResponseMessage;
import com.ohgiraffers.restapi.section03.valid.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/valid")
public class ValidTestController {

    private List<UserDTO> users;

    /* 임시 DB 에서 조회한 값 설정 */
    public ValidTestController() {
        users = new ArrayList<>();

        users.add(new UserDTO(1, "usesr01", "pass01", "카이도우", LocalDateTime.now()));
        users.add(new UserDTO(2, "usesr02", "pass02", "몽키D루피", LocalDateTime.now()));
        users.add(new UserDTO(3, "usesr03", "pass03", "샹크스", LocalDateTime.now()));
        users.add(new UserDTO(4, "usesr04", "pass04", "버기", LocalDateTime.now()));
    }

    @GetMapping("/user/{userNo}")
    public ResponseEntity<ResponseMessage> findByNo(@PathVariable int userNo) throws UserNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<UserDTO> foundUserList = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList());

        UserDTO foundUser = null;
        // userNo 조회 시 조회 됨
        if(foundUserList.size() > 0) {
            foundUser = foundUserList.get(0);
        } else {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        return ResponseEntity.ok().headers(headers)
                                  .body(new ResponseMessage(200, "조회 성공!!!", responseMap));
    }

    @PostMapping("/user/regist")
    private ResponseEntity<?> registUser(@Valid @RequestBody UserDTO newUser) {

        System.out.println("새로운 유저 : " + newUser);

        return ResponseEntity.created(URI.create("/valid/user/" + newUser.getNo())).build();
    }

}
