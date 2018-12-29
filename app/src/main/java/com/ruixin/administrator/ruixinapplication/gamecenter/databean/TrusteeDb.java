package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/19.
 */

public class TrusteeDb {

    /**
     * status : 1
     * data : {"autolist":[{"id":"3853","game":"xtgy","chgamename":"急速冠亚","type":"1","startno":"647297","endno":"650297","state":"1"},{"id":"3852","game":"xtgy","chgamename":"急速冠亚","type":"1","startno":"647281","endno":"650286","state":"1"},{"id":"3851","game":"xt11","chgamename":"急速11","type":"1","startno":"123456564","endno":"654513211","state":"1"},{"id":"3850","game":"jnd36","chgamename":"加拿大36","type":"2","startno":"2295067","endno":"2298067","state":"1"},{"id":"3848","game":"xt36","chgamename":"急速36","type":"2","startno":"162245","endno":"1221444","state":"1"}]}
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
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
        private List<AutolistBean> autolist;

        public List<AutolistBean> getAutolist() {
            return autolist;
        }

        public void setAutolist(List<AutolistBean> autolist) {
            this.autolist = autolist;
        }

        public static class AutolistBean {
            /**
             * id : 3853
             * game : xtgy
             * chgamename : 急速冠亚
             * type : 1
             * startno : 647297
             * endno : 650297
             * state : 1
             */

            private String id;
            private String name;
            private String game;
            private String chgamename;
            private String type;
            private String gametype;
            private String startno;
            private String endno;
            private String state;

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

            public String getGame() {
                return game;
            }

            public void setGame(String game) {
                this.game = game;
            }

            public String getChgamename() {
                return chgamename;
            }

            public void setChgamename(String chgamename) {
                this.chgamename = chgamename;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
   public String getGametype() {
                return gametype;
            }

            public void setGametype(String gametype) {
                this.gametype = gametype;
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
        }
    }
}
