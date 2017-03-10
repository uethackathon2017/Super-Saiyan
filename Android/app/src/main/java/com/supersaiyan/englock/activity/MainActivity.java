package com.supersaiyan.englock.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityMainBinding;
import com.supersaiyan.englock.view.dialog.AlertDialogPlus;


public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_CODE_REQUEST_PERMISSION = 1;

    private ActivityMainBinding binding;
    private AlertDialogPlus allowDisableAciveDialog = null;
    private AlertDialogPlus requestPermissionDialog = null;

    public boolean actived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        binding.setActived(actived);
        binding.setActivity(this);
        binding.getRoot();
        setContentView(binding.getRoot());
    }

    public void activeClick() {
        if (actived) {
            if (allowDisableAciveDialog == null) {
                allowDisableAciveDialog = AlertDialogPlus.newInstance(this, R.mipmap.ic_launcher, R.string.notify_dialog, R.string.allow_disable_active_mesage, R.string.disable, R.string.cancel, 0);
            }
            allowDisableAciveDialog.show(new AlertDialogPlus.OnClick() {
                @Override
                public void onPositiveClick() {
                    optionLockScreen(false);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    if (requestPermissionDialog == null) {
                        requestPermissionDialog = AlertDialogPlus.newInstance(this, R.mipmap.ic_launcher, R.string.notify_dialog, R.string.request_permission_message, R.string.accept, R.string.cancel, 0);
                    }
                    requestPermissionDialog.show(new AlertDialogPlus.OnClick() {
                        @Override
                        public void onPositiveClick() {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:" + getPackageName()));
                            startActivityForResult(intent, REQUEST_CODE_REQUEST_PERMISSION);
                        }
                    });
                } else {
                    optionLockScreen(true);
                }
            } else optionLockScreen(true);
        }
    }

    public void optionLockScreen(boolean endable) {

        actived = !actived;
        binding.setActived(actived);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_REQUEST_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    optionLockScreen(true);
                }
            }
        }
    }
}
