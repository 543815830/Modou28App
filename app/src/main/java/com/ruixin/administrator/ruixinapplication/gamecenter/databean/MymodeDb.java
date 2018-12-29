package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/13.
 */

public class MymodeDb {

    /**
     * status : 1
     * data : {"curno":"641091","modellist":[{"modelname":"44","modelid":"5268"},{"modelname":"55","modelid":"5270"},{"modelname":"640960模式","modelid":"5292"},{"modelname":"640960模式","modelid":"5293"}],"gamename":"xt11"}
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
         * curno : 641091
         * modellist : [{"modelname":"44","modelid":"5268"},{"modelname":"55","modelid":"5270"},{"modelname":"640960模式","modelid":"5292"},{"modelname":"640960模式","modelid":"5293"}]
         * gamename : xt11
         */

        private String curno;
        private String gamename;
        private List<ModellistBean> modellist;

        public String getCurno() {
            return curno;
        }

        public void setCurno(String curno) {
            this.curno = curno;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public List<ModellistBean> getModellist() {
            return modellist;
        }

        public void setModellist(List<ModellistBean> modellist) {
            this.modellist = modellist;
        }

        public static class ModellistBean {
            /**
             * modelname : 44
             * modelid : 5268
             */

            private String modelname;
            private String modelid;
            private String tzpoints;

            public ModellistBean(String modelname) {
                this.modelname = modelname;
            }

            public String getModelname() {
                return modelname;
            }

            public void setModelname(String modelname) {
                this.modelname = modelname;
            }

            public String getModelid() {
                return modelid;
            }
            public String getTzpoints() {
                return tzpoints;
            }

            public void setTzpoints(String tzpoints) {
                this.tzpoints = tzpoints;
            }
            public void setModelid(String modelid) {
                this.modelid = modelid;
            }
        }
    }
}
