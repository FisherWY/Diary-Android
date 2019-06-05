package com.fisher.finaldiary.DataBase;

import android.database.sqlite.SQLiteDatabase;
import com.fisher.finaldiary.MainActivity;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * @Author Fisher
 * @Date 2019/6/5 11:42
 **/


public class dbManager {

    public void initDataBase() {
        LitePal.getDatabase();
    }
}
