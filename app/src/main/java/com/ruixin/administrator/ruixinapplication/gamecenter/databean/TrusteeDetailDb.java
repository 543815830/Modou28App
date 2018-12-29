package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/19.
 */

public class TrusteeDetailDb {

    /**
     * status : 1
     * data : {"maxpoint":"0","minpoint":"0","autoid":"3857","startno":"650125","endno":"653122","type":"1","startmodel":"44","modellist":[{"model":"55","winmodel":"44","lostmodel":"44"},{"model":"44","winmodel":"44","lostmodel":"44"},{"model":"640960模式","winmodel":"44","lostmodel":"44"},{"model":"640960模式","winmodel":"44","lostmodel":"44"},{"model":"280","winmodel":"44","lostmodel":"44"}]}
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
         * maxpoint : 0
         * minpoint : 0
         * autoid : 3857
         * startno : 650125
         * endno : 653122
         * type : 1
         * startmodel : 44
         * modellist : [{"model":"55","winmodel":"44","lostmodel":"44"},{"model":"44","winmodel":"44","lostmodel":"44"},{"model":"640960模式","winmodel":"44","lostmodel":"44"},{"model":"640960模式","winmodel":"44","lostmodel":"44"},{"model":"280","winmodel":"44","lostmodel":"44"}]
         */

        private String maxpoint;
        private String minpoint;
        private String maxyl;
        private String minyl;
        private String fbbs;
        private String ddsx;
        private String fbyh;
        private String yl;
        private String tzsx;

        private String name;
        private String autoid;
        private String startno;
        private String endno;
        private String type;
        private String startmodel;
        private List<ModellistBean> modellist;

        public String getMaxpoint() {
            return maxpoint;
        }

        public void setMaxpoint(String maxpoint) {
            this.maxpoint = maxpoint;
        }
  public String getMaxyl() {
            return maxyl;
        }

        public void setMaxyl(String maxyl) {
            this.maxyl = maxyl;
        }  public String getMinyl() {
            return minyl;
        }

        public void setMinyl(String minyl) {
            this.minyl = minyl;
        }public String getFbbs() {
            return fbbs;
        }

        public void setFbbs(String fbbs) {
            this.fbbs = fbbs;
        }public String getDdsx() {
            return ddsx;
        }

        public void setDdsx(String ddsx) {
            this.ddsx = ddsx;
        }public String getFbyh() {
            return fbyh;
        }

        public void setFbyh(String fbyh) {
            this.fbyh = fbyh;
        }public String getYl() {
            return yl;
        }

        public void setYl(String yl) {
            this.yl = yl;
        }public String getTzsx() {
            return tzsx;
        }

        public void setTzsx(String tzsx) {
            this.tzsx = tzsx;
        }

        public String getMinpoint() {
            return minpoint;
        }

        public void setMinpoint(String minpoint) {
            this.minpoint = minpoint;
        }
  public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAutoid() {
            return autoid;
        }

        public void setAutoid(String autoid) {
            this.autoid = autoid;
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
        }

        public List<ModellistBean> getModellist() {
            return modellist;
        }

        public void setModellist(List<ModellistBean> modellist) {
            this.modellist = modellist;
        }

        public static class ModellistBean {
            /**
             * model : 55
             * winmodel : 44
             * lostmodel : 44
             */

            private String num;
            private String model;
            private String winmodel;
            private String lostmodel;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
               public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getWinmodel() {
                return winmodel;
            }

            public void setWinmodel(String winmodel) {
                this.winmodel = winmodel;
            }

            public String getLostmodel() {
                return lostmodel;
            }

            public void setLostmodel(String lostmodel) {
                this.lostmodel = lostmodel;
            }
        }
    }
}
