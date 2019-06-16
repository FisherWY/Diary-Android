package com.fisher.finaldiary.DataBase;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

public class DiaryModel extends LitePalSupport implements Serializable {
    // 日记id
    private int id;
    // 日记题目
    private String title;
    // 日记正文
    private String mainText;
    // 日记时间
    private Date date;
    // 心情
    private int mood;
    // 天气
    private int weather;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }
}
