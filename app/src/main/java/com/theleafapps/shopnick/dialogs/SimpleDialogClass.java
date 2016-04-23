//package com.theleafapps.shopnick.dialogs;
//
//import android.app.Activity;
//import android.app.DialogFragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.theleafapps.shopnick.R;
//
///**
// * Created by aviator on 29/11/15.
// */
//public class SimpleDialogClass extends DialogFragment implements View.OnClickListener{
//
//    Bundle bundle;
//    Button yes,no;
//    Communicator comm;
//    int position;
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        comm = (Communicator) activity;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        bundle      =   getArguments();
//        View view   =   inflater.inflate(R.layout.dialog_layout,null);
//        yes         =   (Button) view.findViewById(R.id.yes);
//        no          =   (Button) view.findViewById(R.id.no);
//        yes.setOnClickListener(this);
//        no.setOnClickListener(this);
//        setCancelable(false);
//        return view;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        //dbHelper = new DbHelper(getActivity().getApplicationContext());
//        try{
//            if (v.getId()==R.id.yes){
//                position = bundle.getInt("position");
//
//                //dbHelper.deleteNote(MainActivityold.noteIdList.get(position));
//
//                Toast.makeText(getActivity().getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
//                bundle.clear();
//                dismiss();
////                comm.dialogMessage("Yes Was Clicked with position - " + position);
//            }
//            else{
//                dismiss();
//                comm.dialogMessage("No was clicked.");
//                //Toast.makeText(getActivity(),"No Was Clicked",Toast.LENGTH_LONG).show();
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    interface Communicator{
//        public void dialogMessage(String msg);
//    }
//}
