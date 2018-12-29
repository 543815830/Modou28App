package com.ruixin.administrator.ruixinapplication.notice;

import java.util.List;

/**
 * Created by 李丽 on 2018/7/12.
 */

public class NoticeDb {

    /**
     * status : 1
     * data : [{"smsid":"22444","usersid":"25","look":"0","title":"新消息2","mag":"新消息测试2","time":"2018-07-12 15:24:26"},{"smsid":"22443","usersid":"23","look":"1","title":"新消息","mag":"新消息测试","time":"2018-07-12 00:00:00"}]
     */

    private int status;
    private List<DataBean> data;

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
         * smsid : 22444
         * usersid : 25
         * look : 0
         * title : 新消息2
         * mag : 新消息测试2
         * time : 2018-07-12 15:24:26
         */

        private String smsid;
        private String usersid;
        private String look;
        private String title;
        private String mag;
        private String time;

        public String getSmsid() {
            return smsid;
        }

        public void setSmsid(String smsid) {
            this.smsid = smsid;
        }

        public String getUsersid() {
            return usersid;
        }

        public void setUsersid(String usersid) {
            this.usersid = usersid;
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

        public String getMag() {
            return mag;
        }

        public void setMag(String mag) {
            this.mag = mag;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        private boolean isFlag;

        public boolean isFlag() {
            return isFlag;
        }

        public void setFlag(boolean flag) {
            isFlag = flag;
        }
    }
}
