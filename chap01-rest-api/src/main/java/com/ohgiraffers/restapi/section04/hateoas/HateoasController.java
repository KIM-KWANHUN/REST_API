package com.ohgiraffers.restapi.section04.hateoas;

import com.ohgiraffers.restapi.section02.responseEntity.ResponseMessage;
import org.apache.catalina.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasController {

    /* comment.
    *   Hateoas 란?
    *   하나의 API 를 동작시켰을 때, 그 전 상황이나, 그 후 상황에 대한
    *   충분한 정보(링크) 를 제공함으로서 클라이언트가 리소스(자원)에 대한 이해를 더욱 잘 할 수 있게
    *   링크를 제공하는 것.
    *   RestFul API 라고도 하낟. -> 정보가 충분함 */

    private List<UserDTO> user;

    public HateoasController() {

        user = new ArrayList<>();

        user.add(new UserDTO(1, "user01", "pass01", "아카이누", LocalDateTime.now()));
        user.add(new UserDTO(1, "user02", "pass02", "키자루", LocalDateTime.now()));
        user.add(new UserDTO(1, "user03", "pass03", "아오키지", LocalDateTime.now()));
    }

    @GetMapping("/user/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        UserDTO foundUser = user.stream().filter(users -> users.getNo() == userNo).collect(Collectors.toList()).get(0);

        Map<String, Object> response = new HashMap<>();
        response.put("user", foundUser);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage
                (200, "조회 성공!!", response));
    }

    /* Hateoas 적용 전체조회 API */
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        /* EntityModel ? Hateoas 에서 제공하는 클래스로 RestAPI를 구성할댸
        *   해당 리소스와 연관이있는 링크를 포함할수있도록하는것 */
        List<EntityModel<UserDTO>> userAll = user.stream().map(users -> EntityModel.of(
                users,
                // withSelfRel() : 특정 리소스를 조회하는 URL 링크를 제공하며
                // 해당 링크를 제공함으로서, 특정 리소스에 대한 정보를 알 수 있다.
                linkTo(methodOn(HateoasController.class).findUserByNo(users.getNo())).withSelfRel(),
                // withRel() : 현재 리소스와 관련 된 관계를 나타낸다.
                // 즉,  user 한 명은 users 라는 리소스에 포함 된다.

                linkTo(methodOn(HateoasController.class).findAllUsers()).withRel("users")
        )
        ).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("users", userAll);

        ResponseMessage message = new ResponseMessage(200, "조회성공!!", response);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }



}
