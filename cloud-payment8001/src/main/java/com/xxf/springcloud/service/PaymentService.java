package com.xxf.springcloud.service;

import com.xxf.common.entity.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.xxf.springcloud.mapper.PaymentMapper;
import com.xxf.springcloud.entity.Payment;

@Service
@Slf4j
public class PaymentService {

    @Resource
    private PaymentMapper paymentMapper;


    public int deleteByPrimaryKey(Integer id) {
        return paymentMapper.deleteByPrimaryKey(id);
    }


    /**
     * 新增
     *
     * @param record
     * @return
     */
    public ResultBean insert(Payment record) {
        log.info("【PaymentService】insert param:{}", record);
        paymentMapper.insert(record);
        return new ResultBean<>();
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

