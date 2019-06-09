package com.fisher.finaldiary.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.fisher.finaldiary.Adapter.DiaryAdapter;
import com.fisher.finaldiary.DataBase.DiaryModel;
import com.fisher.finaldiary.DataBase.dbManager;
import com.fisher.finaldiary.R;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private static final dbManager dbManager = new dbManager();

    private List<DiaryModel> dbList;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;

    private LinearLayoutManager manager;

    private DiaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // LitePal初始化
        LitePal.initialize(this);
        dbManager.initDataBase();

        // 顶部工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 右下角浮动按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开写日记页面
                toWrite();
//                saveDiary();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        // 主界面
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // 左侧导航栏
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_me);
        navigationView.setNavigationItemSelectedListener(this);

        // 加载日记列表
        swipeRefreshLayout = findViewById(R.id.homeSwiper);
        recyclerView = findViewById(R.id.diaryList);
        manager = new LinearLayoutManager(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        initDiary();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 右上角三个点按钮事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 侧边栏点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_me) {
            // 刷新日记列表
            onRefresh();
        } else if (id == R.id.nav_setting) {
            // 跳转到设置界面
            toSettings();
        } else if (id == R.id.nav_about) {
            // 弹出关于
            showAbout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        dbList.clear();
        dbList = LitePal.findAll(DiaryModel.class);
        adapter = new DiaryAdapter(dbList);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    // 弹出关于对话框
    private void showAbout() {
        final AlertDialog.Builder about = new AlertDialog.Builder(this);
        about.setIcon(R.drawable.ic_menu_send);
        about.setTitle("关于日记时刻");
        about.setMessage("日记时刻的安卓版");
        about.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 没啥事好做的
            }
        });
        about.show();
    }

    // 保存日记
    private void saveDiary() {
        DiaryModel model = new DiaryModel();
        // 统计行数
        int row = LitePal.count(DiaryModel.class);
        model.setId(row + 1);
        model.setTitle("GO SPURS GO");
        model.setMainText("eeqwdqwqeqw");
        model.setMood(0);
        model.setWeather(0);
        model.setDate(new Date());

        if (model.save()) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 跳转到设置页面
    private void toSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    // 跳转到写日记界面
    private void toWrite() {
        Intent intent = new Intent(MainActivity.this, WriteActivity.class);
        startActivity(intent);
    }

    // 加载日记列表
    private void initDiary() {
        dbList = LitePal.findAll(DiaryModel.class);
        adapter = new DiaryAdapter(dbList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
