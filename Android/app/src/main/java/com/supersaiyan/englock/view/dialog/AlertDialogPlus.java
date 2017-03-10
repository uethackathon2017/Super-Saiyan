package com.supersaiyan.englock.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class AlertDialogPlus {

    public static AlertDialogPlus newInstance(Context context, int iconId, int titleId, int messageId, int positiveId, int negativeId, int neutralId) {
        return new AlertDialogPlus(context, iconId, titleId, messageId, positiveId, negativeId, neutralId);
    }

    private AlertDialog alertDialog;
    private OnClick onClick;

    public AlertDialogPlus(Context context, int iconId, int titleId, int messageId, int positiveId, int negativeId, int neutralId) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setIcon(iconId);

        alertDialog.setTitle(titleId);
        alertDialog.setMessage(context.getString(messageId));
        if (positiveId != 0)
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(positiveId), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onClick != null) {
                        onClick.onPositiveClick();
                    }
                }
            });
        if (negativeId != 0)
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(negativeId), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onClick != null) {
                        //   onClick.onNegativeClick();
                    }
                }
            });
        if (neutralId != 0)
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(neutralId), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onClick != null) {
                        //    onClick.onNeutralClick();
                    }
                }
            });
    }

    public void show(OnClick onClick) {
        this.onClick = onClick;
        alertDialog.show();
    }

    public interface OnClick {
        void onPositiveClick();

    }
}
