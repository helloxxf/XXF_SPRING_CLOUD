package com.xxf.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.image.BufferedImage;
/**
 * 描述：
 * 图片验证码
 * @Description
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageResponse {
    private String answer;
    private BufferedImage image;

    public ImageResponse(String answer) {
        this.answer = answer;
        this.image = null;
    }


}
