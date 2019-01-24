package com.aa.test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipHelper.Vibrate(MainActivity.this, new long[]{800, 1000, 800, 1000, 800, 1000, 800, 1000, 800, 1000}, false);
            }
        });
        findViewById(R.id.btn_tuisong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotifictionIcon(MainActivity.this);
            }
        });
    }
    public static void showNotifictionIcon(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, MainActivity.class);//将要跳转的界面
        //Intent intent = new Intent();//只显示通知，无页面跳转
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker("状态栏显示的文字");
        builder.setContentTitle("标题");
        builder.setContentText("通知内容");
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    public void showNotifictionIconAA(Context context) {
       int mNotificationId = hashCode();
       // LogUtils.loge("mNotificationId=" + mNotificationId);
        Intent broadcastIntent = new Intent(context, MainActivity.class);
        broadcastIntent.setAction(Intent.ACTION_VIEW);
        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     //   broadcastIntent.putExtra(AppConstant.PUSH_TYPE, pushBean);
        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setContentTitle("标题")
                .setTicker("bbbbbbbbbbbbbbbbn")
                .setContentText("通知内容")
                .setContentIntent(pendingIntent)
                .setChannelId("zhuhai")//适配8.0
                .setAutoCancel(true)//用户点击就自动消失
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        //适配8.0
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= 26){
            NotificationChannel channel = new NotificationChannel("zhuhai", getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId("zhuhai");
            manager.createNotificationChannel(channel);
        }
        manager.notify(mNotificationId, builder.build());//每次改变mNotificationId的值才能在通知栏产生盖楼的效果
        }


}
