package com.theleafapps.shopnick.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.models.CartItem;
import com.theleafapps.shopnick.models.Variant;
import com.theleafapps.shopnick.models.multiples.CartItems;
import com.theleafapps.shopnick.models.multiples.Variants;
import com.theleafapps.shopnick.tasks.GetAllVariantsByProductIdTask;
import com.theleafapps.shopnick.tasks.UpdateCartItemTask;
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
    int cart_item_id,product_id,c_id;
    String product_name,c_id_str;
    Variants variantRec;
    Map<String,Boolean> variantMap;
    List<String> varList,quantityList;
    ArrayAdapter<String> variantDataAdapter;
    ArrayAdapter<String> quantityDataAdapter;
    TextView product_name_title,variant_available_tv;
    String variant,quantity;


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
        View view           =   inflater.inflate(R.layout.dialog_layout_update_cart_item,null);
        variantSpinner      =   (Spinner) view.findViewById(R.id.cart_update_dialog_variant_spinner);
        quantitySpinner     =   (Spinner) view.findViewById(R.id.cart_update_dialog_quantity_spinner);
        update              =   (Button) view.findViewById(R.id.cart_update_dialog_update_button);
        cancel              =   (Button) view.findViewById(R.id.cart_update_dialog_cancel_button);
        product_name_title  =   (TextView) view.findViewById(R.id.update_cart_dialog_product_name_value);
        variant_available_tv=   (TextView) view.findViewById(R.id.variant_available_tv);
        product_name_title.setText(product_name);
        variantMap          =   new LinkedMap<>();
        varList             =   new ArrayList<>();
        quantityList        =   new ArrayList<>();

        try{

            SharedPreferences sharedPreferences
                            =   getActivity().getSharedPreferences("Shopnick", Context.MODE_PRIVATE);
            c_id_str        =   sharedPreferences.getString("cid", "");

            if(!TextUtils.isEmpty(c_id_str))
                c_id        =   Integer.parseInt(c_id_str);

            GetAllVariantsByProductIdTask getAllVariantsByProductIdTask
                        = new GetAllVariantsByProductIdTask(getActivity(),product_id);
            getAllVariantsByProductIdTask.execute().get();
            variantRec  = getAllVariantsByProductIdTask.variantsRec;

            varList.add("-");
            if(variantRec!=null && variantRec.variants.size()>0) {
                for (Variant variant : variantRec.variants) {
                    variantMap.put(variant.variant_name, variant.available);
                    varList.add(variant.variant_name);
                }
            }else{
                varList.add("Standard");
                variantMap.put("Standard", true);
            }
                variantDataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, varList);
                variantDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                variantSpinner.setAdapter(variantDataAdapter);


            for(int i=1;i<=10;i++){
                quantityList.add(String.valueOf(i));
            }

            quantityDataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, quantityList);
            quantityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quantitySpinner.setAdapter(quantityDataAdapter);

            quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    quantity = quantityList.get(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            variantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    boolean avl = false;
                    String item = varList.get(position);
                    if(!(variantMap.get(item) == null)) {
                        avl = variantMap.get(item);
                    }
                    if(position == 0){
                        variant_available_tv.setVisibility(View.VISIBLE);
                        variant_available_tv.setTextColor(Color.BLUE);
                        variant_available_tv.setText("Select size");
                    } else {
                        if(avl){
                            variant = item;
                            variant_available_tv.setVisibility(View.VISIBLE);
                            variant_available_tv.setTextColor(Color.GREEN);
                            variant_available_tv.setText("In Stock");
                        }else{
                            variant = null;
                            variant_available_tv.setVisibility(View.VISIBLE);
                            variant_available_tv.setTextColor(Color.RED);
                            variant_available_tv.setText("Out Of Stock");
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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

//                Log.d("Tangho","update button clicked - cart_item_id " +
//                        cart_item_id + "variant " + variant + " quantity " + quantity
//                 + "product_id "+ product_id);

                if(variant != null){

                    CartItem updateCartItem         =   new CartItem();
                    updateCartItem.cart_item_id     =   cart_item_id;
                    updateCartItem.variant          =   variant;
                    updateCartItem.quantity         =   Integer.parseInt(quantity);
                    updateCartItem.product_id       =   product_id;
                    updateCartItem.customer_id      =   c_id;

                    CartItems cartItems = new CartItems();
                    cartItems.cartItemList.add(updateCartItem);

                    UpdateCartItemTask updateCartItemTask = new UpdateCartItemTask(getActivity(),cartItems);
                    updateCartItemTask.execute().get();

                    Toast.makeText(getActivity(),"Your Cart has Been Updated",Toast.LENGTH_SHORT).show();
                    comm.dialogMessage("Yes Was Clicked with position");

                    dismiss();
                }
            }
            else if (v.getId()==R.id.cart_update_dialog_cancel_button){
                dismiss();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
