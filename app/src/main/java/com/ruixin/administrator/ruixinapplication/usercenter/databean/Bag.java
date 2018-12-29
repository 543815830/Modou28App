package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/19.
 * 邮箱：543815830@qq.com
 * 红包的数据返回
 */

public class Bag {

    /**
     * status : 1
     * data : {"web_hbsx":"2.5","web_hbxe":"500000"}
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * web_hbsx : 2.5
         * web_hbxe : 500000
         */

        private String web_hbsx;
        private String web_hbxe;

        public String getWeb_hbsx() {
            return web_hbsx;
        }

        public void setWeb_hbsx(String web_hbsx) {
            this.web_hbsx = web_hbsx;
        }

        public String getWeb_hbxe() {
            return web_hbxe;
        }

        public void setWeb_hbxe(String web_hbxe) {
            this.web_hbxe = web_hbxe;
        }
    }
}
