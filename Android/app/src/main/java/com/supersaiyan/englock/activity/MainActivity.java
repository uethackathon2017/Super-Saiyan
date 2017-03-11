package com.supersaiyan.englock.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.supersaiyan.englock.MainService;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.api.ServiceImpl;
import com.supersaiyan.englock.databinding.ActivityMainBinding;
import com.supersaiyan.englock.dto.TopicDTO;
import com.supersaiyan.englock.dto.TopicDTOManager;
import com.supersaiyan.englock.locksystem.listener.OnAnswerListener;
import com.supersaiyan.englock.locksystem.view.AnswerToUnlockLayout;
import com.supersaiyan.englock.model.UserConfig;
import com.supersaiyan.englock.storage.DatabaseManager;
import com.supersaiyan.englock.storage.PrefManager;
import com.supersaiyan.englock.util.AnimUtil;
import com.supersaiyan.englock.view.cardswipe.ChooseTopicLayout;
import com.supersaiyan.englock.view.cardswipe.MiniGameLayout;
import com.supersaiyan.englock.view.cardswipe.MoreAppLayout;
import com.supersaiyan.englock.view.dialog.AlertDialogPlus;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_CODE_REQUEST_PERMISSION = 1;

    private ActivityMainBinding binding;
    private AlertDialogPlus allowDisableAciveDialog = null;
    private AlertDialogPlus requestPermissionDialog = null;
    private AlertDialogPlus introduceLockScreenDialog = null;

    private UserConfig userConfig = UserConfig.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        binding.setUserConfig(userConfig);
        binding.setActivity(this);
        binding.getRoot();
        setContentView(binding.getRoot());
        checkData();
        startAnim();
        if (userConfig.isActiveLock()) {
            startService(new Intent(this, MainService.class));
        }

    }

    private void startAnim() {
        ChooseTopicLayout layoutChoiceTopic;
        MoreAppLayout layoutMoreApp;
        MiniGameLayout layoutMiniGame;

        layoutChoiceTopic = binding.layoutChoiceTopic;
        layoutMoreApp = binding.layoutMoreApp;
        layoutMiniGame = binding.layoutMiniGame;


        layoutChoiceTopic.setHideRestOthersEnable(true);
        layoutMoreApp.setHideRestOthersEnable(true);
        layoutMiniGame.setHideRestOthersEnable(true);

        layoutChoiceTopic.setFirstResetEnable(true);
        layoutMoreApp.setFirstResetEnable(true);
        layoutMiniGame.setFirstResetEnable(true);
        AnimUtil.postAnimationBottom(layoutMoreApp, 200, 1100);
        AnimUtil.postAnimationBottom(layoutMiniGame, 150, 1000);
        AnimUtil.postAnimationBottom(layoutChoiceTopic, 100, 900);
    }

    public void checkData() {
        if (PrefManager.getInstance().isFirtTimeLaunch()) {
            final ProgressDialog progressDialog = ProgressDialog.show(this, getString(R.string.preaparing_data), getString(R.string.please_wait));
            progressDialog.show();
            new TopicDTOManager().getTopicsAsync(new TopicDTOManager.OnTopicLoadListener() {
                @Override
                public void onTopicLoadSuccess(final ArrayList<TopicDTO> topics) {
                    DatabaseManager databaseManager = DatabaseManager.getInstance();
                    for (TopicDTO topicDTO : topics) {
                        databaseManager.insertNewTopic(topicDTO.getTopic(), topicDTO.getWords());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                }

                @Override
                public void onTopicLoadFailure() {

                }
            });
        }

    }

    public void activeClick() {
        if (userConfig.isActiveLock()) {
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
        if (endable) {
            startService(new Intent(this, MainService.class));
            if (introduceLockScreenDialog == null) {
                introduceLockScreenDialog = AlertDialogPlus.newInstance(this, R.mipmap.ic_launcher, R.string.active_success, R.string.introduce_lock_screen, android.R.string.ok, 0, 0);
            }
            introduceLockScreenDialog.show(null);

        } else {
            stopService(new Intent(MainActivity.this, MainService.class));
        }
        userConfig.setActiveLock(!userConfig.isActiveLock());
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

    public void chooseTopicClick() {
        Intent intent = new Intent(this, TopicActivity.class);
        startActivity(intent);
    }

    public void findWordByLocation() {
        Intent intent = new Intent(this, FindWordByLocationActivity.class);
        startActivity(intent);
    }

    public void communicationClick() {
        startActivity(new Intent(this, CommunicationActivity.class));
    }
}
