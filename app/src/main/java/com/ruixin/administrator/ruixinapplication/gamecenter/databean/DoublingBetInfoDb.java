package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/10/25.
 */

public class DoublingBetInfoDb {

    /**
     * status : 1
     * data : {"result":"success","curno":"2974","modellist":[{"modelname":"WENTI6","modelid":"1814"},{"modelname":"扣","modelid":"1815"},{"modelname":"WE","modelid":"1816"},{"modelname":"扣我","modelid":"1817"},{"modelname":"67u","modelid":"1818"},{"modelname":"yuhb57","modelid":"1819"},{"modelname":"yuhbn","modelid":"1820"},{"modelname":"567","modelid":"1821"},{"modelname":"6788","modelid":"1822"}],"caselist":[{"id":"3997","name":"翻倍的小鸟","uid":"19177","game":"xt28","type":"3","startmodel":"1817","lastmodel":"0","lastresult":"0","maxpoint":"5000","minpoint":"25","startno":"2843","endno":"3000","state":"已停止","maxyl":"50000","minyl":"2500","yl":"0","tzsx":"25000","ddsx":"1","fbyh":"2","fbbs":"2.0000","gametype":null,"count":"0"}]}
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
         * result : success
         * curno : 2974
         * modellist : [{"modelname":"WENTI6","modelid":"1814"},{"modelname":"扣","modelid":"1815"},{"modelname":"WE","modelid":"1816"},{"modelname":"扣我","modelid":"1817"},{"modelname":"67u","modelid":"1818"},{"modelname":"yuhb57","modelid":"1819"},{"modelname":"yuhbn","modelid":"1820"},{"modelname":"567","modelid":"1821"},{"modelname":"6788","modelid":"1822"}]
         * caselist : [{"id":"3997","name":"翻倍的小鸟","uid":"19177","game":"xt28","type":"3","startmodel":"1817","lastmodel":"0","lastresult":"0","maxpoint":"5000","minpoint":"25","startno":"2843","endno":"3000","state":"已停止","maxyl":"50000","minyl":"2500","yl":"0","tzsx":"25000","ddsx":"1","fbyh":"2","fbbs":"2.0000","gametype":null,"count":"0"}]
         */

        private String result;
        private String curno;
        private List<MymodeDb.DataBean.ModellistBean> modellist;
        private List<CaselistBean> caselist;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getCurno() {
            return curno;
        }

        public void setCurno(String curno) {
            this.curno = curno;
        }

        public List<MymodeDb.DataBean.ModellistBean> getModellist() {
            return modellist;
        }

        public void setModellist(List<MymodeDb.DataBean.ModellistBean> modellist) {
            this.modellist = modellist;
        }

        public List<CaselistBean> getCaselist() {
            return caselist;
        }

        public void setCaselist(List<CaselistBean> caselist) {
            this.caselist = caselist;
        }

        public static class ModellistBean {
            /**
             * modelname : WENTI6
             * modelid : 1814
             */

            private String modelname;
            private String modelid;

            public String getModelname() {
                return modelname;
            }

            public void setModelname(String modelname) {
                this.modelname = modelname;
            }

            public String getModelid() {
                return modelid;
            }

            public void setModelid(String modelid) {
                this.modelid = modelid;
            }
        }

        public static class CaselistBean {
            /**
             * id : 3997
             * name : 翻倍的小鸟
             * uid : 19177
             * game : xt28
             * type : 3
             * startmodel : 1817
             * modelname
             * lastmodel : 0
             * lastresult : 0
             * maxpoint : 5000
             * minpoint : 25
             * startno : 2843
             * endno : 3000
             * state : 已停止
             * maxyl : 50000
             * minyl : 2500
             * yl : 0
             * tzsx : 25000
             * ddsx : 1
             * fbyh : 2
             * fbbs : 2.0000
             * gametype : null
             * count : 0
             */

            private String id;
            private String name;
            private String uid;
            private String game;
            private String type;
            private String startmodel;
            private String modelname;
            private String lastmodel;
            private String lastresult;
            private String maxpoint;
            private String minpoint;
            private String startno;
            private String endno;
            private String state;
            private String maxyl;
            private String minyl;
            private String yl;
            private String tzsx;
            private String ddsx;
            private String fbyh;
            private String fbbs;
            private Object gametype;
            private String count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getGame() {
                return game;
            }

            public void setGame(String game) {
                this.game = game;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStartmodel() {
                return startmodel;
            }

            public void setStartmodel(String startmodel) {
                this.startmodel = startmodel;
            }  public String getModelname() {
                return modelname;
            }
            private boolean isFlag;

            public boolean isFlag() {
                return isFlag;
            }

            public void setFlag(boolean flag) {
                isFlag = flag;
            }
            public void setModelname(String modelname) {
                this.modelname = modelname;
            }

            public String getLastmodel() {
                return lastmodel;
            }

            public void setLastmodel(String lastmodel) {
                this.lastmodel = lastmodel;
            }

            public String getLastresult() {
                return lastresult;
            }

            public void setLastresult(String lastresult) {
                this.lastresult = lastresult;
            }

            public String getMaxpoint() {
                return maxpoint;
            }

            public void setMaxpoint(String maxpoint) {
                this.maxpoint = maxpoint;
            }

            public String getMinpoint() {
                return minpoint;
            }

            public void setMinpoint(String minpoint) {
                this.minpoint = minpoint;
            }

            public String getStartno() {
                return startno;
            }

            public void setStartno(String startno) {
                this.startno = startno;
            }

            public String getEndno() {
                return endno;
            }

            public void setEndno(String endno) {
                this.endno = endno;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getMaxyl() {
                return maxyl;
            }

            public void setMaxyl(String maxyl) {
                this.maxyl = maxyl;
            }

            public String getMinyl() {
                return minyl;
            }

            public void setMinyl(String minyl) {
                this.minyl = minyl;
            }

            public String getYl() {
                return yl;
            }

            public void setYl(String yl) {
                this.yl = yl;
            }

            public String getTzsx() {
                return tzsx;
            }

            public void setTzsx(String tzsx) {
                this.tzsx = tzsx;
            }

            public String getDdsx() {
                return ddsx;
            }

            public void setDdsx(String ddsx) {
                this.ddsx = ddsx;
            }

            public String getFbyh() {
                return fbyh;
            }

            public void setFbyh(String fbyh) {
                this.fbyh = fbyh;
            }

            public String getFbbs() {
                return fbbs;
            }

            public void setFbbs(String fbbs) {
                this.fbbs = fbbs;
            }

            public Object getGametype() {
                return gametype;
            }

            public void setGametype(Object gametype) {
                this.gametype = gametype;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
