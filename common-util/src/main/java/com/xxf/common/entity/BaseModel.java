package com.xxf.common.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 描述：
 * 将返回的对象都解析成json格式
 */
public class BaseModel implements Serializable {

    @Override
    public String toString() {
        JSONObject json = (JSONObject) JSON.toJSON(this);
        return json.toString();
    }

}
