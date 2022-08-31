package com.krt.common.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片处理工具类，主要压缩，添加logo等
 * <p>主要列举Thumbnails用法</p>
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年8月24日
 */
@Slf4j
public class ImageUtils {

    /**
     * 使用给定的图片生成指定大小的图片(根据高宽自适应 不会拉伸)
     */
    public static void generateFixedSizeImage() throws IOException {
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\18.png")
                .size(500, 500)
                .toFile("C:\\Users\\Administrator\\Desktop\\newmeinv.png");

    }

    /**
     * 裁剪图片中心400*400的区域
     */
    public static void generateCutImage() throws IOException {
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\18.png")
                .sourceRegion(Positions.CENTER, 400, 400)
                .size(200, 200)
                .keepAspectRatio(false)
                .toFile("C:\\Users\\Administrator\\Desktop\\18_cut.png");
    }

    /**
     * 对原图加水印,然后顺时针旋转90度,最后压缩为80%保存
     */
    public static void generateRotationWatermark() throws IOException {
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\18.png")
                // 缩放大小
                .size(1600, 1600)
                // 顺时针旋转90度
                .rotate(90)
                //水印位于右下角,半透明
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\logo.jpg")), 1f)
                // 图片压缩80%质量
                .outputQuality(0.8)
                .toFile("C:\\Users\\Administrator\\Desktop\\18_new.jpg");
    }

    /**
     * 转换图片格式,将流写入到输出流
     */
    public static void generateOutputstream() throws IOException {
        OutputStream outputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\20.jpg");
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\18.png")
                .size(500, 500)
                // 转换格式
                .outputFormat("jpg")
                // 写入输出流
                .toOutputStream(outputStream);
    }

    /**
     * 按比例缩放图片
     */
    public static void generateScale() throws IOException {
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\20.jpg").
                scalingMode(ScalingMode.BICUBIC)
                // 图片缩放80%, 不能和size()一起使用
                .scale(0.8)
                // 图片质量压缩80%
                .outputQuality(0.8)
                .toFile("C:\\Users\\Administrator\\Desktop\\21.jpg");
    }

    /**
     * 生成缩略图到指定的目录
     */
    public static void generateThumbnail2Directory() throws IOException {
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\20.jpg", "C:\\Users\\Administrator\\Desktop\\18.png")
                // 图片缩放80%, 不能和size()一起使用
                .scale(0.8)
                //指定的目录一定要存在,否则报错
                .toFiles(new File("C:\\Users\\Administrator\\Desktop\\new"), Rename.NO_CHANGE);

    }

    /**
     * 将指定目录下所有图片生成缩略图
     */
    public static void generateDirectoryThumbnail() throws IOException {
        Thumbnails.of(new File("C:\\Users\\Administrator\\Desktop\\new").listFiles())
                .scale(0.8)
                .toFiles(new File("C:\\Users\\Administrator\\Desktop\\new"), Rename.SUFFIX_HYPHEN_THUMBNAIL);

    }

    public static void main(String[] args) throws IOException {
        
        //使用给定的图片生成指定大小的图片
        generateFixedSizeImage();

        //对原图加水印,然后顺时针旋转90度,最后压缩为80%保存
        generateRotationWatermark();

        //转换图片格式,将流写入到输出流
        generateOutputstream();

        //按比例缩放图片
        generateScale();

        //生成缩略图到指定的目录
        generateThumbnail2Directory();

        //将指定目录下所有图片生成缩略图
        generateDirectoryThumbnail();

        //切割图片
        generateCutImage();
    }
}
