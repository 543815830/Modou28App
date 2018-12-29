package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/7/9.
 */

public class AdvanceDb {


    /**
     * status : 1
     * data : [{"gamename":"xt28","newflag":1,"gamechname":"急速28","getprize":2,"cgnum":"无","qishu":"无","yingli":"无","slv":"无","jlmoney":0,"nextqishu":3,"nextyingli":500,"nextslv":20},{"gamename":"xt16","newflag":1,"gamechname":"急速16","getprize":2,"cgnum":"无","qishu":"无","yingli":"无","slv":"无","jlmoney":0,"nextqishu":2,"nextyingli":2000,"nextslv":40},{"gamename":"xt10","newflag":1,"gamechname":"急速10","getprize":2,"cgnum":"无","qishu":"无","yingli":"无","slv":"无","jlmoney":0,"nextqishu":3,"nextyingli":2000,"nextslv":40}]
     */

    private int status;
    private List<DataBean> data;
    private List<ListBean> list;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
    public static class DataBean {
        /**
         * gamename : xt28
         * newflag : 1
         * gamechname : 急速28
         * getprize : 2
         * cgnum : 无
         * qishu : 无
         * yingli : 无
         * slv : 无
         * jlmoney : 0
         * nextqishu : 3
         * nextyingli : 500
         * nextslv : 20
         */

        private String gamename;
        private int newflag;
        private String gamechname;
        private int getprize;
        private String cgnum;
        private String qishu;
        private String yingli;
        private String slv;
        private int jlmoney;
        private String nextqishu;
        private String nextyingli;
        private String nextslv;
        private List<DataBean> list;

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public int getNewflag() {
            return newflag;
        }

        public void setNewflag(int newflag) {
            this.newflag = newflag;
        }

        public String getGamechname() {
            return gamechname;
        }

        public void setGamechname(String gamechname) {
            this.gamechname = gamechname;
        }

        public int getGetprize() {
            return getprize;
        }

        public void setGetprize(int getprize) {
            this.getprize = getprize;
        }

        public String getCgnum() {
            return cgnum;
        }

        public void setCgnum(String cgnum) {
            this.cgnum = cgnum;
        }

        public String getQishu() {
            return qishu;
        }

        public void setQishu(String qishu) {
            this.qishu = qishu;
        }

        public String getYingli() {
            return yingli;
        }

        public void setYingli(String yingli) {
            this.yingli = yingli;
        }

        public String getSlv() {
            return slv;
        }

        public void setSlv(String slv) {
            this.slv = slv;
        }

        public int getJlmoney() {
            return jlmoney;
        }

        public void setJlmoney(int jlmoney) {
            this.jlmoney = jlmoney;
        }

        public String getNextqishu() {
            return nextqishu;
        }

        public void setNextqishu(String nextqishu) {
            this.nextqishu = nextqishu;
        }

        public String getNextyingli() {
            return nextyingli;
        }

        public void setNextyingli(String nextyingli) {
            this.nextyingli = nextyingli;
        }

        public String getNextslv() {
            return nextslv;
        }

        public void setNextslv(String nextslv) {
            this.nextslv = nextslv;
        }


    }
    public static class ListBean {
        public ListBean(String gamename, String gamechname) {
            this.gamename = gamename;
            this.gamechname = gamechname;
        }

        /**
         * gamename : xt28
         * gamechname : 急速28
         */

        private String gamename;
        private String gamechname;

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public String getGamechname() {
            return gamechname;
        }

        public void setGamechname(String gamechname) {
            this.gamechname = gamechname;
        }

        private boolean isFlag;

        public boolean isFlag() {
            return isFlag;
        }

        public void setFlag(boolean flag) {
            isFlag = flag;
        }
    }
}
