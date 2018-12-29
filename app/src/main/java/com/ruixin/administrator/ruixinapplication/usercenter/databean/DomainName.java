package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/25.
 * 邮箱：543815830@qq.com
 * 域名的属性
 */

public class DomainName {

    /**
     * status : 1
     * data : {"type":-1,"log":[{"id":"9288715","time":"2018-04-19 16:28:39","logtype":"19","log":"域名","points":"0","experience":"0","usersid":"19143","yucoins":"12345"},{"id":"9288716","time":"2018-04-24 16:29:44","logtype":"19","log":"域名","points":"0","experience":"0","usersid":"19143","yucoins":"123456"}]}
     */

    private int status;
    private DataBean data;
    private String totalpage;
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
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
         * type : -1
         *  "zsxlym":"123",//域名的名字
         "zsxldq":"2018-07-01 00:00:00"
         * log : [{"id":"9288715","time":"2018-04-19 16:28:39","logtype":"19","log":"域名","points":"0","experience":"0","usersid":"19143","yucoins":"12345"},{"id":"9288716","time":"2018-04-24 16:29:44","logtype":"19","log":"域名","points":"0","experience":"0","usersid":"19143","yucoins":"123456"}]
         */

        private int type;
        private String zsxlym;
        private String path;
        private String zsxldq;
        private List<LogBean> log;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
        public String getZsxlym() {
            return zsxlym;
        }

        public void setZsxlym(String zsxlym) {
            this.zsxlym = zsxlym;
        }
        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
        public String getZsxldq() {
            return zsxldq;
        }

        public void setZsxldq(String zsxldq) {
            this.zsxldq = zsxldq;
        }

        public List<LogBean> getLog() {
            return log;
        }

        public void setLog(List<LogBean> log) {
            this.log = log;
        }

        public static class LogBean {
            /**
             * id : 9288715
             * time : 2018-04-19 16:28:39
             * logtype : 19
             * log : 域名
             * points : 0
             * experience : 0
             * usersid : 19143
             * yucoins : 12345
             */

            private String id;
            private String time;
            private String logtype;
            private String log;
            private String points;
            private String experience;
            private String usersid;
            private String yucoins;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getLogtype() {
                return logtype;
            }

            public void setLogtype(String logtype) {
                this.logtype = logtype;
            }

            public String getLog() {
                return log;
            }

            public void setLog(String log) {
                this.log = log;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getExperience() {
                return experience;
            }

            public void setExperience(String experience) {
                this.experience = experience;
            }

            public String getUsersid() {
                return usersid;
            }

            public void setUsersid(String usersid) {
                this.usersid = usersid;
            }

            public String getYucoins() {
                return yucoins;
            }

            public void setYucoins(String yucoins) {
                this.yucoins = yucoins;
            }
        }
    }
}
