package com.xxf.springcloud.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.xxf.springcloud.mapper.PaymentMapper;
import com.xxf.springcloud.entity.Payment;

@Service
public class PaymentService {

    @Resource
    private PaymentMapper paymentMapper;


    public int deleteByPrimaryKey(Integer id) {
        return paymentMapper.deleteByPrimaryKey(id);
    }


    public int insert(Payment record) {
        return paymentMapper.insert(record);
    }


    public int insertSelective(Payment record) {
        return paymentMapper.insertSelective(record);
    }


    public Payment selectByPrimaryKey(Integer id) {
        return paymentMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(Payment record) {
        return paymentMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Payment record) {
        return paymentMapper.updateByPrimaryKey(record);
    }

}
