package com.omni.ereadysdk.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.omni.ereadysdk.R;

import java.lang.ref.WeakReference;

public class DialogTools {

    private boolean isTest = false;

    private static DialogTools mDialogTools;
    private ProgressDialog mProgressDialog;
    private AlertDialog mNoNetworkDialog;

    public static DialogTools getInstance() {
        if (mDialogTools == null) {
            mDialogTools = new DialogTools();
        }
        return mDialogTools;
    }

    private AlertDialog createAlertDialog(Context context, String title, String message, int iconRes,
                                          String positiveBtnText, DialogInterface.OnClickListener positiveBtnClickListener,
                                          String negativeBtnText, DialogInterface.OnClickListener negativeBtnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);

        if (iconRes != -1) {
            builder.setIcon(iconRes);
        }

        if (!TextUtils.isEmpty(positiveBtnText) && positiveBtnClickListener != null) {
            builder.setPositiveButton(positiveBtnText, positiveBtnClickListener);
        }

        if (!TextUtils.isEmpty(negativeBtnText) && negativeBtnClickListener != null) {
            builder.setNegativeButton(negativeBtnText, negativeBtnClickListener);
        }

        return builder.create();
    }

    public void showSettingFragment(FragmentManager manager) {

    }

    public void showProgress(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(null);
        }

        WeakReference<Context> weakContext = new WeakReference<>(context);

        if (context != null && !((Activity) context).isFinishing() && weakContext.get() != null
                && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgress(Context context) {
        WeakReference<Context> weakContext = new WeakReference<>(context);

        if (context != null && !((Activity) context).isFinishing() && weakContext.get() != null
                && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showNoNetworkMessage(Context context) {
        if (mNoNetworkDialog == null && context != null) {
            mNoNetworkDialog = createAlertDialog(context,
                    context.getString(R.string.error_dialog_title_text_no_network),
                    context.getString(R.string.error_dialog_message_text_no_network),
                    -1,
                    context.getResources().getString(R.string.dialog_button_ok_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },
                    null, null);
        }

        WeakReference<Context> weakContext = new WeakReference<>(context);

        if (context != null && !((Activity) context).isFinishing() && weakContext.get() != null
                && !mNoNetworkDialog.isShowing()) {
            mNoNetworkDialog.show();
        }
    }

    public void showTestMessage(Context context, @StringRes int titleRes, String message) {
        if (isTest) {
            showErrorMessage(context, titleRes, message);
        }
    }

    public Dialog createErrorMessageDialog(Context context, @StringRes int titleRes, String message) {
        if (context != null && !((Activity) context).isFinishing()) {
            return createAlertDialog(context, context.getResources().getString(titleRes), message, -1,
                    context.getResources().getString(R.string.dialog_button_ok_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },
                    null, null);
        }
        return null;
    }

    public void showErrorMessage(Context context, @StringRes int titleRes, @StringRes int messageRes) {
        showErrorMessage(context, context.getResources().getString(titleRes), context.getResources().getString(messageRes), null);
    }

    public void showErrorMessage(Context context, @StringRes int titleRes, String message) {
        showErrorMessage(context, context.getResources().getString(titleRes), message, null);
    }

    public void showErrorMessage(Context context, String title, String message) {
        showErrorMessage(context, title, message, null);
    }

    public void showErrorMessage(Context context, @StringRes int titleRes, @StringRes int message, @Nullable DialogInterface.OnDismissListener dismissListener) {
        showErrorMessage(context, context.getResources().getString(titleRes), context.getResources().getString(message), -1, dismissListener);
    }

    public void showErrorMessage(Context context, @StringRes int titleRes, String message, @Nullable DialogInterface.OnDismissListener dismissListener) {
        showErrorMessage(context, context.getResources().getString(titleRes), message, -1, dismissListener);
    }

    public void showErrorMessage(Context context, String title, String message, @Nullable DialogInterface.OnDismissListener dismissListener) {
        showErrorMessage(context, title, message, -1, dismissListener);
    }

    public void showErrorMessage(Context context, String title, String message, int iconRes, @Nullable DialogInterface.OnDismissListener dismissListener) {
        WeakReference<Context> weakContext = new WeakReference<>(context);

        if (context != null && !((Activity) context).isFinishing() && weakContext.get() != null) {
            Dialog dialog = createAlertDialog(context, title, message, iconRes,
                    context.getResources().getString(R.string.dialog_button_ok_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    },
                    null, null);

            if (dismissListener != null) {
                dialog.setOnDismissListener(dismissListener);
            }

            dialog.show();
        }
    }
}
