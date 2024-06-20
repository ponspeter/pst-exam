package com.pst.exam.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String field;
    private String operation;
    private String param1;
    private String param2;
}
