package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/19.
 * 邮箱：543815830@qq.com
 */

public class Salary {

    /**
     * status : 1
     * data : {"gongzi":"0.0000","gongzi_num":"10000","gongzi_auto":""}
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
         * gongzi : 0.0000
         * gongzi_num : 10000
         * gongzi_auto :
         */

        private String gongzi;
        private String gongzi_num;
        private String gongzi_auto;

        public String getGongzi() {
            return gongzi;
        }

        public void setGongzi(String gongzi) {
            this.gongzi = gongzi;
        }

        public String getGongzi_num() {
            return gongzi_num;
        }

        public void setGongzi_num(String gongzi_num) {
            this.gongzi_num = gongzi_num;
        }

        public String getGongzi_auto() {
            return gongzi_auto;
        }

        public void setGongzi_auto(String gongzi_auto) {
            this.gongzi_auto = gongzi_auto;
        }
    }
}
