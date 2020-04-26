package com.xxf.springcloud.controller;

import com.xxf.common.entity.ResultBean;
import com.xxf.springcloud.entity.Payment;
import com.xxf.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 新增支付订单
     * @param payment
     * @return
     */
    @GetMapping("/addPayment")
    public ResultBean addPayment(@RequestParam Payment payment) {
        return paymentService.insert(payment);
    }
}
