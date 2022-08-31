package com.krt.captcha.util;

import com.krt.captcha.dto.SliderImageDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;


/**
 * 滑块验证工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年03月02日
 */
@Slf4j
@Data
public class SliderImageUtils {
    /**
     * 源文件宽度
     */
    private static int ORI_WIDTH = 310;
    /**
     * 源文件高度
     */
    private static int ORI_HEIGHT = 150;
    /**
     * 抠图坐标x
     */
    private static int X = 62;
    /**
     * 抠图坐标y
     */
    private static int Y;
    /**
     * 模板图宽度
     */
    private static int WIDTH;
    /**
     * 模板图高度
     */
    private static int HEIGHT;

    /**
     * 根据模板切图
     *
     * @param templateFile 模板小图流
     * @param oriis        待抠大图流
     * @param templateType 抠图文件文件类型
     * @param targetType   目标文件类型
     * @return {@link SliderImageDTO}
     * @throws Exception
     */
    public static SliderImageDTO pictureTemplatesCut(InputStream templateFile, InputStream oriis, String templateType, String targetType) throws Exception {
        SliderImageDTO imageDTO = new SliderImageDTO();
        //解決oriis 2次读取问题
        ByteArrayOutputStream baosOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = oriis.read(buffer)) > -1) {
            baosOutputStream.write(buffer, 0, len);
        }
        baosOutputStream.flush();
        InputStream oriis1 = new ByteArrayInputStream(baosOutputStream.toByteArray());
        InputStream oriis2 = new ByteArrayInputStream(baosOutputStream.toByteArray());
        baosOutputStream.close();
        // 文件类型
        String templateFiletype = templateType;
        String oriFiletype = targetType;
        if (templateFiletype == null || "".equals(templateFiletype)
                || targetType == null || "".equals(targetType)) {
            log.error("文件类型未填写");
            throw new RuntimeException("file type is empty");
        }
        // 模板图
        BufferedImage imageTemplate = ImageIO.read(templateFile);
        WIDTH = imageTemplate.getWidth();
        HEIGHT = imageTemplate.getHeight();
        log.debug("模板小图WIDTH:{}", WIDTH);
        log.debug("模板小图HEIGHT:{}", HEIGHT);
        generateCutoutCoordinates();
        // 最终图像
        BufferedImage newImage = new BufferedImage(WIDTH, HEIGHT, imageTemplate.getType());
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.white);
        // 获取感兴趣的目标区域
        BufferedImage targetImageNoDeal = getTargetArea(X, Y, WIDTH, HEIGHT, oriis1, oriFiletype);
        log.debug("目标区域X:{}", X);
        log.debug("目标区域Y:{}", Y);
        imageDTO.setX(X);
        // 根据模板图片抠图
        newImage = DealCutPictureByTemplate(targetImageNoDeal, imageTemplate, newImage);
        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();
        //新建流。
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        ImageIO.write(newImage, "png", os);
        byte[] smallImgData = os.toByteArray();
        // 源图生成遮罩
        BufferedImage oriImage = ImageIO.read(oriis2);
        byte[] bigImgData = DealOriPictureByTemplate(oriImage, imageTemplate, X, Y);
        String smallImg = org.apache.commons.codec.binary.Base64.encodeBase64String(smallImgData);
        String bigImg = org.apache.commons.codec.binary.Base64.encodeBase64String(bigImgData);
        imageDTO.setBigImg("data:image/png;base64," + bigImg);
        imageDTO.setSmallImg("data:image/png;base64," + smallImg);

        //一下测试生成图片
//        FileOutputStream bigImageFout = new FileOutputStream("C:/big.png");
//        bigImageFout.write(bigImgData);
//        bigImageFout.close();
//        FileOutputStream smallImageFout = new FileOutputStream("C:/small.png");
//        smallImageFout.write(smallImgData);
//        smallImageFout.close();
        return imageDTO;
    }

    /**
     * 抠图后原图生成
     *
     * @param oriImage      兴趣的目标区域
     * @param templateImage
     * @param x             抠图x轴
     * @param y             抠图x轴
     * @return
     * @throws Exception
     */
    private static byte[] DealOriPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x, int y) throws Exception {
        // 源文件备份图像矩阵 支持alpha通道的rgb图像
        BufferedImage ori_copy_image = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);
        //copy 源图做不透明处理
        for (int i = 0; i < oriImageData.length; i++) {
            for (int j = 0; j < oriImageData[0].length; j++) {
                int rgb = oriImage.getRGB(i, j);
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                //无透明处理
                rgb = r + (g << 8) + (b << 16) + (255 << 24);
                ori_copy_image.setRGB(i, j, rgb);
            }
        }
        for (int i = 0; i < templateImageData.length; i++) {
            for (int j = 0; j < templateImageData[0].length - 5; j++) {
                int rgb = templateImage.getRGB(i, j);
                //对源文件备份图像(x+i,y+j)坐标点变色
                if (rgb != 16777215 && rgb <= 0) {
                    ori_copy_image.setRGB(x + i, y + j, 0xff333333);
                }
            }
        }
        ////新建流。
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        ImageIO.write(ori_copy_image, "png", os);
        //从流中获取数据数组。
        return os.toByteArray();
    }


    /**
     * 根据模板图片抠图
     *
     * @param oriImage      需要抠得矩形区域
     * @param templateImage 抠图模板
     * @param targetImage   透明画板
     * @return
     */

    private static BufferedImage DealCutPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, BufferedImage targetImage) {
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);
        // 模板图像宽度
        for (int i = 0; i < templateImageData.length; i++) {
            // 模板图片高度
            for (int j = 0; j < templateImageData[i].length; j++) {
                // 如果模板图像当前像素点不是白色 copy源文件信息到目标图片中
                int rgb = templateImageData[i][j];
                if (rgb != 16777215 && rgb <= 0) {
                    targetImage.setRGB(i, j, oriImageData[i][j]);
                }
            }
        }
        return targetImage;
    }


    /**
     * 获取目标区域
     *
     * @param x            随机切图坐标x轴位置
     * @param y            随机切图坐标y轴位置
     * @param targetWidth  切图后目标宽度
     * @param targetHeight 切图后目标高度
     * @param ois          源文件输入流
     * @return
     * @throws Exception
     */
    private static BufferedImage getTargetArea(int x, int y, int targetWidth, int targetHeight, InputStream ois, String filetype) throws Exception {
        Iterator<ImageReader> imageReaderList = ImageIO.getImageReadersByFormatName(filetype);
        ImageReader imageReader = imageReaderList.next();
        // 获取图片流
        ImageInputStream iis = ImageIO.createImageInputStream(ois);
        // 输入源中的图像将只按顺序读取
        imageReader.setInput(iis, true);
        ImageReadParam param = imageReader.getDefaultReadParam();
        Rectangle rec = new Rectangle(x, y, targetWidth, targetHeight);
        param.setSourceRegion(rec);
        BufferedImage targetImage = imageReader.read(0, param);
        return targetImage;
    }

    /**
     * 生成图像矩阵
     *
     * @param bimg 图片
     * @return
     */
    private static int[][] getData(BufferedImage bimg) {
        int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
        for (int i = 0; i < bimg.getWidth(); i++) {
            for (int j = 0; j < bimg.getHeight(); j++) {
                data[i][j] = bimg.getRGB(i, j);
            }
        }
        return data;
    }

    /**
     * 随机生成抠图坐标
     */
    private static void generateCutoutCoordinates() {
        Random random = new Random();
        int widthDifference = ORI_WIDTH - WIDTH;
        int heightDifference = ORI_HEIGHT - HEIGHT;
        if (widthDifference <= 0) {
            X = 5;
        } else {
            //优化兴趣地址 小图62 大图 310  抠图范围 62 -- （310-62） 248  为显示效果更好 取130 -240
            X = random.nextInt(110) + 130;
        }
        if (heightDifference <= 0) {
            Y = 5;
        } else {
            Y = random.nextInt(ORI_HEIGHT - HEIGHT) + 5;
        }
    }

    /**
     * 获取ImageDTO
     *
     * @return
     * @throws Exception
     */
    public static SliderImageDTO getImaageDTO() throws Exception {
        Random random = new Random();
        //模板小图编号
        int templateNo = random.nextInt(3) + 1;
        //原图id编号
        int targetNo = random.nextInt(20) + 1;
        ClassPathResource templateResource = new ClassPathResource("static/templates/" + templateNo + ".png");
        InputStream templateFileStream = templateResource.getInputStream();
        //模板小图
        ClassPathResource targetResource = new ClassPathResource("static/targets/" + targetNo + ".jpg");
        InputStream targetFileStream = targetResource.getInputStream();
        //获取 大小图
        SliderImageDTO dto = SliderImageUtils.pictureTemplatesCut(templateFileStream, targetFileStream, "png", "jpg");
        templateFileStream.close();
        targetFileStream.close();
        return dto;
    }
}


