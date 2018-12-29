package com.ruixin.administrator.ruixinapplication.domain;

/**
 * 作者：Created by ${李丽} on 2018/3/26.
 * 邮箱：543815830@qq.com
 * 返回数据中data有数据的属性
 */

public class EntryDb {

    /**
     * status : 1
     * msg : 验证成功
     * data :
     */

    private int status;
    private String msg;
    /**
     * data : {"qcuid":"19148","qcpass":"e10adc3949ba59abbe56e057f20f883e"}
     */

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
         * qcuid : 19148
         * qcpass : e10adc3949ba59abbe56e057f20f883e
         */

        private String qcuid;
        private String qcpass;

        public String getQcuid() {
            return qcuid;
        }

        public void setQcuid(String qcuid) {
            this.qcuid = qcuid;
        }

        public String getQcpass() {
            return qcpass;
        }

        public void setQcpass(String qcpass) {
            this.qcpass = qcpass;
        }
    }
}
