package com.theleafapps.shopnick.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.Variant;
import com.theleafapps.shopnick.models.multiples.Variants;
import com.theleafapps.shopnick.tasks.GetAllVariantsByProductIdTask;
import com.theleafapps.shopnick.utils.Communicator;
import com.theleafapps.shopnick.utils.LinkedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by aviator on 10/07/16.
 */
public class CartUpdateDialog extends DialogFragment implements View.OnClickListener {

    Communicator comm;
    Bundle bundle;
    Button update,cancel;
    Spinner variantSpinner,quantitySpinner;
    int cart_item_id,product_id;
    String product_name;
    Variants variantRec;
    Map<String,Boolean> variantMap;
    List<String> varList,quantityList;
    ArrayAdapter<String> variantDataAdapter;
    ArrayAdapter<String> quantityDataAdapter;
    TextView product_name_title;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Tangho","View Created");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        bundle              =   getArguments();
        cart_item_id        =   bundle.getInt("cart_item_id");
        product_id          =   bundle.getInt("product_id");
        product_name        =   bundle.getString("product_name");
        View view           =   inflater.inflate(R.layout.update_cart_item_dialog,null);
        variantSpinner      =   (Spinner) view.findViewById(R.id.cart_update_dialog_variant_spinner);
        quantitySpinner     =   (Spinner) view.findViewById(R.id.cart_update_dialog_quantity_spinner);
        update              =   (Button) view.findViewById(R.id.cart_update_dialog_update_button);
        cancel              =   (Button) view.findViewById(R.id.cart_update_dialog_cancel_button);
        product_name_title  =   (TextView) view.findViewById(R.id.update_cart_dialog_product_name_value);
        product_name_title.setText(product_name);
        variantMap          =   new LinkedMap<>();
        varList             =   new ArrayList<>();
        quantityList        =   new ArrayList<>();

        try{

            GetAllVariantsByProductIdTask getAllVariantsByProductIdTask
                        = new GetAllVariantsByProductIdTask(getActivity(),product_id);
            getAllVariantsByProductIdTask.execute().get();
            variantRec  = getAllVariantsByProductIdTask.variantsRec;

            if(variantRec!=null && variantRec.variants.size()>0){
                for (Variant variant : variantRec.variants) {
                    variantMap.put(variant.variant_name, variant.available);
                    varList.add(variant.variant_name);
                }

                variantDataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, varList);
                variantDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                variantSpinner.setAdapter(variantDataAdapter);
            }

            for(int i=1;i<=10;i++){
                quantityList.add(String.valueOf(i));
            }
            quantityDataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, quantityList);
            quantityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quantitySpinner.setAdapter(quantityDataAdapter);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        update.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        try{
            if (v.getId()==R.id.cart_update_dialog_update_button){
                Log.d("Tangho","update button clicked - cart_item_id " + cart_item_id);
            }
            else if (v.getId()==R.id.cart_update_dialog_cancel_button){
                dismiss();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
