package com.krt.common.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;

/**
 * 图片处理工具类，主要压缩，添加logo等
 * <p>主要列举Thumbnails用法</p>
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年8月24日
 */
@Slf4j
public class ImageUtils2 {

    /**
     * 使用给定的图片生成指定大小的图片(根据高宽自适应 不会拉伸)
     */
    public static void generateFixedSizeImage() throws IOException {
        Thumbnails.of("")
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
     * 裁剪图片区域
     */
    public static void generateCutImage(int x, int y, int width, int height, String preFile, String outFile) throws IOException {
        Thumbnails.of(preFile)
                .sourceRegion(x, y, width, height)
//                .sourceRegion(Positions.CENTER, width, height)
                .scale(1)
//                .keepAspectRatio(true)
                .toFile(outFile);
    }

    /**
     * 裁剪图片区域
     */
    public static void generateCutImage2(int width, int height, String inputPath, String outPath) throws IOException {
        // 图片读取路径
        // 图片输出路径
        // 1.得到图片的输入流
        FileInputStream input = new FileInputStream(inputPath);
        // 2.用工具类ImageIO得到BufferedImage对象,将图片信息放入缓存区
        BufferedImage image = ImageIO.read(input);
        // 3.设置截图图片的(x坐标,y坐标,width宽,height高)信息,并返回截切的新图片,存入缓存区
        System.err.println(image.getWidth());
        System.err.println(image.getHeight());
        BufferedImage result = image.getSubimage(0, 0, image.getWidth(), 300);
        // 4.得到图片的输出流
        FileOutputStream out = new FileOutputStream(outPath);
        // 5. 将缓存区的图片,利用ImageIO工具类输出到指定位置.
        ImageIO.write(result, "jpg", out);
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

    /**
     * 根据指定大小和指定精度压缩图片
     *
     * @param srcPath     源图片地址
     * @param desPath     目标图片地址
     * @param desFileSize 指定图片大小，单位kb
     * @param accuracy    精度，递归压缩的比率，建议小于0.9
     * @return
     */
    public static String commpressPicForScale(String srcPath, String desPath, long desFileSize, double accuracy) {
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(srcPath)) {
            return null;
        }
        if (!new File(srcPath).exists()) {
            return null;
        }
        try {
            // 1、先转换成jpg
            Thumbnails.of(srcPath).scale(1f).toFile(desPath);
            // 递归压缩，直到目标文件大小小于desFileSize
            commpressPicCycle(desPath, desFileSize, accuracy);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return desPath;
    }

    /**
     *      * 根据指定大小和指定精度压缩图片
     *      *
     *      * @param srcPath 源图片地址
     *      * @param desPath 目标图片地址
     *      * @param desFileSize 指定图片大小，单位kb
     *      * @param accuracy 精度，递归压缩的比率，建议小于0.9
     */
    public static void commpressPicCycle(String desPath, long desFileSize, double accuracy) throws IOException {
        File srcFileJPG = new File(desPath);
        long srcFileSizeJPG = srcFileJPG.length();
        // 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
        if (srcFileSizeJPG <= desFileSize * 1024) {
            return;
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(srcFileJPG);
        int sacWidth = bim.getWidth();
        int sacHeight = bim.getHeight();
        int desWidth = new BigDecimal(sacWidth).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(sacHeight).multiply(
                new BigDecimal(accuracy)).intValue();

        Thumbnails.of(desPath).size(desWidth, desHeight)
                .outputQuality(accuracy).toFile(desPath);
        commpressPicCycle(desPath, desFileSize, accuracy);
    }

    public static void main(String[] args) throws IOException {
        //pdf 转图片
//        PdfUtils2.pdf2png(new File("F:\\mysql\\image2\\admin.pdf"),"F:\\mysql\\image2\\admincc.png","png");
//        PdfToImage.generateBookImage("F:\\mysql\\image2\\admin.pdf", null, "png");
//        commpressPicCycle("C:\\Users\\jsb\\Pictures\\ttt.jpg", 1000L, 0.9);
        //截取图片
        String preFile = "F:\\mysql\\image3\\pdf\\rental_pay_1.png";
        String outFile = "F:\\mysql\\image3\\pdf\\rental_pay_1cut.png";


        int firstHeight = 452;
        int secondHeight = 396;
        int thirdHeight = 466;

        int firstY = 0;
        int secondY = firstY + firstHeight + 3;
        int thirdY = secondY + secondHeight + 3;

        //第一张
//        generateCutImage(0,0,3333, 452, preFile, outFile);
        //第二张
//        generateCutImage(0,455,3333, 396, preFile, outFile);
        //第三张
        generateCutImage(0, thirdY, 3333, thirdHeight, preFile, outFile);
//        generateCutImage(800,300,preFile,outFile);
    }
}
