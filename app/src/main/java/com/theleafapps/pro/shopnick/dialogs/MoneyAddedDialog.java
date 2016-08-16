package com.theleafapps.pro.shopnick.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.theleafapps.pro.shopnick.R;
import com.theleafapps.pro.shopnick.ui.CheckoutActivity;
import com.theleafapps.pro.shopnick.utils.Communicator;

/**
 * Created by aviator on 21/07/16.
 */
public class MoneyAddedDialog extends DialogFragment implements View.OnClickListener {

    Communicator comm;
    Bundle bundle;
    Button ok;
    double cart_total;
    TextView money_tv;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        bundle      =   getArguments();
        cart_total  =   bundle.getDouble("wallet_value");
        View view   =   inflater.inflate(R.layout.dialog_layout_wallet_money_added,null);
        ok          =   (Button) view.findViewById(R.id.ok_money_added_button);
        money_tv    =   (TextView) view.findViewById(R.id.money_added_text);
        money_tv.setText("Your Wallet has been updated to \nRs. "+ cart_total +"\n\nHappy Shopping !!");
        ok.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ok_money_added_button){
            dismiss();
            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
