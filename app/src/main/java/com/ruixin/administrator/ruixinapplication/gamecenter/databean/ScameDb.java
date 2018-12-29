package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

/**
 * Created by 李丽 on 2018/11/20.
 */

public class ScameDb {
    private String id;
    private String name;

    public ScameDb(String id, String name) {
        this.id = id;
        this.name = name;
    }

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
    private boolean isFlag;

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
