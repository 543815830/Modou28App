package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/8.
 */

public class ProfitDb {

    /**
     * status : 1
     * data : {"time_list":["2018-05-31","2018-06-01","2018-06-02","2018-06-03","2018-06-04","2018-06-05","2018-06-06","2018-06-07"]}
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
        private List<String> time_list;
        private List<String> gamedatalist;
        private List<GamedataBean> gamedata;

        public List<String> getTime_list() {
            return time_list;
        }
        public List<String> getGamedatalist() {
            return gamedatalist;
        }

        public void setTime_list(List<String> time_list) {
            this.time_list = time_list;
        }
        public void setGamedatalist(List<String> gamedatalist) {
            this.gamedatalist = gamedatalist;
        }

        public List<GamedataBean> getGamedata() {
            return gamedata;
        }

        public void setGamedata(List<GamedataBean> gamedata) {
            this.gamedata = gamedata;
        }

        public static class GamedataBean {
            /**
             * date : 2018-06-11
             * data : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-49,0,0,0,0]
             * shouyi : -67
             */

            private String date;
            private int shouyi;
            private List<Integer> data;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getShouyi() {
                return shouyi;
            }

            public void setShouyi(int shouyi) {
                this.shouyi = shouyi;
            }

            public List<Integer> getData() {
                return data;
            }

            public void setData(List<Integer> data) {
                this.data = data;
            }
        }
    }
}
