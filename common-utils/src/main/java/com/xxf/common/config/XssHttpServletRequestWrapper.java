package com.xxf.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return XssUtil.cleanXSS(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return XssUtil.cleanXSS(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = XssUtil.cleanXSS(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    /**
     * 主要是针对HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE 获取pathvalue的时候把原来的pathvalue经过xss过滤掉
     */
    @Override
    public Object getAttribute(String name) {
        // 获取pathvalue的值
        if (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE.equals(name)) {
            Map uriTemplateVars = (Map) super.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (Objects.isNull(uriTemplateVars)) {
                return uriTemplateVars;
            }
            Map newMap = new LinkedHashMap<>();
            uriTemplateVars.forEach((key, value) -> {
                if (value instanceof String) {
                    newMap.put(key, XssUtil.cleanXSS((String) value));
                } else {
                    newMap.put(key, value);

                }
            });
            return newMap;
        } else {
            return super.getAttribute(name);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String str = getRequestBody(super.getInputStream());
        Map<String, Object> map = JSON.parseObject(str, Map.class);
        Map<String, Object> resultMap = new HashMap<>(map.size());
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (map.get(key) instanceof String) {
                resultMap.put(key, XssUtil.cleanXSS(val.toString()));
            } else {
                resultMap.put(key, val);
            }
        }
        str = JSON.toJSONString(resultMap);
        final ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }


    private String getRequestBody(InputStream stream) {
        String line = "";
        StringBuilder body = new StringBuilder();

        // 读取POST提交的数据内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
        try {
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
//            e.printStackTrace();
            log.error("getRequest Body failure, {}", e.getMessage());
        }
        return body.toString();
    }

}

