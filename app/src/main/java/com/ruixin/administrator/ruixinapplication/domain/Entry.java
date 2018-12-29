package com.ruixin.administrator.ruixinapplication.domain;

/**
 * 作者：Created by ${李丽} on 2018/3/26.
 * 邮箱：543815830@qq.com
 * 返回数据中data为空的属性
 */

public class Entry {

    /**
     * status : 1
     * msg : 验证成功
     * data :
     */

    private int status;
    private String msg;
    private String xnb;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }  public String getXnb() {
        return xnb;
    }

    public void setXnb(String xnb) {
        this.xnb = xnb;
    }
}
