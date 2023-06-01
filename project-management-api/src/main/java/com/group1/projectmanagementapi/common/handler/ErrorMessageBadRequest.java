package com.group1.projectmanagementapi.common.handler;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageBadRequest {
    private int statusCode;
    private Date timestamp;
    private Map<String, String> message;
    private String description;
}
