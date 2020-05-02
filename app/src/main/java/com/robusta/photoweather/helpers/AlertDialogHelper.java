package com.robusta.photoweather.helpers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.robusta.photoweather.R;


public class AlertDialogHelper {

    public static void showAlert(Context context, String title, String message, String positive, String negative, final Callback callback) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancel();
                    }
                })
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSuccess();
                    }
                }).show();
    }

    public static void showAlert(Context context, String title, String message, String positive, final Callback callback) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSuccess();
                    }
                }).show();
    }

    public static void showAlert(Context context, String title, String message, String positive) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //callback.onSuccess();
                    }
                }).show();
    }

    /*public static void showSuccessAlert(Context context, String message, String positive) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((TextView)dialog.findViewById(R.id.title)).setText(message);
        ((AppCompatButton)(dialog.findViewById(R.id.bt_close))).setText(positive);

        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public static void showSuccessWithListenerAlert(Context context, String message, String positive, final Callback callback) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((TextView)dialog.findViewById(R.id.title)).setText(message);
        ((AppCompatButton)(dialog.findViewById(R.id.bt_close))).setText(positive);

        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                callback.onSuccess();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public static void showWarningAlert(Context context, String message, String negative) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((TextView)dialog.findViewById(R.id.title)).setText(message);
        ((AppCompatButton)(dialog.findViewById(R.id.bt_close))).setText(negative);

        (dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }*/

    /*public static void showSuccessAlert(Context context, String title, String message, String positive) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }*/

    public interface Callback {

        public void onSuccess();
        public void onCancel();

    }

}
