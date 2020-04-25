package com.xxf.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Description JSON工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Slf4j
public final class JSONUtil {
    private JSONUtil() {
    }

    //json to Map<String,String>
    public static <T extends Map<String, ?>> Map<List<String>, String> json2Map(T properties) {
        Map<List<String>, String> map = new HashMap<>();
        properties.forEach((key, value) -> {
            if (value instanceof Map) {
                json2Map((T) value).forEach((childKey, childValue) -> {
                    LinkedList<String> keyList = new LinkedList<>(childKey);
                    keyList.addFirst(key);
                    map.put(keyList, childValue);
                });
            } else if (value != null) {
                List<String> keyList = new LinkedList<>();
                keyList.add(key);
                map.put(keyList, value.toString());
            }
        });
        return map;
    }

    //选择性更新json
    public static JSONObject selectiveUpdate(JSONObject old, JSONObject update) {
        update.forEach((key, value) -> {
            if (value instanceof JSONObject && old.get(key) instanceof JSONObject) {
                //若均为json节点则递归调用
                selectiveUpdate((JSONObject) old.get(key), (JSONObject) value);
            } else if ("".equals(value)) {
                //移除字段
                old.remove(key);
            } else {
                //覆盖字段
                old.put(key, value);
            }
        });
        return old;
    }

    //List<String> -> String
    public static String keyList2str(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(separator);
            }
        }
        return sb.toString().replace("\'", "").replace("\"", "");
    }

    //解析deviceConf 对应查询条件 # 符号mybatis无法正常解析
    public static List<String> jsonWhereCondition(String jsonColumnName, JSONObject input) {
        if (input == null || input.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> output = new LinkedList<>();
        json2Map(input).forEach((keys, value) -> {
            String key = JSONUtil.keyList2str(keys, ',');
            if (StringUtils.isEmpty(value)) {
                //判断字段是否存在
                output.add(String.format(" and %s #>> '{%s}' is not null ", jsonColumnName, replace(key)));
            } else {
                //判断字段值是否匹配
                output.add(String.format(" and %s #>> '{%s}' = '%s' ", jsonColumnName, replace(key), replace(value)));
            }
        });
        log.info("jsonWhereCondition output: {}", output);
        return output;
    }

    //替换sql特殊字符
    private static String replace(String str) {
        return str.replace("'", "''");
    }
}