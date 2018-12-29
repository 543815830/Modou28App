package com.ruixin.administrator.ruixinapplication.exchangemall.domain;

import java.util.List;

/**
 * Created by 李丽 on 2018/9/10.
 */

public class LuckyDb {

    /**
     * status : 1
     * msg : 成功！
     * Result : {"cjcs":"92","num":"4","sum":[{"id":"1","points":"1000","prize":"1000金币","v":"0.99"},{"id":"2","points":"29999","prize":"2000金币","v":"0.88"},{"id":"3","points":"3000","prize":"1000金币","v":"0.00"},{"id":"4","points":"2222","prize":"2000金贵","v":"0.01"}],"prize":"2000金币","points":"29999"}
     */

    private int status;
    private String msg;
    private ResultBean Result;

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

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * cjcs : 92
         * num : 4
         * sum : [{"id":"1","points":"1000","prize":"1000金币","v":"0.99"},{"id":"2","points":"29999","prize":"2000金币","v":"0.88"},{"id":"3","points":"3000","prize":"1000金币","v":"0.00"},{"id":"4","points":"2222","prize":"2000金贵","v":"0.01"}]
         * prize : 2000金币
         * points : 29999
         */

        private String cjcs;
        private String num;
        private String prize;
        private String points;
        private List<SumBean> sum;

        public String getCjcs() {
            return cjcs;
        }

        public void setCjcs(String cjcs) {
            this.cjcs = cjcs;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrize() {
            return prize;
        }

        public void setPrize(String prize) {
            this.prize = prize;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public List<SumBean> getSum() {
            return sum;
        }

        public void setSum(List<SumBean> sum) {
            this.sum = sum;
        }

        public static class SumBean {
            /**
             * id : 1
             * points : 1000
             * prize : 1000金币
             * v : 0.99
             */

            private String id;
            private String points;
            private String prize;
            private String v;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getPrize() {
                return prize;
            }

            public void setPrize(String prize) {
                this.prize = prize;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }
        }
    }
}
