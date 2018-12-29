package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/19.
 * 邮箱：543815830@qq.com
 */

public class Relief {

    /**
     * status : 1
     * data : {"web_jjv0":"50","web_jjv1":"60","web_jjv2":"70","web_jjv3":"100","web_jjv4":"120","web_jjv5":"150","web_jjv6":"200","jiujitime":"10"}
     */

    private int status;
    private String msg;
    private DataBean data;

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
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * web_jjv0 : 50
         * web_jjv1 : 60
         * web_jjv2 : 70
         * web_jjv3 : 100
         * web_jjv4 : 120
         * web_jjv5 : 150
         * web_jjv6 : 200
         * jiujitime : 10
         */

        private String web_jjv0;
        private String web_jjv1;
        private String web_jjv2;
        private String web_jjv3;
        private String web_jjv4;
        private String web_jjv5;
        private String web_jjv6;
        private String jiujitime;

        public String getWeb_jjv0() {
            return web_jjv0;
        }

        public void setWeb_jjv0(String web_jjv0) {
            this.web_jjv0 = web_jjv0;
        }

        public String getWeb_jjv1() {
            return web_jjv1;
        }

        public void setWeb_jjv1(String web_jjv1) {
            this.web_jjv1 = web_jjv1;
        }

        public String getWeb_jjv2() {
            return web_jjv2;
        }

        public void setWeb_jjv2(String web_jjv2) {
            this.web_jjv2 = web_jjv2;
        }

        public String getWeb_jjv3() {
            return web_jjv3;
        }

        public void setWeb_jjv3(String web_jjv3) {
            this.web_jjv3 = web_jjv3;
        }

        public String getWeb_jjv4() {
            return web_jjv4;
        }

        public void setWeb_jjv4(String web_jjv4) {
            this.web_jjv4 = web_jjv4;
        }

        public String getWeb_jjv5() {
            return web_jjv5;
        }

        public void setWeb_jjv5(String web_jjv5) {
            this.web_jjv5 = web_jjv5;
        }

        public String getWeb_jjv6() {
            return web_jjv6;
        }

        public void setWeb_jjv6(String web_jjv6) {
            this.web_jjv6 = web_jjv6;
        }

        public String getJiujitime() {
            return jiujitime;
        }

        public void setJiujitime(String jiujitime) {
            this.jiujitime = jiujitime;
        }
    }
}
