package com.theleafapps.pro.shopnick.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.utils.Communicator;

/**
 * Created by aviator on 20/07/16.
 */
public class CouponCodesDialog extends DialogFragment implements View.OnClickListener {

    Communicator comm;
    Bundle bundle;
    Button ok;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        bundle      =   getArguments();
        View view   =   inflater.inflate(R.layout.dialog_layout_coupon_codes,null);
        ok          =   (Button) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ok){
                dismiss();
        }
    }
}
