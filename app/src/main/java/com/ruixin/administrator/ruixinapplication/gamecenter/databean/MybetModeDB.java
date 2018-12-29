package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/22.
 */

public class MybetModeDB {


    /**
     * status : 1
     * data : {"modeljson":[{"ID":"1755","modelID":"1","modelName":"22","modelPoints":"157","moneyPoints":["0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","69","63","0","0","0","0","0","15","10","0","0","0"]},{"ID":"1757","modelID":"2","modelName":"96","modelPoints":"109","moneyPoints":["0","3","6","100","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},{"ID":"1770","modelID":"4","modelName":"66","modelPoints":"561","moneyPoints":["0","0","0","0","0","0","561","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},{"ID":"1771","modelID":"5","modelName":"222","modelPoints":"280","moneyPoints":["0","0","0","0","0","0","280","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},{"ID":"1772","modelID":"6","modelName":"28","modelPoints":"280","moneyPoints":["0","0","280","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},{"ID":"1773","modelID":"7","modelName":"280","modelPoints":"340","moneyPoints":["0","0","280","0","0","60","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]}],"standodd":[1000,333.3333,166.6667,100,66.6667,47.619,35.7143,27.7778,22.2222,18.1818,15.873,14.4928,13.6986,13.3333,13.3333,13.6986,14.4928,15.873,18.1818,22.2222,27.7778,35.7143,47.619,66.6667,100,166.6667,333.3333,1000]}
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
        private List<ModeljsonBean> modeljson;


        public List<ModeljsonBean> getModeljson() {
            return modeljson;
        }

        public void setModeljson(List<ModeljsonBean> modeljson) {
            this.modeljson = modeljson;
        }
        public static class ModeljsonBean {
            /**
             * ID : 1755
             * modelID : 1
             * modelName : 22
             * modelPoints : 157
             * moneyPoints : ["0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","69","63","0","0","0","0","0","15","10","0","0","0"]
             */

            private String ID;
            private String modelID;
            private String modelName;
            private String modelPoints;
            private List<String> moneyPoints;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getModelID() {
                return modelID;
            }

            public void setModelID(String modelID) {
                this.modelID = modelID;
            }

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
            }

            public String getModelPoints() {
                return modelPoints;
            }

            public void setModelPoints(String modelPoints) {
                this.modelPoints = modelPoints;
            }

            public List<String> getMoneyPoints() {
                return moneyPoints;
            }

            public void setMoneyPoints(List<String> moneyPoints) {
                this.moneyPoints = moneyPoints;
            }
        }
    }
}
