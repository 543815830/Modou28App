package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

/**
 * Created by 李丽 on 2018/11/7.
 */

public class MoneyDb {
    private String money;
    private int number;


    public MoneyDb() {
    }



    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setUsername(int number) {
        this.number = number;
    }

    public MoneyDb(int number,String money) {

        this.number = number;
        this.money = money;
    }
}
