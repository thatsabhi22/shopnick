package com.theleafapps.shopnick.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.tasks.DeleteCartItemByIdTask;
import com.theleafapps.shopnick.utils.Communicator;

/**
 * Created by aviator on 29/11/15.
 */
public class SimpleDialogClass extends DialogFragment implements View.OnClickListener{

    Bundle bundle;
    Button yes,no;
    Communicator comm;
    int cart_item_id;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        bundle      =   getArguments();
        View view   =   inflater.inflate(R.layout.dialog_layout,null);
        yes         =   (Button) view.findViewById(R.id.deleteQueryYes);
        no          =   (Button) view.findViewById(R.id.deleteQueryNo);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        setCancelable(false);
        return view;
    }


    @Override
    public void onClick(View v) {
        try{
            if (v.getId()==R.id.deleteQueryYes){
                cart_item_id = bundle.getInt("cart_item_id");

                DeleteCartItemByIdTask deleteCartItemByIdTask = new DeleteCartItemByIdTask(getActivity().getApplicationContext(),cart_item_id);
                deleteCartItemByIdTask.execute().get();

                Toast.makeText(getActivity(),"CartItem Deleted",Toast.LENGTH_SHORT).show();
                bundle.clear();
                comm.dialogMessage("Yes Was Clicked with position");
                dismiss();
            }
            else{
                dismiss();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
