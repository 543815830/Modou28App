package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/6.
 */

public class MyBetDb {

    /**
     * status : 1
     * data : {"totalpage":70,"minno":"620946","maxno":"623821","sumpoints":"212888","betpoints":"219479","ykb":"97.00","mybetlist":[{"id":"623821","kjtime":"06-06 11:09:29","points":"157","hdpoints":"999","result":["5","7","5","17"],"latestdata":null,"zjpl":"1000.0002","ykbl":"636.31"},{"id":"623820","kjtime":"06-06 11:08:59","points":"157","hdpoints":"0","result":["0","9","4","13"],"latestdata":null,"zjpl":"1000.0001","ykbl":"0.00"},{"id":"623815","kjtime":"06-06 11:06:29","points":"157","hdpoints":"0","result":["6","0","1","7"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623814","kjtime":"06-06 11:05:59","points":"157","hdpoints":"0","result":["3","0","3","6"],"latestdata":null,"zjpl":"1000.001","ykbl":"0.00"},{"id":"623812","kjtime":"06-06 11:04:59","points":"157","hdpoints":"0","result":["5","9","4","18"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623811","kjtime":"06-06 11:04:29","points":"157","hdpoints":"0","result":["4","1","9","14"],"latestdata":null,"zjpl":"1000.0003","ykbl":"0.00"},{"id":"623810","kjtime":"06-06 11:03:59","points":"157","hdpoints":"0","result":["1","6","7","14"],"latestdata":null,"zjpl":"999.9996","ykbl":"0.00"},{"id":"623807","kjtime":"06-06 11:02:29","points":"157","hdpoints":"999","result":["3","5","9","17"],"latestdata":null,"zjpl":"999.9992","ykbl":"636.31"},{"id":"623803","kjtime":"06-06 11:00:29","points":"157","hdpoints":"0","result":["3","7","0","10"],"latestdata":null,"zjpl":"1000.0003","ykbl":"0.00"},{"id":"623802","kjtime":"06-06 10:59:59","points":"157","hdpoints":"0","result":["3","4","1","8"],"latestdata":null,"zjpl":"1000.0078","ykbl":"0.00"},{"id":"623801","kjtime":"06-06 10:59:29","points":"157","hdpoints":"0","result":["7","0","6","13"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623799","kjtime":"06-06 10:58:29","points":"157","hdpoints":"0","result":["3","9","0","12"],"latestdata":null,"zjpl":"999.9984","ykbl":"0.00"},{"id":"623798","kjtime":"06-06 10:57:59","points":"157","hdpoints":"999","result":["7","0","9","16"],"latestdata":null,"zjpl":"1000.0023","ykbl":"636.31"},{"id":"623793","kjtime":"06-06 10:55:29","points":"157","hdpoints":"0","result":["0","7","3","10"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623788","kjtime":"06-06 10:52:59","points":"157","hdpoints":"0","result":["5","4","1","10"],"latestdata":null,"zjpl":"1000.0021","ykbl":"0.00"},{"id":"623786","kjtime":"06-06 10:51:59","points":"157","hdpoints":"0","result":["0","3","4","7"],"latestdata":null,"zjpl":"1000.0032","ykbl":"0.00"},{"id":"623785","kjtime":"06-06 10:51:29","points":"157","hdpoints":"0","result":["3","2","1","6"],"latestdata":null,"zjpl":"999.9984","ykbl":"0.00"},{"id":"623783","kjtime":"06-06 10:50:29","points":"157","hdpoints":"0","result":["9","5","5","19"],"latestdata":null,"zjpl":"1000.0013","ykbl":"0.00"},{"id":"623780","kjtime":"06-06 10:48:59","points":"157","hdpoints":"1000","result":["7","7","9","23"],"latestdata":null,"zjpl":"1000","ykbl":"636.94"},{"id":"623779","kjtime":"06-06 10:48:29","points":"157","hdpoints":"0","result":["9","2","4","15"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"}],"gameinfo":{"type":"28"}}
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
        /**
         * totalpage : 70
         * minno : 620946
         * maxno : 623821
         * sumpoints : 212888
         * betpoints : 219479
         * ykb : 97.00
         * mybetlist : [{"id":"623821","kjtime":"06-06 11:09:29","points":"157","hdpoints":"999","result":["5","7","5","17"],"latestdata":null,"zjpl":"1000.0002","ykbl":"636.31"},{"id":"623820","kjtime":"06-06 11:08:59","points":"157","hdpoints":"0","result":["0","9","4","13"],"latestdata":null,"zjpl":"1000.0001","ykbl":"0.00"},{"id":"623815","kjtime":"06-06 11:06:29","points":"157","hdpoints":"0","result":["6","0","1","7"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623814","kjtime":"06-06 11:05:59","points":"157","hdpoints":"0","result":["3","0","3","6"],"latestdata":null,"zjpl":"1000.001","ykbl":"0.00"},{"id":"623812","kjtime":"06-06 11:04:59","points":"157","hdpoints":"0","result":["5","9","4","18"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623811","kjtime":"06-06 11:04:29","points":"157","hdpoints":"0","result":["4","1","9","14"],"latestdata":null,"zjpl":"1000.0003","ykbl":"0.00"},{"id":"623810","kjtime":"06-06 11:03:59","points":"157","hdpoints":"0","result":["1","6","7","14"],"latestdata":null,"zjpl":"999.9996","ykbl":"0.00"},{"id":"623807","kjtime":"06-06 11:02:29","points":"157","hdpoints":"999","result":["3","5","9","17"],"latestdata":null,"zjpl":"999.9992","ykbl":"636.31"},{"id":"623803","kjtime":"06-06 11:00:29","points":"157","hdpoints":"0","result":["3","7","0","10"],"latestdata":null,"zjpl":"1000.0003","ykbl":"0.00"},{"id":"623802","kjtime":"06-06 10:59:59","points":"157","hdpoints":"0","result":["3","4","1","8"],"latestdata":null,"zjpl":"1000.0078","ykbl":"0.00"},{"id":"623801","kjtime":"06-06 10:59:29","points":"157","hdpoints":"0","result":["7","0","6","13"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623799","kjtime":"06-06 10:58:29","points":"157","hdpoints":"0","result":["3","9","0","12"],"latestdata":null,"zjpl":"999.9984","ykbl":"0.00"},{"id":"623798","kjtime":"06-06 10:57:59","points":"157","hdpoints":"999","result":["7","0","9","16"],"latestdata":null,"zjpl":"1000.0023","ykbl":"636.31"},{"id":"623793","kjtime":"06-06 10:55:29","points":"157","hdpoints":"0","result":["0","7","3","10"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"},{"id":"623788","kjtime":"06-06 10:52:59","points":"157","hdpoints":"0","result":["5","4","1","10"],"latestdata":null,"zjpl":"1000.0021","ykbl":"0.00"},{"id":"623786","kjtime":"06-06 10:51:59","points":"157","hdpoints":"0","result":["0","3","4","7"],"latestdata":null,"zjpl":"1000.0032","ykbl":"0.00"},{"id":"623785","kjtime":"06-06 10:51:29","points":"157","hdpoints":"0","result":["3","2","1","6"],"latestdata":null,"zjpl":"999.9984","ykbl":"0.00"},{"id":"623783","kjtime":"06-06 10:50:29","points":"157","hdpoints":"0","result":["9","5","5","19"],"latestdata":null,"zjpl":"1000.0013","ykbl":"0.00"},{"id":"623780","kjtime":"06-06 10:48:59","points":"157","hdpoints":"1000","result":["7","7","9","23"],"latestdata":null,"zjpl":"1000","ykbl":"636.94"},{"id":"623779","kjtime":"06-06 10:48:29","points":"157","hdpoints":"0","result":["9","2","4","15"],"latestdata":null,"zjpl":"1000","ykbl":"0.00"}]
         * gameinfo : {"type":"28"}
         */

        private int totalpage;
        private String minno;
        private String maxno;
        private String sumpoints;
        private String betpoints;
        private String ykb;
        private GameinfoBean gameinfo;
        private List<MybetlistBean> mybetlist;

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public String getMinno() {
            return minno;
        }

        public void setMinno(String minno) {
            this.minno = minno;
        }

        public String getMaxno() {
            return maxno;
        }

        public void setMaxno(String maxno) {
            this.maxno = maxno;
        }

        public String getSumpoints() {
            return sumpoints;
        }

        public void setSumpoints(String sumpoints) {
            this.sumpoints = sumpoints;
        }

        public String getBetpoints() {
            return betpoints;
        }

        public void setBetpoints(String betpoints) {
            this.betpoints = betpoints;
        }

        public String getYkb() {
            return ykb;
        }

        public void setYkb(String ykb) {
            this.ykb = ykb;
        }

        public GameinfoBean getGameinfo() {
            return gameinfo;
        }

        public void setGameinfo(GameinfoBean gameinfo) {
            this.gameinfo = gameinfo;
        }

        public List<MybetlistBean> getMybetlist() {
            return mybetlist;
        }

        public void setMybetlist(List<MybetlistBean> mybetlist) {
            this.mybetlist = mybetlist;
        }

        public static class GameinfoBean {
            /**
             * type : 28
             */

            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class MybetlistBean {
            /**
             * id : 623821
             * kjtime : 06-06 11:09:29
             * points : 157
             * hdpoints : 999
             * result : ["5","7","5","17"]
             * latestdata : null
             * zjpl : 1000.0002
             * ykbl : 636.31
             */

            private String id;
            private String kjtime;
            private String points;
            private String hdpoints;
            private String latestdata;
            private String zjpl;
            private String ykbl;
            private List<String> result;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getKjtime() {
                return kjtime;
            }

            public void setKjtime(String kjtime) {
                this.kjtime = kjtime;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getHdpoints() {
                return hdpoints;
            }

            public void setHdpoints(String hdpoints) {
                this.hdpoints = hdpoints;
            }

            public String getLatestdata() {
                return latestdata;
            }

            public void setLatestdata(String latestdata) {
                this.latestdata = latestdata;
            }

            public String getZjpl() {
                return zjpl;
            }

            public void setZjpl(String zjpl) {
                this.zjpl = zjpl;
            }

            public String getYkbl() {
                return ykbl;
            }

            public void setYkbl(String ykbl) {
                this.ykbl = ykbl;
            }

            public List<String> getResult() {
                return result;
            }

            public void setResult(List<String> result) {
                this.result = result;
            }
        }
    }
}
