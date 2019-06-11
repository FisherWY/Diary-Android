package com.fisher.finaldiary.Adapter;

import com.fisher.finaldiary.R;

/**
 * @Author Fisher
 * @Date 2019/6/11 17:30
 **/


public class MoodAdapter {

    private String[] mood = {"开心", "伤心", "爱心", "调皮", "生病"};

    private int[] resources = {R.drawable.mood_happy, R.drawable.mood_sad, R.drawable.mood_love, R.drawable.mood_naughty, R.drawable.mood_sick};

    public String getMood(int pos) {
        return mood[pos];
    }

    public int getResources(int pos) {
        return resources[pos];
    }
}
