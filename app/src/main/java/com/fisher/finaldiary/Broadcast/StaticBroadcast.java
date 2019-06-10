package com.fisher.finaldiary.Broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.fisher.finaldiary.Activity.WriteActivity;
import com.fisher.finaldiary.R;
import com.fisher.finaldiary.Service.NotificationService;

public class StaticBroadcast extends BroadcastReceiver {

    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        // 启动日记服务
        Toast.makeText(context, "日记时刻启动成功", Toast.LENGTH_SHORT).show();
        Intent serviceIntent = new Intent(context, NotificationService.class);
        context.startService(serviceIntent);

        // 通知
        if (intent.getAction().equals("WRITE_NOTIFICATION")) {
            Intent writeIntent = new Intent(context, WriteActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, writeIntent, 0);
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 如果SDK版本支持notification channel，创建channel通道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel("diary_write", "日记时刻前台服务通知", NotificationManager.IMPORTANCE_HIGH));
            }
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "diary_write")
                    .setContentTitle("今天写日记了吗？")
                    .setContentText("下面开始写今天的日记吧")
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
            manager.notify(2, notification.build());
        }
    }
}
