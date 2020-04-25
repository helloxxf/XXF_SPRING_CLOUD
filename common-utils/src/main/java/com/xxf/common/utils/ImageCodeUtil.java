package com.xxf.common.utils;

import com.xxf.common.entity.ImageResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * @Description  图片验证码工具类
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
public class ImageCodeUtil {
    private int imageWidth;
    private int imageHeight;
    private VerificationCodeType type;
    private String answer;
    private static final int TWO = 2;
    private static final int SIZE = 18;
    private static final int HUNDRED = 100;
    private SecureRandom random = new SecureRandom();

    private static final Double CIR = 3.141592653589793D;
    private static final Double THETA = 180.0D;
    private static final int THETA_X = 7;
    private static final Double THETA_Y = 20.0D;
    private static final int RATIO = 50;
    private static final int Y = 20;
    private static final int INCREASE = 15;

    public ImageCodeUtil(int imageWidth, int imageHeight, VerificationCodeType type) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.type = type;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public VerificationCodeType getType() {
        return this.type;
    }

    public void setType(VerificationCodeType type) {
        this.type = type;
    }

    public ImageResponse getVerificationCode() {
        BufferedImage bi = new BufferedImage(this.imageWidth, this.imageHeight, 1);
        Graphics g = bi.getGraphics();
        setBackGround(g);
        drawRandomLine(g);
        drawRandomPoint(g);
        g.setColor(Color.BLUE);
        g.setFont(new Font("宋体", 1, SIZE));
        this.answer = StringRandom.getRandomChar(this.type);
        drawChars((Graphics2D) g, this.answer);
        return new ImageResponse(this.answer, bi);
    }

    /**
     * 验证
     * @param o //
     * @return //
     */
    @SuppressWarnings("unchecked")
    public boolean checkVerificationCode(Object o) {
        return (o != null) && (o instanceof String) && (((String) o).equalsIgnoreCase(this.answer));
    }

    /**
     * 背景
     * @param g 画图
     */
    private void setBackGround(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.imageWidth, this.imageHeight);
    }

    /**
     * 边框
     * @param g 画图
     */
    @SuppressWarnings("unchecked")
    private void setBorder(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(1, 1, this.imageWidth - TWO, this.imageHeight - TWO);
    }

    /**
     * 画干扰线
     * @param g 画图
     */
    private void drawRandomLine(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < TWO; i++) {
            int x1 = random.nextInt(this.imageWidth);
            int y1 = random.nextInt(this.imageHeight);
            int x2 = random.nextInt(this.imageWidth);
            int y2 = random.nextInt(this.imageHeight);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 画干扰点
     * @param g 画图
     */
    private void drawRandomPoint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < HUNDRED; i++) {
            int x1 = random.nextInt(this.imageWidth);
            int y1 = random.nextInt(this.imageHeight);
            int x2 = random.nextInt(TWO);
            int y2 = random.nextInt(TWO);
            g.drawOval(x1, y1, x2, y2);
        }
    }

    /**
     * 画入字符
     * @param g 画图
     * @param chars 字符
     */
    private void drawChars(Graphics2D g, String chars) {
        String ch = "";
        int x = THETA_X;
        for (int i = 0; i < chars.length(); i++) {
            int degree = random.nextInt() % RATIO;
            ch = chars.substring(i, i + 1);
            g.rotate(degree * CIR / THETA, x, THETA_Y);
            g.drawString(ch, x, Y);
            g.rotate(-degree * CIR / THETA, x, THETA_Y);
            x += INCREASE;
        }
    }

}
