package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/19.
 * 邮箱：543815830@qq.com
 * 邮箱的属性
 */

public class MailBox {

    /**
     * status : 1
     * data : [{"smsid":"22390","userid":"官方","look":"0","mag":"您收到一次金币奖励,获得金币:1000,金币已经打入您的账户余额","time":"2018-04-16 11:08:49"},{"smsid":"22383","userid":"官方","look":"0","mag":"您收到一次金币奖励,获得金币:100,金币已经打入您的账户余额","time":"2018-04-17 10:52:19"}]
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    private List<DataBean> data;
    private String totalpage;
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * smsid : 22390
         * userid : 官方
         * look : 0
         * mag : 您收到一次金币奖励,获得金币:1000,金币已经打入您的账户余额
         * time : 2018-04-16 11:08:49
         */

        private String smsid;
        private String userid;
        private String look;
        private String title;

        private String time;

        public String getSmsid() {
            return smsid;
        }

        public void setSmsid(String smsid) {
            this.smsid = smsid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
