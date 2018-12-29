package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/12.
 */

public class GaCBarDb {

    /**
     * status : 1
     * data : {"zhname":"急速28","type":"28","nearno":"638163","restsecond":17,"gametime":30,"gamesystem":1,"latestno":"638162","latestresult":["2","8","9","19"],"gamearea":"","mytoday":{"points":11745640,"profit":5370,"playnum":646,"winpercent":"16.6"}}
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
         * zhname : 急速28
         * type : 28
         * nearno : 638163
         * restsecond : 17
         * gametime : 30
         * gamesystem : 1
         * latestno : 638162
         * latestresult : ["2","8","9","19"]
         * gamearea :
         * mytoday : {"points":11745640,"profit":5370,"playnum":646,"winpercent":"16.6"}
         */

        private String zhname;
        private String type;
        private String nearno;
        private int restsecond;
        private int gametime;
        private int gamesystem;
        private String latestno;
        private String gamearea;
        private MytodayBean mytoday;
        private List<String> latestresult;

        public String getZhname() {
            return zhname;
        }

        public void setZhname(String zhname) {
            this.zhname = zhname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNearno() {
            return nearno;
        }

        public void setNearno(String nearno) {
            this.nearno = nearno;
        }

        public int getRestsecond() {
            return restsecond;
        }

        public void setRestsecond(int restsecond) {
            this.restsecond = restsecond;
        }

        public int getGametime() {
            return gametime;
        }

        public void setGametime(int gametime) {
            this.gametime = gametime;
        }

        public int getGamesystem() {
            return gamesystem;
        }

        public void setGamesystem(int gamesystem) {
            this.gamesystem = gamesystem;
        }

        public String getLatestno() {
            return latestno;
        }

        public void setLatestno(String latestno) {
            this.latestno = latestno;
        }

        public String getGamearea() {
            return gamearea;
        }

        public void setGamearea(String gamearea) {
            this.gamearea = gamearea;
        }

        public MytodayBean getMytoday() {
            return mytoday;
        }

        public void setMytoday(MytodayBean mytoday) {
            this.mytoday = mytoday;
        }

        public List<String> getLatestresult() {
            return latestresult;
        }

        public void setLatestresult(List<String> latestresult) {
            this.latestresult = latestresult;
        }

        public static class MytodayBean {
            /**
             * points : 11745640
             * profit : 5370
             * playnum : 646
             * winpercent : 16.6
             */

            private int points;
            private int profit;
            private int playnum;
            private String winpercent;
            private int xnb;

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }
            public int getXnb() {
                return xnb;
            }

            public void setXnb(int xnb) {
                this.xnb = xnb;
            }

            public int getProfit() {
                return profit;
            }

            public void setProfit(int profit) {
                this.profit = profit;
            }

            public int getPlaynum() {
                return playnum;
            }

            public void setPlaynum(int playnum) {
                this.playnum = playnum;
            }

            public String getWinpercent() {
                return winpercent;
            }

            public void setWinpercent(String winpercent) {
                this.winpercent = winpercent;
            }
        }
    }
}
