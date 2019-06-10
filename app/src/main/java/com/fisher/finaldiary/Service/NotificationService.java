package com.fisher.finaldiary.Service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.fisher.finaldiary.Activity.WriteActivity;
import com.fisher.finaldiary.Broadcast.StaticBroadcast;
import com.fisher.finaldiary.R;

import java.util.Calendar;
import java.util.Date;

public class NotificationService extends Service {

    private NotificationManager manager;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 启动快速写日记服务
        quickWrite();

        // 启动每日定时提醒写日记服务
        notifyWrite();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void quickWrite() {
        Intent intent = new Intent(this, WriteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // 如果SDK版本支持notification channel，创建channel通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("diary_notification", "日记时刻前台服务通知", NotificationManager.IMPORTANCE_LOW));
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "diary_notification")
                .setContentTitle("快速写日记")
                .setContentText("点击此处可快速写今天的日记")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent);
        startForeground(1, notification.build());
    }

    private void notifyWrite() {
        SharedPreferences preferences = getSharedPreferences("com.fisher.finaldiary_preferences", MODE_PRIVATE);
        boolean notice = preferences.getBoolean("notifications_new_message", false);
        if (notice) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            // 工作类型
            int type = AlarmManager.RTC_WAKEUP;
            // 任务触发时间 -> 每天8点
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR, 8);
            long triggerAtMillis = calendar.getTimeInMillis();
            // 执行间隔 -> 1天
            long intervalMillis = 24 * 60 * 60 * 1000;
            // 执行任务
            Intent intent = new Intent(this, StaticBroadcast.class);
            intent.setAction("WRITE_NOTIFICATION");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            alarmManager.setInexactRepeating(type, triggerAtMillis, intervalMillis, pendingIntent);
        }
    }
}
