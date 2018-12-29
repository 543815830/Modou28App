package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/27.
 */

public class PeilvDb {

    /**
     * status : 1
     * data : {"odds":[214.7484,71.5828,35.7914,21.4748,14.3166,10.2261,7.6696,5.9652,4.7722,3.9045,3.4087,3.1123,2.9418,2.8633,2.8633,2.9418,3.1123,3.4087,3.9045,4.7722,5.9652,7.6696,10.2261,14.3166,21.4748,35.7914,71.5828,214.7484]}
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
        private List<Double> odds;

        public  List<Double>getOdds() {
            return odds;
        }

        public void setOdds(List<Double> odds) {
            this.odds = odds;
        }
    }
}
