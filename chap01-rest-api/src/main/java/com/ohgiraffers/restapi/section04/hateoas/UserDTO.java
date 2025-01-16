package com.ohgiraffers.restapi.section04.hateoas;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private int no;
    private String id;
    private String pwd;
    private String name;
    private LocalDateTime enrollTime;

}
