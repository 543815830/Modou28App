package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/9/17.
 */

public class MybagDetailDb {

    /**
     * status : 1
     * msg : 我的红包详情
     * data : {"list":[{"name":"魔图","points":"5000","time":"2018-09-14 14:08:46","getuid":"19171"}],"luckymanID":"19171"}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"name":"魔图","points":"5000","time":"2018-09-14 14:08:46","getuid":"19171"}]
         * luckymanID : 19171
         */

        private String luckymanID;
        private List<ListBean> list;

        public String getLuckymanID() {
            return luckymanID;
        }

        public void setLuckymanID(String luckymanID) {
            this.luckymanID = luckymanID;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : 魔图
             * points : 5000
             * time : 2018-09-14 14:08:46
             * getuid : 19171
             */

            private String name;
            private String points;
            private String time;
            private String getuid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getGetuid() {
                return getuid;
            }

            public void setGetuid(String getuid) {
                this.getuid = getuid;
            }
        }
    }
}
