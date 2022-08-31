package com.krt.captcha.controller;

import com.github.botaruibo.xvcode.generator.Generator;
import com.github.botaruibo.xvcode.generator.GifVCGenerator;
import com.github.botaruibo.xvcode.generator.PngVCGenerator;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.krt.captcha.dto.SliderImageDTO;
import com.krt.captcha.util.SliderImageUtils;
import com.krt.captcha.vo.SliderImageVO;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.util.IdUtils;
import com.krt.common.util.ShiroUtils;
import com.krt.common.validator.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月19日
 */
@Controller
public class CaptchaController extends BaseController {

    @Autowired
    private Producer producer;

    /**
     * 获取验证码
     *
     * @throws Exception 抛出异常
     */
    @GetMapping("captcha.jpg")
    public void captcha() throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        ServletOutputStream out = null;
        try {
            ShiroUtils.getSubject().getSession(true);
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            //保存到session
            ShiroUtils.setSessionAttribute(GlobalConstant.KAPTCHA_SESSION_KEY, text);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 获取png验证码
     *
     * @throws Exception 抛出异常
     */
    @GetMapping("captcha.png")
    public void captchaPng() throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        Generator generator = new PngVCGenerator(120, 40, 4);
        printCaptcha(generator);
    }

    /**
     * 获取gif验证码
     *
     * @throws Exception 抛出异常
     */
    @GetMapping("captcha.gif")
    public void captchaGif() throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/gif");
        Generator generator = new GifVCGenerator(120, 40, 4);
        printCaptcha(generator);

    }

    /**
     * 输出验证码
     *
     * @param generator
     * @throws IOException
     */
    public void printCaptcha(Generator generator) throws IOException {
        ServletOutputStream out = null;
        try {
            ShiroUtils.getSubject().getSession(true);
            out = response.getOutputStream();
            generator.write2out(out);
            //保存到session
            ShiroUtils.setSessionAttribute(GlobalConstant.KAPTCHA_SESSION_KEY, generator.text());
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 获取滑动验证码
     *
     * @return {@link ReturnBean}
     * @throws Exception
     */
    @GetMapping("sliderImage")
    @ResponseBody
    public ReturnBean<SliderImageVO> getSliderImageVO() throws Exception {
        SliderImageDTO sliderImageDTO = SliderImageUtils.getImaageDTO();
        //保存到session
        ShiroUtils.setSessionAttribute(GlobalConstant.KAPTCHA_SESSION_KEY, sliderImageDTO.getX());
        SliderImageVO sliderImageVO = new SliderImageVO();
        BeanUtils.copyProperties(sliderImageDTO, sliderImageVO);
        return ReturnBean.ok(sliderImageVO);
    }

    /**
     * 获取滑动验证码
     *
     * @return
     */
    @GetMapping("checkSliderImage")
    @ResponseBody
    public ReturnBean getSliderImageVO(Integer x) {
        if (x == null) {
            return ReturnBean.error("验证码错误");
        }
        Integer value = (Integer) ShiroUtils.getSessionAttribute(GlobalConstant.KAPTCHA_SESSION_KEY);
        if (Assert.isNull(value)) {
            return ReturnBean.error();
        }
        //坐标误差值
        int deviation = 5;
        if (Math.abs(x - value) > deviation) {
            //移除验证码
            ShiroUtils.getSession().removeAttribute(GlobalConstant.KAPTCHA_SESSION_KEY);
            return ReturnBean.error("验证码错误");
        } else {
            //移除验证码
            ShiroUtils.getSession().removeAttribute(GlobalConstant.KAPTCHA_SESSION_KEY);
            //保存ticket
            String ticket = IdUtils.getUUID();
            ShiroUtils.setSessionAttribute(GlobalConstant.KAPTCHA_SESSION_TICKET, ticket);
            return ReturnBean.ok(ticket);
        }
    }

    /**
     * 获取验证码
     *
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("captcha")
    public ModelAndView captchaApi() throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }
}
