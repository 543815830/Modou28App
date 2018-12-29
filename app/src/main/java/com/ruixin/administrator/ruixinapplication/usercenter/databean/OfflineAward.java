package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/18.
 * 邮箱：543815830@qq.com
 */

public class OfflineAward {

    /**
     * status : 1
     * data : {"tgv1":"0","tgv2":"0","tgv3":"0","tgv4":"0","tgv5":"0","tgv6":"0","web_chongzhi_fanli":"2","web_tzfl":"0.05","xxksbl":"1.0000","tjurl":"http://localhost/app/Reg_Recom_App.php?userID=19146"}
     */

    private int status;
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
         * tgv1 : 0
         * tgv2 : 0
         * tgv3 : 0
         * tgv4 : 0
         * tgv5 : 0
         * tgv6 : 0
         * web_chongzhi_fanli : 2
         * web_tzfl : 0.05
         * xxksbl : 1.0000
         * tjurl : http://localhost/app/Reg_Recom_App.php?userID=19146
         */

        private String tgv1;
        private String tgv2;
        private String tgv3;
        private String tgv4;
        private String tgv5;
        private String tgv6;
        private String web_chongzhi_fanli;
        private String web_tzfl;
        private String xxksbl;
        private String tjurl;

        public String getTgv1() {
            return tgv1;
        }

        public void setTgv1(String tgv1) {
            this.tgv1 = tgv1;
        }

        public String getTgv2() {
            return tgv2;
        }

        public void setTgv2(String tgv2) {
            this.tgv2 = tgv2;
        }

        public String getTgv3() {
            return tgv3;
        }

        public void setTgv3(String tgv3) {
            this.tgv3 = tgv3;
        }

        public String getTgv4() {
            return tgv4;
        }

        public void setTgv4(String tgv4) {
            this.tgv4 = tgv4;
        }

        public String getTgv5() {
            return tgv5;
        }

        public void setTgv5(String tgv5) {
            this.tgv5 = tgv5;
        }

        public String getTgv6() {
            return tgv6;
        }

        public void setTgv6(String tgv6) {
            this.tgv6 = tgv6;
        }

        public String getWeb_chongzhi_fanli() {
            return web_chongzhi_fanli;
        }

        public void setWeb_chongzhi_fanli(String web_chongzhi_fanli) {
            this.web_chongzhi_fanli = web_chongzhi_fanli;
        }

        public String getWeb_tzfl() {
            return web_tzfl;
        }

        public void setWeb_tzfl(String web_tzfl) {
            this.web_tzfl = web_tzfl;
        }

        public String getXxksbl() {
            return xxksbl;
        }

        public void setXxksbl(String xxksbl) {
            this.xxksbl = xxksbl;
        }

        public String getTjurl() {
            return tjurl;
        }

        public void setTjurl(String tjurl) {
            this.tjurl = tjurl;
        }
    }
}
