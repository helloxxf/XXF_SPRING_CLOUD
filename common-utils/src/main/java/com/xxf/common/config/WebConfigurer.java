package com.xxf.common.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @Description
 * @Date Created in 2020/3/19 17:13
 * @User xxf
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {


    //设置通过fastJSON作为json/对象转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while(iterator.hasNext()){
            HttpMessageConverter<?> converter = iterator.next();
            if(converter instanceof MappingJackson2HttpMessageConverter){
                iterator.remove();
            }
        }
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(SerializerFeature.NotWriteDefaultValue, // List类型字段为null时输出[]而非null
//                SerializerFeature.WriteMapNullValue, // 显示空字段
//                SerializerFeature.WriteNullStringAsEmpty, // 字符串类型字段为null时间输出""而非null
//                SerializerFeature.WriteNullBooleanAsFalse, // Boolean类型字段为null时输出false而null
//                SerializerFeature.PrettyFormat, // 美化json输出，否则会作为整行输出
//                SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null,输出为0,而非null
//                SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteDateUseDateFormat, // 时间格式yyyy-MM-dd HH: mm: ss
                SerializerFeature.DisableCircularReferenceDetect);  // 禁用循环引用检测
        converter.setFastJsonConfig(config);
        converters.add(converter);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }




}
