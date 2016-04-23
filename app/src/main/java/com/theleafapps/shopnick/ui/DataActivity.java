//package com.theleafapps.shopnick.ui;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.theleafapps.shopnick.R;
//import com.theleafapps.shopnick.models.UserRecord;
//import com.theleafapps.shopnick.models.multiples.UserRecords;
//import com.theleafapps.shopnick.utils.AppConstants;
//import com.theleafapps.shopnick.utils.PrefUtil;
//
//import dfapi.ApiException;
//import dfapi.ApiInvoker;
//import dfapi.BaseAsyncRequest;
//
//public class DataActivity extends AppCompatActivity {
//
//    EditText first_name,last_name,image_url,twitter,facebook,notes,skype;
//    Button insertButton,updateButton,webDataButton;
//    AddUserRecordTask addUserRecordTask;
//    UserRecord userRecord;
//    int recordId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data);
//
//        userRecord = new UserRecord();
//        buildViews();
//
//        Intent intent = getIntent();
//        int position  = intent.getIntExtra("position",-1);
//
//        if(position == -1){
//            updateButton.setVisibility(View.GONE);
//        }
//        else{
//            insertButton.setVisibility(View.GONE);
//            UserRecord record = DataListActivity.userRecordListRec.get(position);
//            recordId = record.id;
//            if(record != null){
//                first_name.setText(record.first_name);
//                last_name.setText(record.last_name);
//                image_url.setText(record.image_url);
//                twitter.setText(record.twitter);
//                skype.setText(record.skype);
//                facebook.setText(record.facebook);
//                notes.setText(record.notes);
//            }
//        }
//
//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                userRecord.id         = recordId;
//                userRecord.first_name = first_name.getText().toString();
//                userRecord.last_name  = last_name.getText().toString();
//                userRecord.image_url  = image_url.getText().toString();
//                userRecord.twitter    = twitter.getText().toString();
//                userRecord.skype      = skype.getText().toString();
//                userRecord.facebook   = facebook.getText().toString();
//                userRecord.notes      = notes.getText().toString();
//
//                UpdateUserRecordTask updateUserRecordTask = new UpdateUserRecordTask(userRecord);
//                updateUserRecordTask.execute();
//            }
//        });
//
//        webDataButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),WebActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void buildViews() {
//        insertButton  = (Button) findViewById(R.id.insertDataButton);
//        updateButton  = (Button) findViewById(R.id.updateDataButton);
//        webDataButton = (Button) findViewById(R.id.parseWebData);
//
//        first_name  = (EditText) findViewById(R.id.first_name);
//        last_name   = (EditText) findViewById(R.id.last_name);
//        image_url   = (EditText) findViewById(R.id.image_url);
//        twitter     = (EditText) findViewById(R.id.twitter);
//        skype       = (EditText) findViewById(R.id.skype);
//        facebook    = (EditText) findViewById(R.id.facebook);
//        notes       = (EditText) findViewById(R.id.notes);
//    }
//
//
////            .setOnClickListener(new View.OnClickListener() {
////        @Override
////        public void onClick(View v) {
////            Activity tmp = (Activity) v.getTag();
////            if (changedContact) {
////                setResult(Activity.RESULT_OK);
////            } else {
////                setResult(Activity.RESULT_CANCELED);
////            }
////            tmp.finish();
////        }
////    });
//    public void insertData(View view){
//        DataActivity tmp    = new DataActivity();
//        addUserRecordTask   = new AddUserRecordTask(tmp);
//        addUserRecordTask.execute();
//    }
//
//    private class UpdateUserRecordTask extends BaseAsyncRequest {
//
//        UserRecord userRecord;
//        public UpdateUserRecordTask(UserRecord record){
//            userRecord = record;
//        }
//
//        @Override
//        protected void doSetup() throws ApiException{
//            callerName = "updateUserRecordTask";
//
//            serviceName = AppConstants.DB_SVC;
//            endPoint = "user_record";
//
//            verb = "PATCH";
//
//            // send the contact record in the body
//            UserRecords userRecords = new UserRecords();
//            userRecords.userRecord.add(userRecord);
//
//            requestString = ApiInvoker.serialize(userRecords);
//
//            // include sessionToken
//            applicationApiKey = AppConstants.API_KEY;
//            sessionToken = PrefUtil.getString(getApplicationContext(), AppConstants.SESSION_TOKEN);
//        }
//
//        @Override
//        protected void onCompletion(boolean success) {
//            if(success){
//                Toast.makeText(getApplicationContext(),"Record has been updated",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(),DataListActivity.class);
//                startActivity(intent);
//            }
//            else{
//                Toast.makeText(getApplicationContext(),"Error updating Record",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private class AddUserRecordTask extends BaseAsyncRequest {
//        private DataActivity dataActivity;
//        private int userRecordId;
//        // once you add the contact and get back the contact id, finish the activity
//        public AddUserRecordTask(DataActivity activity){
//            dataActivity = activity;
//        }
//        @Override
//        protected void doSetup() throws ApiException {
//            callerName = "AddUserRecordTask";
//
//            serviceName = AppConstants.DB_SVC;
//            endPoint = "user_record";
//
//            verb = "POST";
//
//            // build contact record, don't have id yet so can't provide one
//            userRecord              = new UserRecord();
//            userRecord.first_name   = userRecord.getNonNull(first_name.getText().toString());
//            userRecord.last_name    = userRecord.getNonNull(last_name.getText().toString());
//            userRecord.skype        = userRecord.getNonNull(skype.getText().toString());
//            userRecord.twitter      = userRecord.getNonNull(twitter.getText().toString());
//            userRecord.facebook     = userRecord.getNonNull(facebook.getText().toString());
//            userRecord.notes        = userRecord.getNonNull(notes.getText().toString());
//            userRecord.image_url    = userRecord.getNonNull(image_url.getText().toString());
//
//            UserRecords userRecords = new UserRecords();
//            userRecords.userRecord.add(userRecord);
//
//            requestString           = ApiInvoker.serialize(userRecords);
//            requestString           = requestString.replace("\"id\":0,","");
//
//            applicationApiKey       = AppConstants.API_KEY;
//            sessionToken            = PrefUtil.getString(getApplicationContext(), AppConstants.SESSION_TOKEN);
//        }
//
//        @Override
//        protected void processResponse(String response) throws ApiException, org.json.JSONException {
//            // response has whole contact record, but we just want the id
//            UserRecords records = (UserRecords) ApiInvoker.deserialize(response, "", UserRecords.class);
//            if(records.userRecord.size() > 0 && records.userRecord !=null)
//            userRecordId        = records.userRecord.get(0).id;
//            Log.d("Tagho","The Recieved Id is " + userRecordId);
//        }
//
//
//        @Override
//        protected void onCompletion(boolean success) {
//            if(success){
//                addUserRecordTask = null;
//                setResult(Activity.RESULT_OK);
//            }
//            else{
//                setResult(Activity.RESULT_CANCELED);
//            }
//
//            // let the rest of the contact stuff get uploaded while this view finishes
//            // the group in the ContactList activity
//            dataActivity.finish();
//        }
//    }
//}
