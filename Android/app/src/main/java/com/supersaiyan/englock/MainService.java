package com.supersaiyan.englock;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.supersaiyan.englock.locksystem.view.LockView;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class MainService extends Service implements LockView.OnUnlockListener {

    private WindowManager windowManager;
    private LockView lockView;
    private WindowManager.LayoutParams params;
    private boolean isLocking = false;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isLocking) {
                return;
            } else {
                addWindowManager();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
        pushNotifyCation();
        super.onCreate();
    }

    private void pushNotifyCation() {
//        Intent notificationIntent = new Intent(this, ChooseTopicActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentIntent(contentIntent)
//                .setPriority(-2)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(getString(R.string.was_active))
//                .setSmallIcon(R.mipmap.ic_notify, 0)
//                .setWhen(0)
//                .build();
//        startForeground(1995,
//                notification);
    }

    public void addWindowManager() {
        isLocking = true;
        lockView = new LockView(this, -1, LockView.MODE_LOCK_SCREEN, this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        params.flags &= ~WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        params.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

        params.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        params.format = PixelFormat.TRANSPARENT;
        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        windowManager.addView(lockView, params);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    public void stopLock() {
        isLocking = false;
        windowManager.removeViewImmediate(lockView);
        //   stopSelf();
    }

    @Override
    public void onUnlock() {
        stopLock();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
