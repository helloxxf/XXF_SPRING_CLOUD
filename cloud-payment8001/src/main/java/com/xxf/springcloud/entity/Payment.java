package com.xxf.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 金额
     */
    private String serial;
}