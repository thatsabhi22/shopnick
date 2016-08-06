package com.theleafapps.shopnick.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;

import com.theleafapps.shopnick.dialogs.MyProgressDialog;
import com.theleafapps.shopnick.models.SubCategory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by aviator on 23/04/16.
 */
public class Commons {

    public static MyProgressDialog progressDialog;

    public static int cartItemCount;

    public static String productListTitle;

    public static LinkedMap<Integer,List<SubCategory>> catIdToSubCatMap = new LinkedMap<>();

    public static  final CharSequence[] sort_options = {
            "Popularity", "Price Low to High", "Price High to Low", "Discount High to Low"
    };

    public static boolean hasActiveInternetConnection(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (isConnected) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000);
                urlc.connect();

                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("Tangho", "Error checking internet connection", e);
            }
        } else {
            Log.d("Tangho", "No network available!");
        }
        return false;
    }

    public static String getAdrId(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static void showProgressDialog(ProgressDialog dialog){
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

//    String key      =   "dumlagakehaisha";
//    //            String encMsg   =   EncryptUtils.xorMessage(android_id,key);
//    String encMsg   =   "1";
//
//    Log.d("Tangho", encMsg + " XOR-ed to: " + (encMsg = EncryptUtils.xorMessage(encMsg, key)));
//
//    String encoded = EncryptUtils.base64encode(encMsg);
//
//    Log.d("Tangho",
//            " is encoded to: " + encoded + " and that is decoding to: " +
//            (encMsg = EncryptUtils.base64decode(encoded)));
//    Log.d("Tangho","XOR-ing back to original: " + EncryptUtils.xorMessage(encMsg, key));
}