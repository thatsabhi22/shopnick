package com.theleafapps.pro.shopnick.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.theleafapps.pro.shopnick.R;

import java.io.Serializable;

/**
 * Created by aviator on 01/08/16.
 */
public class MyProgressDialog extends Dialog implements Serializable {

    public static MyProgressDialog show(Context context, MyProgressDialog progressDialog, CharSequence title,
                                        CharSequence message) {
        return show(context,progressDialog, title, message, false);
    }

    public static MyProgressDialog show(Context context, MyProgressDialog progressDialog, CharSequence title,
                                        CharSequence message, boolean indeterminate) {
        return show(context,progressDialog, title, message, indeterminate, false, null);
    }

    public static MyProgressDialog show(Context context, MyProgressDialog progressDialog, CharSequence title,
                                        CharSequence message, boolean indeterminate, boolean cancelable, Object o) {
        return show(context,progressDialog, title, message, indeterminate, cancelable, null);
    }

    public static MyProgressDialog show(Context context,MyProgressDialog dialog, CharSequence title,
                                        CharSequence message, boolean indeterminate,
                                        boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);

        dialog.setOnCancelListener(cancelListener);
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    public static void dismissDialog(){

    }

    public MyProgressDialog(Context context) {
        super(context, R.style.NewDialog);
    }
}
