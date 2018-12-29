package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/20.
 * 邮箱：543815830@qq.com
 */

public class Signed {

    /**
     * status : 1
     * msg : 恭喜您,签到成功!
     * data : {"qdarr":["2018-03-02","2018-04-20"],"user":{"points":"35994","maxexperience":"0","qddate":"2018-04-20","lxqd":"2"},"ljqdhd":"1"}
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
         * qdarr : ["2018-03-02","2018-04-20"]
         * user : {"points":"35994","maxexperience":"0","qddate":"2018-04-20","lxqd":"2"}
         * ljqdhd : 1
         */

        private UserBean user;
        private String ljqdhd;
        private List<String> qdarr;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getLjqdhd() {
            return ljqdhd;
        }

        public void setLjqdhd(String ljqdhd) {
            this.ljqdhd = ljqdhd;
        }

        public List<String> getQdarr() {
            return qdarr;
        }

        public void setQdarr(List<String> qdarr) {
            this.qdarr = qdarr;
        }

        public static class UserBean {
            /**
             * points : 35994
             * maxexperience : 0
             * qddate : 2018-04-20
             * lxqd : 2
             */

            private String points;
            private String maxexperience;
            private String qddate;
            private String lxqd;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getMaxexperience() {
                return maxexperience;
            }

            public void setMaxexperience(String maxexperience) {
                this.maxexperience = maxexperience;
            }

            public String getQddate() {
                return qddate;
            }

            public void setQddate(String qddate) {
                this.qddate = qddate;
            }

            public String getLxqd() {
                return lxqd;
            }

            public void setLxqd(String lxqd) {
                this.lxqd = lxqd;
            }
        }
    }
}
