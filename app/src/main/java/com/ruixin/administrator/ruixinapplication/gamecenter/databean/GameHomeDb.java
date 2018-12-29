package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/8.
 */

public class GameHomeDb {

    /**
     * status : 1
     * data : {"totalpage":145,"totalrecord":2887,"mytoday":{"profit":0,"playnum":0,"winpercent":"0.0"},"openhistory":[{"no":"4550544","stoptime":"15:45","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550543","stoptime":"10:55","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550542","stoptime":"10:54","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550541","stoptime":"10:54","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550540","stoptime":"10:53","isopened":"1","openresult":["2","3","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550539","stoptime":"10:53","isopened":"1","openresult":["2","3","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550538","stoptime":"10:52","isopened":"1","openresult":["3","5","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550537","stoptime":"10:52","isopened":"1","openresult":["4","2","6"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550536","stoptime":"10:51","isopened":"1","openresult":["1","4","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550535","stoptime":"10:51","isopened":"1","openresult":["6","2","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550534","stoptime":"10:50","isopened":"1","openresult":["3","2","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550533","stoptime":"10:50","isopened":"1","openresult":["2","6","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550532","stoptime":"10:49","isopened":"1","openresult":["5","3","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550531","stoptime":"10:49","isopened":"1","openresult":["2","4","6"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550530","stoptime":"10:48","isopened":"1","openresult":["1","5","6"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550529","stoptime":"10:48","isopened":"1","openresult":["4","1","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550528","stoptime":"10:47","isopened":"1","openresult":["2","1","3"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550527","stoptime":"10:47","isopened":"1","openresult":["2","6","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550526","stoptime":"10:46","isopened":"1","openresult":["1","2","3"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550525","stoptime":"10:46","isopened":"1","openresult":["6","4","10"],"mybetpoints":0,"mywinpoints":0,"origindata":null}]}
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
         * totalpage : 145
         * totalrecord : 2887
         * mytoday : {"profit":0,"playnum":0,"winpercent":"0.0"}
         * openhistory : [{"no":"4550544","stoptime":"15:45","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550543","stoptime":"10:55","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550542","stoptime":"10:54","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550541","stoptime":"10:54","isopened":"0","openresult":[""],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550540","stoptime":"10:53","isopened":"1","openresult":["2","3","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550539","stoptime":"10:53","isopened":"1","openresult":["2","3","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550538","stoptime":"10:52","isopened":"1","openresult":["3","5","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550537","stoptime":"10:52","isopened":"1","openresult":["4","2","6"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550536","stoptime":"10:51","isopened":"1","openresult":["1","4","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550535","stoptime":"10:51","isopened":"1","openresult":["6","2","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550534","stoptime":"10:50","isopened":"1","openresult":["3","2","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550533","stoptime":"10:50","isopened":"1","openresult":["2","6","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550532","stoptime":"10:49","isopened":"1","openresult":["5","3","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550531","stoptime":"10:49","isopened":"1","openresult":["2","4","6"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550530","stoptime":"10:48","isopened":"1","openresult":["1","5","6"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550529","stoptime":"10:48","isopened":"1","openresult":["4","1","5"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550528","stoptime":"10:47","isopened":"1","openresult":["2","1","3"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550527","stoptime":"10:47","isopened":"1","openresult":["2","6","8"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550526","stoptime":"10:46","isopened":"1","openresult":["1","2","3"],"mybetpoints":0,"mywinpoints":0,"origindata":null},{"no":"4550525","stoptime":"10:46","isopened":"1","openresult":["6","4","10"],"mybetpoints":0,"mywinpoints":0,"origindata":null}]
         */

        private int totalpage;
        private int totalrecord;
        private MytodayBean mytoday;
        private List<OpenhistoryBean> openhistory;

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getTotalrecord() {
            return totalrecord;
        }

        public void setTotalrecord(int totalrecord) {
            this.totalrecord = totalrecord;
        }

        public MytodayBean getMytoday() {
            return mytoday;
        }

        public void setMytoday(MytodayBean mytoday) {
            this.mytoday = mytoday;
        }

        public List<OpenhistoryBean> getOpenhistory() {
            return openhistory;
        }

        public void setOpenhistory(List<OpenhistoryBean> openhistory) {
            this.openhistory = openhistory;
        }

        public static class MytodayBean {
            /**
             * profit : 0
             * playnum : 0
             * winpercent : 0.0
             */

            private int profit;
            private int playnum;
            private String winpercent;

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

        public static class OpenhistoryBean {
            /**
             * no : 4550544
             * stoptime : 15:45
             * isopened : 0
             * openresult : [""]
             * mybetpoints : 0
             * mywinpoints : 0
             * origindata : null
             */

            private String no;
            private String stoptime;
            private String isopened;
            private int mybetpoints;
            private int mywinpoints;
            private Object origindata;
            private List<String> openresult;

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getStoptime() {
                return stoptime;
            }

            public void setStoptime(String stoptime) {
                this.stoptime = stoptime;
            }

            public String getIsopened() {
                return isopened;
            }

            public void setIsopened(String isopened) {
                this.isopened = isopened;
            }

            public int getMybetpoints() {
                return mybetpoints;
            }

            public void setMybetpoints(int mybetpoints) {
                this.mybetpoints = mybetpoints;
            }

            public int getMywinpoints() {
                return mywinpoints;
            }

            public void setMywinpoints(int mywinpoints) {
                this.mywinpoints = mywinpoints;
            }

            public Object getOrigindata() {
                return origindata;
            }

            public void setOrigindata(Object origindata) {
                this.origindata = origindata;
            }

            public List<String> getOpenresult() {
                return openresult;
            }

            public void setOpenresult(List<String> openresult) {
                this.openresult = openresult;
            }
        }
    }
}
