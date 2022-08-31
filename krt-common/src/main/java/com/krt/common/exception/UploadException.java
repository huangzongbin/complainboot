package com.krt.common.exception;

import com.krt.common.bean.ReturnBean;

/**
 * 上传异常
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月12日
 */
public class UploadException extends RuntimeException {

    protected ReturnBean returnBean;

    public UploadException(ReturnBean returnBean) {
        super(returnBean.getMsg());
        this.returnBean = returnBean;
    }

    public UploadException(String msg, Throwable e) {
        super(msg, e);
        this.returnBean = ReturnBean.error(msg);
    }

    public UploadException(ReturnBean returnBean, Throwable e) {
        super(returnBean.getMsg(), e);
        this.returnBean = returnBean;
    }

    public ReturnBean getReturnBean() {
        return returnBean;
    }

    public void setReturnBean(ReturnBean returnBean) {
        this.returnBean = returnBean;
    }
}
