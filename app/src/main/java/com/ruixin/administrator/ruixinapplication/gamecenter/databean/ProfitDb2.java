package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/12.
 */

public class ProfitDb2 {

    /**
     * status : 1
     * data : {"time_list":["2018-05-31","2018-06-01","2018-06-02","2018-06-03","2018-06-04","2018-06-05","2018-06-06","2018-06-07","2018-06-08","2018-06-09","2018-06-10","2018-06-11","2018-06-12"],"gamedata":[{"gamechname":"急速28","pm":52,"profit":-5384},{"gamechname":"急速16","pm":41,"profit":0},{"gamechname":"急速11","pm":58,"profit":-3615},{"gamechname":"急速36","pm":10,"profit":3067},{"gamechname":"急速10","pm":48,"profit":-100},{"gamechname":"蛋蛋28","pm":39,"profit":0},{"gamechname":"北京28","pm":45,"profit":0},{"gamechname":"北京16","pm":45,"profit":0},{"gamechname":"北京11","pm":44,"profit":0},{"gamechname":"北京36","pm":44,"profit":0},{"gamechname":"冠军10","pm":45,"profit":0},{"gamechname":"蛋蛋外围","pm":43,"profit":0},{"gamechname":"加拿大28","pm":38,"profit":0},{"gamechname":"加拿大16","pm":45,"profit":0},{"gamechname":"加拿大11","pm":46,"profit":0},{"gamechname":"加拿大36","pm":44,"profit":0},{"gamechname":"加拿大外围","pm":51,"profit":0},{"gamechname":"韩国28","pm":49,"profit":0},{"gamechname":"韩国16","pm":44,"profit":0},{"gamechname":"韩国11","pm":45,"profit":0},{"gamechname":"韩国36","pm":43,"profit":0},{"gamechname":"韩国外围","pm":58,"profit":-100},{"gamechname":"期号10","pm":40,"profit":0},{"gamechname":"PK22","pm":46,"profit":0},{"gamechname":"东京28","pm":45,"profit":0},{"gamechname":"东京16","pm":45,"profit":0},{"gamechname":"东京11","pm":45,"profit":0},{"gamechname":"东京36","pm":45,"profit":0},{"gamechname":"东京外围","pm":45,"profit":0},{"gamechname":"急速冠亚","pm":35,"profit":0},{"gamechname":"北京10","pm":43,"profit":0},{"gamechname":"加拿大10","pm":45,"profit":0},{"gamechname":"韩国10","pm":42,"profit":0},{"gamechname":"东京10","pm":45,"profit":0},{"gamechname":"PK冠亚","pm":45,"profit":0},{"gamechname":"急速22","pm":46,"profit":0},{"gamechname":"蛋蛋16","pm":44,"profit":0},{"gamechname":"蛋蛋36","pm":8,"profit":640},{"gamechname":"飞艇10","pm":45,"profit":0},{"gamechname":"飞艇冠军","pm":45,"profit":0},{"gamechname":"飞艇22","pm":43,"profit":0},{"gamechname":"固定蛋蛋28","pm":43,"profit":0},{"gamechname":"固定北京28","pm":37,"profit":0},{"gamechname":"固定加拿大28","pm":42,"profit":0},{"gamechname":"固定韩国28","pm":45,"profit":0},{"gamechname":"趣吧16","pm":45,"profit":0},{"gamechname":"蛋蛋定位","pm":45,"profit":0},{"gamechname":"韩国定位","pm":43,"profit":0},{"gamechname":"加拿大定位","pm":48,"profit":0},{"gamechname":"重庆时时彩","pm":45,"profit":0},{"gamechname":"江苏骰宝","pm":44,"profit":0},{"gamechname":"PK龙虎","pm":45,"profit":0},{"gamechname":"骰宝外围","pm":45,"profit":0},{"gamechname":"幸运农场","pm":45,"profit":0},{"gamechname":"PK赛车","pm":63,"profit":-2},{"gamechname":"中速28","pm":45,"profit":0},{"gamechname":"中速16","pm":45,"profit":0},{"gamechname":"中速11","pm":45,"profit":0},{"gamechname":"中速36","pm":45,"profit":0},{"gamechname":"急速百家乐","pm":45,"profit":0},{"gamechname":"快乐竞猜","pm":45,"profit":0},{"gamechname":"合计","pm":0,"profit":"-5494"}]}
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
        private List<GamedataBean> gamedata;

        public List<String> getTime_list() {
            return time_list;
        }

        public void setTime_list(List<String> time_list) {
            this.time_list = time_list;
        }

        public List<GamedataBean> getGamedata() {
            return gamedata;
        }

        public void setGamedata(List<GamedataBean> gamedata) {
            this.gamedata = gamedata;
        }

        public static class GamedataBean {
            /**
             * gamechname : 急速28
             * pm : 52
             * profit : -5384
             */

            private String gamechname;
            private int pm;
            private int profit;

            public String getGamechname() {
                return gamechname;
            }

            public void setGamechname(String gamechname) {
                this.gamechname = gamechname;
            }

            public int getPm() {
                return pm;
            }

            public void setPm(int pm) {
                this.pm = pm;
            }

            public int getProfit() {
                return profit;
            }

            public void setProfit(int profit) {
                this.profit = profit;
            }
        }
    }
}
