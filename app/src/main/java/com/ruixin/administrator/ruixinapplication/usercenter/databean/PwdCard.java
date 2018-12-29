package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/25.
 * 邮箱：543815830@qq.com
 */

public class PwdCard {

    /**
     * status : -7
     * msg : 有密保卡，用密保卡登录
     * data : {"chaeckcard_data":"B4","y":"B","x":4,"usersid":"19143","text":"54"}
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
         * chaeckcard_data : B4
         * y : B
         * x : 4
         * usersid : 19143
         * text : 54
         */

        private String chaeckcard_data;
        private String y;
        private int x;
        private String usersid;
        private String text;

        public String getChaeckcard_data() {
            return chaeckcard_data;
        }

        public void setChaeckcard_data(String chaeckcard_data) {
            this.chaeckcard_data = chaeckcard_data;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public String getUsersid() {
            return usersid;
        }

        public void setUsersid(String usersid) {
            this.usersid = usersid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
