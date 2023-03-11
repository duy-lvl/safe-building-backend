package com.safepass.safebuilding.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseObject implements Serializable {
    private String status;
    private String message;
    private Pagination pagination;
    private Object data;
}
