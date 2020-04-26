package com.xxf.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    /**
     * 订单编号
     */
    private Integer id;

    /**
     * 价格
     */
    private Double price;

    /**
     * 商品名称
     */
    private String name;
}