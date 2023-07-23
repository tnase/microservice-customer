package com.tmsService.customer.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVo {
    private Object vo;
    private String errorsMsg;
    private String warningMsg;
    private String successMsg;
}
