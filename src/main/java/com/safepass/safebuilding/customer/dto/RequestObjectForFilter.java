package com.safepass.safebuilding.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestObjectForFilter {
    private int page;
    private int size;
    private String searchKey;
    private String sortBy;
    private String order;

}
