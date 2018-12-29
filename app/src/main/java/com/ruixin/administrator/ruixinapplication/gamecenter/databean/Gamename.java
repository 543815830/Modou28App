package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

/**
 * Created by ning on 2016/4/24.
 */
public class Gamename {
    private String username;
    private String pinyin;
    private String firstLetter;

    public Gamename() {
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }


    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gamename(String firstLetter, String pinyin, String username) {
        this.firstLetter = firstLetter;
        this.pinyin = pinyin;
        this.username = username;
    }
}
