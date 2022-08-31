package com.krt.common.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.common.exception.UploadException;
import com.krt.common.util.PatternUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

/**
 * 字段校验
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
public class Assert {

    /**************************************返回true || false**********************************************/

    /**
     * 是空字符串
     *
     * @param str
     */
    public static boolean isBlank(String str) {
        if (StringUtils.isBlank(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是null
     *
     * @param object
     */
    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是空字符串或null
     *
     * @param object
     */
    public static boolean isEmpty(Object object) {
        boolean flag = (null == object || (object instanceof String && StringUtils.isBlank((String) object)));
        return flag;
    }

    /**
     * 验证是否为给定长度范围的英文字母 、数字和下划线
     *
     * @param value
     * @param min   最小长度
     * @param max   最大长度
     */
    public static boolean isStrLength(String value, int min, int max) {
        String reg = "^\\w{" + min + "," + max + "}$";
        if (min < 0) {
            min = 0;
        }
        if (max <= 0) {
            reg = "^\\w{" + min + ",}$";
        }
        return validateMatchRegex(reg, value);
    }

    /**
     * 验证是否为数字
     *
     * @param value
     */
    public static boolean isNumbers(String value) {
        return validateMatchRegex(PatternUtils.NUMBERS, value);
    }

    /**
     * 验证手机号码
     *
     * @param value
     */
    public static boolean isMobile(String value) {
        return validateMatchRegex(PatternUtils.MOBILE, value);
    }

    /**
     * 验证邮箱
     *
     * @param value
     */
    public static boolean isEmail(String value) {
        return validateMatchRegex(PatternUtils.EMAIL, value);
    }

    /**
     * 验证车牌
     *
     * @param value
     */
    public static boolean isPlateNumber(String value) {
        return validateMatchRegex(PatternUtils.PLATE_NUMBER, value);
    }

    /**
     * 验证身份证
     *
     * @param value
     */
    public static boolean isCitizenId(String value) {
        return validateMatchRegex(PatternUtils.CITIZEN_ID, value);
    }

    /**
     * 通过正则表达式验证
     *
     * @param regex 正则表达式
     * @param value
     */
    public static boolean validateMatchRegex(String regex, String value) {
        if (!isEmpty(value)) {
            return Pattern.matches(regex, value);
        } else {
            return false;
        }
    }


    /**************************************抛出异常**********************************************/

    /**
     * 是空字符串
     *
     * @param str
     * @param message
     */
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new KrtException(ReturnBean.paramError(message));
        }
    }

    /**
     * 判断是不是null
     *
     * @param object
     * @param message
     */
    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new KrtException(ReturnBean.paramError(message));
        }
    }

    /**
     * 判断不是null
     *
     * @param object
     * @param message
     */
    public static void isNotNull(Object object, String message) {
        if (object != null) {
            throw new KrtException(ReturnBean.paramError(message));
        }
    }

    /**
     * 判断是不是空字符串或null
     *
     * @param object
     * @param message
     */
    public static void isEmpty(Object object, String message) {
        boolean flag = (null == object || (object instanceof String && StringUtils.isBlank((String) object)));
        if (flag) {
            throw new KrtException(ReturnBean.paramError(message));
        }
    }

    /**
     * 验证是否为给定长度范围的英文字母 、数字和下划线
     *
     * @param value
     * @param min     最小长度
     * @param max     最大长度
     * @param message
     */
    public static void isStrLength(String value, int min, int max, String message) {
        String reg = "^\\w{" + min + "," + max + "}$";
        if (min < 0) {
            min = 0;
        }
        if (max <= 0) {
            reg = "^\\w{" + min + ",}$";
        }
        validateMatchRegex(reg, value, message);
    }

    /**
     * 验证是否为数字
     *
     * @param value
     * @param message
     */
    public static void isNumbers(String value, String message) {
        validateMatchRegex(PatternUtils.NUMBERS, value, message);
    }

    /**
     * 验证手机号码
     *
     * @param value
     * @param message
     */
    public static void isMobile(String value, String message) {
        validateMatchRegex(PatternUtils.MOBILE, value, message);
    }

    /**
     * 验证邮箱
     *
     * @param value
     * @param message
     */
    public static void isEmail(String value, String message) {
        validateMatchRegex(PatternUtils.EMAIL, value, message);
    }

    /**
     * 验证车牌
     *
     * @param value
     * @param message
     */
    public static void isPlateNumber(String value, String message) {
        validateMatchRegex(PatternUtils.PLATE_NUMBER, value, message);
    }

    /**
     * 验证身份证
     *
     * @param value
     * @param message
     */
    public static void isCitizenId(String value, String message) {
        validateMatchRegex(PatternUtils.CITIZEN_ID, value, message);
    }

    /**
     * 通过正则表达式验证
     *
     * @param regex   正则表达式
     * @param value
     * @param message
     */
    public static void validateMatchRegex(String regex, String value, String message) {
        isEmpty(value, message);
        if (false == Pattern.matches(regex, value)) {
            throw new KrtException(ReturnBean.paramError(message));
        }
    }


    /**
     * 检测文件是否是excel
     *
     * @param file 文件
     */
    public static void isExcel(MultipartFile file) {
        String excel2003 = "xls";
        String excel2007 = "xlsx";
        if (file == null || file.isEmpty()) {
            throw new UploadException(ReturnBean.paramError("上传excel不能为空"));
        }
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!excel2003.equalsIgnoreCase(fileExt) && !excel2007.equalsIgnoreCase(fileExt)) {
            throw new UploadException(ReturnBean.paramError("文件类型不合法"));
        }
    }
}
