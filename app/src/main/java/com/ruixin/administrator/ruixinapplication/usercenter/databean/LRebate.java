package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/19.
 * 邮箱：543815830@qq.com
 * 亏损返利的属性
 */

public class LRebate {


    /**
     * status : 1
     * data : {"ksflbl":"2.0000","xxksbl":"1.0000","fanli":32.4,"isgeted":0,"qrfl":0,"sypoints":0,"qrzfl":0,"qryl":0,"group":{"week":[{"id":"13","from":"0","to":"1","percent":"1.00","type":"2"},{"id":"12","from":"1","to":"2","percent":"1.00","type":"2"},{"id":"14","from":"2","to":"3","percent":"2.00","type":"2"},{"id":"15","from":"3","to":"4","percent":"3.00","type":"2"},{"id":"16","from":"4","to":"5","percent":"4.00","type":"2"}]},"system":{"ksfl":"2.0000","qrfl":"1.0000","xxksbl":"1.0000"},"sumxxks":0,"sumxxksfl":0,"isxxgeted":0}
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
        /**
         * ksflbl : 2.0000
         * xxksbl : 1.0000
         * fanli : 32.4
         * isgeted : 0
         * qrfl : 0
         * sypoints : 0
         * qrzfl : 0
         * qryl : 0
         * group : {"week":[{"id":"13","from":"0","to":"1","percent":"1.00","type":"2"},{"id":"12","from":"1","to":"2","percent":"1.00","type":"2"},{"id":"14","from":"2","to":"3","percent":"2.00","type":"2"},{"id":"15","from":"3","to":"4","percent":"3.00","type":"2"},{"id":"16","from":"4","to":"5","percent":"4.00","type":"2"}]}
         * system : {"ksfl":"2.0000","qrfl":"1.0000","xxksbl":"1.0000"}
         * sumxxks : 0
         * sumxxksfl : 0
         * isxxgeted : 0
         */

        private String ksflbl;
        private String qrflbl;
        private String xxksbl;
        private String fanli;
        private String isgeted;
        private String qrfl;
        private String sypoints;
        private String qrzfl;
        private String qryl;
        private GroupBean group;
        private SystemBean system;
        private String sumxxks;
        private String sumxxksfl;
        private String isxxgeted;
        private String qrfled;

        public String getKsflbl() {
            return ksflbl;
        }
        public String getQrflbl() {
            return qrflbl;
        }

        public void setKsflbl(String ksflbl) {
            this.ksflbl = ksflbl;
        }
        public void setQrflbl(String qrflbl) {
            this.qrflbl = qrflbl;
        }

        public String getXxksbl() {
            return xxksbl;
        }

        public void setXxksbl(String xxksbl) {
            this.xxksbl = xxksbl;
        }

        public String getFanli() {
            return fanli;
        }

        public void setFanli(String fanli) {
            this.fanli = fanli;
        }

        public String getIsgeted() {
            return isgeted;
        }

        public void setIsgeted(String isgeted) {
            this.isgeted = isgeted;
        }

        public String getQrfl() {
            return qrfl;
        }

        public void setQrfl(String qrfl) {
            this.qrfl = qrfl;
        }

        public String getSypoints() {
            return sypoints;
        }

        public void setSypoints(String sypoints) {
            this.sypoints = sypoints;
        }

        public String getQrzfl() {
            return qrzfl;
        }

        public void setQrzfl(String qrzfl) {
            this.qrzfl = qrzfl;
        }

        public String getQryl() {
            return qryl;
        }

        public void setQryl(String qryl) {
            this.qryl = qryl;
        }

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public SystemBean getSystem() {
            return system;
        }

        public void setSystem(SystemBean system) {
            this.system = system;
        }

        public String getSumxxks() {
            return sumxxks;
        }

        public void setSumxxks(String sumxxks) {
            this.sumxxks = sumxxks;
        }

        public String getSumxxksfl() {
            return sumxxksfl;
        }

        public void setSumxxksfl(String sumxxksfl) {
            this.sumxxksfl = sumxxksfl;
        }

        public String getIsxxgeted() {
            return isxxgeted;
        }
        public String getQrfled() {
            return qrfled;
        }

        public void setIsxxgeted(String isxxgeted) {
            this.isxxgeted = isxxgeted;
        }
        public void setQrfled(String qrfled) {
            this.qrfled = qrfled;
        }

        public static class GroupBean {
            private List<WeekBean> week;

            public List<WeekBean> getWeek() {
                return week;
            }

            public void setWeek(List<WeekBean> week) {
                this.week = week;
            }

            public static class WeekBean {
                /**
                 * id : 13
                 * from : 0
                 * to : 1
                 * percent : 1.00
                 * type : 2
                 */

                private String id;
                private String from;
                private String to;
                private String percent;
                private String type;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getFrom() {
                    return from;
                }

                public void setFrom(String from) {
                    this.from = from;
                }

                public String getTo() {
                    return to;
                }

                public void setTo(String to) {
                    this.to = to;
                }

                public String getPercent() {
                    return percent;
                }

                public void setPercent(String percent) {
                    this.percent = percent;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class SystemBean {
            /**
             * ksfl : 2.0000
             * qrfl : 1.0000
             * xxksbl : 1.0000
             */

            private String ksfl;
            private String qrfl;
            private String xxksbl;

            public String getKsfl() {
                return ksfl;
            }

            public void setKsfl(String ksfl) {
                this.ksfl = ksfl;
            }

            public String getQrfl() {
                return qrfl;
            }

            public void setQrfl(String qrfl) {
                this.qrfl = qrfl;
            }

            public String getXxksbl() {
                return xxksbl;
            }

            public void setXxksbl(String xxksbl) {
                this.xxksbl = xxksbl;
            }
        }
    }
}
