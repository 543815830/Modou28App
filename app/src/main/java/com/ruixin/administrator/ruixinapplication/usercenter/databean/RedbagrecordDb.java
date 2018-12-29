package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李丽 on 2018/9/13.
 */

public class RedbagrecordDb {

    /**
     * status : 1
     * msg : 我发的红包
     * data : [{"hbid":"AkJVPswkGN6Glbqz","type":"1","rest":"443","points":"600","get":"1","sum":"5","time":"2018-09-12 14:52:43","state":"1"},{"hbid":"68SJrGIspRDNJZ4O","type":"1","rest":"600","points":"600","get":"0","sum":"5","time":"2018-09-12 14:50:44","state":"1"},{"hbid":"btbK1LKdRR6kaPtW","type":"1","rest":"3710","points":"5000","get":"1","sum":"6","time":"2018-09-12 14:14:11","state":"1"},{"hbid":"NoOIR0hjK81cLd9B","type":"1","rest":"500","points":"500","get":"0","sum":"2","time":"2018-09-12 14:06:50","state":"1"},{"hbid":"AKKwqqHWF96whyFD","type":"1","rest":"439","points":"500","get":"1","sum":"10","time":"2018-09-12 14:03:53","state":"1"}]
     */

    private int status;
    private String msg;
    private String totalpage;
    private List<DataBean> data;

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
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * hbid : AkJVPswkGN6Glbqz
         * type : 1
         * rest : 443
         * points : 600
         * get : 1
         * sum : 5
         * time : 2018-09-12 14:52:43
         * state : 1
         */

        private String id;
        private String hbid;
        private String type;
        private String rest;
        private String points;
        private String get;
        private String sum;
        private String time;
        private String state;

        public String getHbid() {
            return hbid;
        }

        public void setHbid(String hbid) {
            this.hbid = hbid;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRest() {
            return rest;
        }

        public void setRest(String rest) {
            this.rest = rest;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getGet() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
