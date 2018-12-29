package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/20.
 * 邮箱：543815830@qq.com
 * 邮箱详情的属性
 */

public class MailContent {

    /**
     * status : 1
     * data : {"id":"22390","usersid":"0","title":"您得到金币奖励","mag":"您收到一次金币奖励,获得金币:1000,金币已经打入您的账户余额","mid":"19146","time":"2018-04-16 11:08:49","del":"0","look":"0"}
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

    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
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
         * id : 22390
         * usersid : 0
         * title : 您得到金币奖励
         * mag : 您收到一次金币奖励,获得金币:1000,金币已经打入您的账户余额
         * mid : 19146
         * time : 2018-04-16 11:08:49
         * del : 0
         * look : 0
         */

        private String id;
        private String usersid;
        private String title;
        private String mag;
        private String mid;
        private String time;
        private String del;
        private String look;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsersid() {
            return usersid;
        }

        public void setUsersid(String usersid) {
            this.usersid = usersid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMag() {
            return mag;
        }

        public void setMag(String mag) {
            this.mag = mag;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }
    }
}
