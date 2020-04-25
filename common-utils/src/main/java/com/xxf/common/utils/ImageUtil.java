package com.xxf.common.utils;


import com.xxf.common.constant.ErrorCodeEnum;
import com.xxf.common.exception.ServiceException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @Description 图像处理<br>
 *              对图片进行压缩、水印、伸缩变换、透明处理、格式转换操作
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Slf4j
public class ImageUtil {
    /**
     * 水印格式：居中
     */
    public static final Integer WATERMARK_STYLE_CENTER = 10;

    /**
     * 水印格式：平铺
     */
    public static final Integer WATERMARK_STYLE_TILED = 20;

    /**
     * 水印间隔距离
     */
    public static final Integer WATERMARK_INTERVAL = 20;


    private static final Integer compressRatio = 8;

    /**
     * 功能描述:
     *
     * @param
     * @return
     * @auther kanst
     * @date 2019/4/8 10:41
     */
    private static int rotateAngleForPhoto(InputStream srcFile) {
        int angel = 0;
        try {

            Metadata metadata = ImageMetadataReader.readMetadata(srcFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (tag.getTagType() == ExifDirectoryBase.TAG_ORIENTATION) {
                        String description = tag.getDescription();
                        if (description.contains("90")) {
                            // 顺时针旋转90度
                            angel = 90;
                        } else if (description.contains("180")) {
                            // 顺时针旋转180度
                            angel = 180;
                        } else if (description.contains("270")) {
                            // 顺时针旋转270度
                            angel = 270;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return angel;
    }

    /**
     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians((double) angel), (double) src_width / 2, (double) src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt((double) (src.height * src.height + src.width * src.width)) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }


    private static BufferedImage getInputImage(MultipartFile file) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = (FileInputStream) file.getInputStream();
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            log.error("读取图片文件出错！" + e.getMessage());
        }
        return srcImage;
    }

    /**
     * 给图片添加文字水印
     *
     * @param waterMarkContent 文字水印内容
     * @param watermarkStyle   水印格式(10: 居中， 20：平铺)
     */
    public static InputStream setTextWaterMarkForImage(final MultipartFile file, String waterMarkContent, Integer watermarkStyle) throws Exception {

        int angle = rotateAngleForPhoto(file.getInputStream());
        log.info("angle:{}", angle);

        /* 旋转图片 */
        BufferedImage srcImg = getInputImage(file);
        if (srcImg == null) {
            throw new ServiceException(ErrorCodeEnum.ERROR);
        }
        //BufferedImage srcImg = ImageIO.read(srcImgFile);
        srcImg = Rotate(srcImg, angle);

        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);
        // 加水印
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SimSun", Font.BOLD, 40));
        g.rotate(Math.toRadians(15)); //水印旋转
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f)); //设置水印透明度

        //设置水印的坐标
        int textWidth = g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
        int textHight = g.getFontMetrics(g.getFont()).getHeight();
        if (WATERMARK_STYLE_TILED.equals(watermarkStyle)) {
            for (float y = 0; y < srcImgHeight; y += textHight * 3) {
                for (float x = 0; x < srcImgWidth; x += textWidth) {
                    g.drawString(waterMarkContent, x + y, y - x);
                }
            }
        } else {
            int x = (srcImgWidth - textWidth / 2) / 2;
            int y = (srcImgHeight - textHight * 4) / 2;
            g.drawString(waterMarkContent, x, y);
        }
        g.dispose();


        /*压缩图片*/
        BufferedImage newImg = new BufferedImage(srcImgWidth / compressRatio, srcImgHeight / compressRatio, BufferedImage.TYPE_INT_RGB);
        newImg.getGraphics().drawImage(bufImg.getScaledInstance(srcImgWidth / compressRatio, srcImgHeight / compressRatio, Image.SCALE_SMOOTH), 0, 0, null);

        /*返回图片的inputStream*/
        ByteArrayOutputStream osStream = new ByteArrayOutputStream();
        ImageIO.write(newImg, "png", osStream);
        //InputStream is = new ByteArrayInputStream(osStream.toByteArray());

        return new ByteArrayInputStream(osStream.toByteArray());

    }


    /**
     * 给图片添加图片水印
     *
     * @param srcImgPath     原图片位置
     * @param markImgPath    图片水印的位置
     * @param tarImgPath     加水印后图片位置
     * @param watermarkStyle 水印格式(10: 居中， 20：平铺)
     */
    public static void setImageWaterMarkForImage(String srcImgPath, String markImgPath, String tarImgPath, Integer watermarkStyle) throws Exception {
        java.awt.Image img = ImageIO.read(new File(srcImgPath));
        java.awt.Image mark = ImageIO.read(new File(markImgPath));

        int imgWidth = img.getWidth(null);
        int imgHeight = img.getHeight(null);
        BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.rotate(Math.toRadians(15)); //水印旋转
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.1f)); //设置水印透明度

        int waterWidth = mark.getWidth(null);
        int waterHight = mark.getHeight(null);
        if (WATERMARK_STYLE_TILED.equals(watermarkStyle)) {
            for (int y = 0; y < imgHeight; y += waterHight * 2) {
                for (int x = 0; x < imgWidth; x += waterWidth) {
                    g.drawImage(mark, x + y, y - x, null);
                }
            }
        } else {
            int x = (imgWidth - waterWidth / 2) / 2;
            int y = (imgHeight - waterHight * 3) / 2;
            g.drawImage(mark, x, y, null);
        }
        g.dispose();
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "jpg", outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }


    /**
     * 获取文件类型
     */
    public static String getFileType(String uri) throws IOException {
        Path path = Paths.get(uri);
        return Files.probeContentType(path);
    }


}