package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * Created by 李丽 on 2018/9/18.
 */

public class GetRedbagDb {

    /**
     * status : 2
     * msg : 领到红包
     * data : {"senderName":"魔图","points":1009}
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
         * senderName : 魔图
         * points : 1009
         */

        private String senderName;
        private int points;

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }
}
