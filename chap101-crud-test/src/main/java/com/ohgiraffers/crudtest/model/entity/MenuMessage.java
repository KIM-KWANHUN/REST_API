package com.ohgiraffers.crudtest.model.entity;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MenuMessage {

    private int messageCode;
    private String message;
    private Map<String, Object> messageResults;
}
